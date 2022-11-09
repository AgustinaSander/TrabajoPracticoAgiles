package com.tpagiles.models;

public enum EnumRHFactor {
    POSITIVO("+"),
    NEGATIVO("-");

    private String rhFactor;
    EnumRHFactor(String rhFactor) {
        this.rhFactor = rhFactor;
    }

    public String getRhFactor() {
        return rhFactor;
    }
}
