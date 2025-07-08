package com.store.soattechchallenge.identification.infrastructure.api.dto;

import lombok.Getter;

@Getter
public class LoginRequestDTO {

    private String documentNumber;

    private String email;
}