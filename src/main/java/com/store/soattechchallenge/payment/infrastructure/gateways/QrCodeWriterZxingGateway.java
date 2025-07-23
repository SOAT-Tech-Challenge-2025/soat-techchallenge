package com.store.soattechchallenge.payment.infrastructure.gateways;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.store.soattechchallenge.payment.gateways.QrCodeWriterGateway;

import java.awt.image.BufferedImage;

public class QrCodeWriterZxingGateway implements QrCodeWriterGateway {
    private final QRCodeWriter qrCodeWriter;

    public QrCodeWriterZxingGateway(QRCodeWriter qrCodeWriter) {
        this.qrCodeWriter = qrCodeWriter;
    }

    @Override
    public BufferedImage render(String qrCode, Integer width, Integer height) {
        try {
            BitMatrix bitMatrix = this.qrCodeWriter.encode(
                    qrCode,
                    BarcodeFormat.QR_CODE,
                    width,
                    height
            );
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
