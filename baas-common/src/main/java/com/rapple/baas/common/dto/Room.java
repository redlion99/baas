package com.rapple.baas.common.dto;


import java.util.UUID;

/**
 * Created by libin on 14-11-25.
 */
public class Room {
    private UUID id=UUID.randomUUID();
    private String name;
    private String path;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
