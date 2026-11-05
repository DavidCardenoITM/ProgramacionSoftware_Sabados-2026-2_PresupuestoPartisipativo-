package com.itm.edu.co.ProyectoPP_2026.Dtos;

public final class MunicipioDtos {

    private MunicipioDtos() {
    }

    public record MunicipioResumen(Integer id, String nombre, String codigoDane) {
    }

    public record CrearMunicipioRequest(String nombre, String codigoDane) {
    }

    public record ActualizarMunicipioRequest(String nombre, String codigoDane) {
    }
}
