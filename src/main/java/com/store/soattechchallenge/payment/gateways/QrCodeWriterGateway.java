package com.store.soattechchallenge.payment.gateways;

import java.awt.image.BufferedImage;

public interface QrCodeWriterGateway {
    public BufferedImage render(String qrCode, Integer width, Integer height);
}
