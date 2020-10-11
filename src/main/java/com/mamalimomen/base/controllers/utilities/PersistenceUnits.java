package com.mamalimomen.base.controllers.utilities;

public enum PersistenceUnits {
    UNIT_ONE("persistence-unit-one");

    private final String unitName;

    PersistenceUnits(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName() {
        return this.unitName;
    }
}
