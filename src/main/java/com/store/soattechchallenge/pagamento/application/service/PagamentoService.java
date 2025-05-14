package com.store.soattechchallenge.pagamento.application.service;

import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.exceptions.AlreadyExists;
import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.model.GatewayPagamentoResponse;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.domain.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService implements PagamentoUseCase {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private GatewayPagamento gatewayPagamento;

    @Override
    public Pagamento find(String id) {
        return this.pagamentoRepository.findById(id);
    }

    @Override
    public Pagamento create(String id, Double vlTotalPedido, List<Produto> produtos) {
        try {
            this.pagamentoRepository.findById(id);
            throw new AlreadyExists("Já existe um pagamento com esse ID");
        } catch (NotFound ignored) {}

        LocalDateTime expiracao = LocalDateTime.now().plusMinutes(10);
        LocalDateTime now = LocalDateTime.now();
        Pagamento pagamento = this.pagamentoRepository.save(
            new Pagamento(
                    id,
                    id + "-awaiting-integration",
                    StatusPagamento.OPENED,
                    vlTotalPedido,
                    "",
                    expiracao,
                    now,
                    now
            )
        );

        GatewayPagamentoResponse gatewayPagamentoResponse = this.gatewayPagamento.create(pagamento, produtos);
        String idExterno = gatewayPagamentoResponse.getId();
        try {
            this.pagamentoRepository.findByIdExterno(idExterno);
            throw new AlreadyExists("Já existe um pagamento com esse ID externo");
        } catch (NotFound ignored) {}

        pagamento.setIdExterno(idExterno);
        pagamento.setCodigoQr(gatewayPagamentoResponse.getCodigoQr());
        pagamento.setTimestamp(LocalDateTime.now());
        return this.pagamentoRepository.save(pagamento);
    }

    @Override
    public Pagamento finalize(String id, StatusPagamento statusPagamento) {
        Pagamento pagamento = this.pagamentoRepository.findById(id);
        pagamento.finalize(statusPagamento);
        return this.pagamentoRepository.save(pagamento);
    }
}
