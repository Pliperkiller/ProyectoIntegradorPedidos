package com.micro.inventario.infrastructure.adapters.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ErrorResponse {
    
    @Schema(description = "Código de error", example = "INVENTORY_ERROR")
    private String code;
    
    @Schema(description = "Mensaje de error", example = "Hubo un error al procesar la solicitud")
    private String message;
    
    @Schema(description = "Detalles adicionales del error", example = "Servicio no habilitado")
    private String details;

    public ErrorResponse() {
    }

    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponse(String code, String message, String details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}