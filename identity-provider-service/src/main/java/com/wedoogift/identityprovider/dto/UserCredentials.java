package com.wedoogift.identityprovider.dto;


import lombok.Builder;

@Builder
public record UserCredentials(String email,
                              String password) {
}
