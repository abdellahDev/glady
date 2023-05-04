package com.wedoogift.identityprovider.enumeration;

import lombok.Getter;

public enum RoleEnum {
    SUPER_ADMIN("SuperAdmin"),
    ADMIN("Admin"),
    USER("User");

    @Getter
    private final String role;
    RoleEnum(String role) {
        this.role=role;
    }
}
