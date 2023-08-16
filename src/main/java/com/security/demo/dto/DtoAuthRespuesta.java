package com.security.demo.dto;

public class DtoAuthRespuesta {
    private String accesToken;
    private String tokenType="Bearer";

    public DtoAuthRespuesta(String accesToken) {
        this.accesToken = accesToken;
    }
}
