package com.project.enums;

public enum Role {
    CLIENT("client"),
    MANAGER("manager");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    
}
