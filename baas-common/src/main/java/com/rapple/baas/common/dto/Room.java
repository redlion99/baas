package com.rapple.baas.common.dto;


import java.util.UUID;

/**
 * Created by libin on 14-11-25.
 */
public class Room {
    private UUID id=UUID.randomUUID();
    private String name;
    private String domain;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
        this.domain = "/public";
    }

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
        return domain+"/"+id.toString();
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
