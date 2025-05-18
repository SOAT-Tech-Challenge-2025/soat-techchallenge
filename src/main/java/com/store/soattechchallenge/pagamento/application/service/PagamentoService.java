package com.store.soattechchallenge.pagamento.application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.pagamento.application.usecases.PagamentoUseCase;
import com.store.soattechchallenge.pagamento.domain.GatewayPagamento;
import com.store.soattechchallenge.pagamento.domain.exception.PagamentoAlreadyExists;
import com.store.soattechchallenge.pagamento.domain.exception.PagamentoFinalizationException;
import com.store.soattechchallenge.pagamento.domain.exception.CodigoQRGenerationException;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.repository.exception.EntityNotFoundException;
import com.store.soattechchallenge.pagamento.domain.model.Pagamento;
import com.store.soattechchallenge.pagamento.domain.model.Produto;
import com.store.soattechchallenge.pagamento.domain.model.StatusPagamento;
import com.store.soattechchallenge.pagamento.domain.repository.PagamentoRepository;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.MercadoPagoClient;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.exception.MPClientException;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPOrder;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.integrations.mercado_pago.model.MPPayment;
import com.store.soattechchallenge.pagamento.infrastructure.adapters.out.mappers.StatusPagamentoMapper;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService implements PagamentoUseCase {
    private final PagamentoRepository pagamentoRepository;
    private final GatewayPagamento gatewayPagamento;
    private final MercadoPagoClient mercadoPagoClient;
    private final StatusPagamentoMapper statusPagamentoMapper;

    public PagamentoService(
            MercadoPagoClient mercadoPagoClient,
            PagamentoRepository pagamentoRepository,
            GatewayPagamento gatewayPagamento,
            StatusPagamentoMapper statusPagamentoMapper
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.mercadoPagoClient = mercadoPagoClient;
        this.gatewayPagamento = gatewayPagamento;
        this.statusPagamentoMapper = statusPagamentoMapper;
    }

    @Override
    public Pagamento find(String id) {
        return this.pagamentoRepository.findById(id);
    }

    @Override
    public Pagamento create(String id, Double vlTotalPedido, List<Produto> produtos) {
        if (this.pagamentoRepository.existsById(id)) {
            throw new PagamentoAlreadyExists("Payment with ID " + id + "already exists");
        }

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
            String id = mpOrder.externalReference();
            String idExterno = Long.toString(mpOrder.id());
            if (pagamentoRepository.existsByIdExterno(idExterno)) {
                throw new PagamentoAlreadyExists("Payment with external ID " + idExterno + " already exists");
            }

            Pagamento pagamento = this.pagamentoRepository.findById(id);
            pagamento.setIdExterno(idExterno);
            pagamento.finalize(this.statusPagamentoMapper.toDomain(mpOrder.status()));
            return this.pagamentoRepository.save(pagamento);
        } catch (EntityNotFoundException error) {
            throw new PagamentoFinalizationException("Payment not found");
        } catch (MPClientException error) {
            throw new PagamentoFinalizationException("Error interacting with the payment gateway");
        }
    }

    @Override
    public BufferedImage renderCodigoQr(String id, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Pagamento pagamento = this.pagamentoRepository.findById(id);
            BitMatrix bitMatrix = qrCodeWriter.encode(
                    pagamento.getCodigoQr(),
                    BarcodeFormat.QR_CODE,
                    width,
                    height
            );
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (EntityNotFoundException error) {
            throw new CodigoQRGenerationException("Payment not found");
        } catch (WriterException e) {
            throw new CodigoQRGenerationException("Error generation QR Code: " + e.getMessage());
        }
    }
}
