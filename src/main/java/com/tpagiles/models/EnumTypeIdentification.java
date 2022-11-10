package com.tpagiles.models;

public enum EnumTypeIdentification {
    DNI("DNI"),
    PASAPORTE("PASAPORTE"),
    LU("LU"),
    LE("LE");

    private String type;
    EnumTypeIdentification(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
