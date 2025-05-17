package com.store.soattechchallenge.pagamento.application.service;

import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.configuration.PagamentoConfiguration;
import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.exceptions.AlreadyExists;
import com.store.soattechchallenge.pagamento.domain.exceptions.FinalizePagamentoError;
import com.store.soattechchallenge.pagamento.domain.exceptions.NotFound;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.domain.repository.PagamentoRepository;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exceptions.MPClientError;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPPayment;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.StatusPagamentoMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService implements PagamentoUseCase {
    private final PagamentoRepository pagamentoRepository;
    private final GatewayPagamento gatewayPagamento;
    private final MercadoPagoClient mercadoPagoClient;

    public PagamentoService(
            PagamentoConfiguration pagamentoConfiguration,
            PagamentoRepository pagamentoRepository,
            GatewayPagamento gatewayPagamento
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.mercadoPagoClient = new MercadoPagoClient(pagamentoConfiguration.getAccessToken());
        this.gatewayPagamento = gatewayPagamento;
    }

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
        Pagamento pagamento = new Pagamento(
                id,
                "empty-" + id,
                StatusPagamento.OPENED,
                vlTotalPedido,
                "",
                expiracao,
                now,
                now
        );

        pagamento = this.gatewayPagamento.create(pagamento, produtos);
        return this.pagamentoRepository.save(pagamento);
    }

    @Override
    public Pagamento finalizeByMercadoPagoPaymentId(String paymentId) {
        try {
            MPPayment mpPayment = this.mercadoPagoClient.findPaymentById(paymentId);
            MPOrder mpOrder = this.mercadoPagoClient.findOrderById(mpPayment.order().id());
            try {
                this.pagamentoRepository.findByIdExterno(mpOrder.externalReference());
                throw new AlreadyExists("Já existe um pagamento com esse ID externo");
            } catch (NotFound ignored) {}

            Pagamento pagamento = this.pagamentoRepository.findById(mpOrder.externalReference());
            pagamento.setIdExterno(Long.toString(mpOrder.id()));
            pagamento.finalize(StatusPagamentoMapper.toStatusPagamento(mpOrder.status()));
            return this.pagamentoRepository.save(pagamento);
        } catch (NotFound error) {
            throw new FinalizePagamentoError("O pagamento não foi identificado");
        } catch (MPClientError error) {
            throw new FinalizePagamentoError("Ocorreu um erro na comunicação com o gateway de pagamento");
        }
    }
}
