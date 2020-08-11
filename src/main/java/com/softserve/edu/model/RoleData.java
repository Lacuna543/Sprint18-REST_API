package com.softserve.edu.model;

public enum RoleData {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    //
    private String name;

    private RoleData(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
