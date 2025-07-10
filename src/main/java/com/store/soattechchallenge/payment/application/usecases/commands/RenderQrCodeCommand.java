package com.store.soattechchallenge.payment.application.usecases.commands;

public record RenderQrCodeCommand(String id, int width, int height) {
}
