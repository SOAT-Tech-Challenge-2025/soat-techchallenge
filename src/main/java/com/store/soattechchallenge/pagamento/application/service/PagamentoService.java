package com.store.soattechchallenge.pagamento.application.service;

import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
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
        return this.pagamentoRepository.save(
                new Pagamento(
                        pagamento.id(),
                        gatewayPagamentoResponse.id(),
                        pagamento.stPagamento(),
                        pagamento.vlTotalPedido(),
                        gatewayPagamentoResponse.codigoQr(),
                        pagamento.expiracao(),
                        pagamento.dtInclusao(),
                        LocalDateTime.now()
                )
        );
    }

    @Override
    public Pagamento finish(String id, StatusPagamento statusPagamento) {
        Pagamento pagamento = this.pagamentoRepository.findById(id);
        return this.pagamentoRepository.save(
                new Pagamento(
                    pagamento.id(),
                    pagamento.idExterno(),
                    statusPagamento,
                    pagamento.vlTotalPedido(),
                    pagamento.codigoQr(),
                    pagamento.expiracao(),
                    pagamento.dtInclusao(),
                    LocalDateTime.now()
                )
        );
    }
}
