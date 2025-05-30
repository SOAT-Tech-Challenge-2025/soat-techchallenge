package com.store.soattechchallenge.identification.infrastructure.adapters.out.dto;

import lombok.Getter;

@Getter
public class LoginRequestDTO {

    private String documentNumber;

    private String email;
}