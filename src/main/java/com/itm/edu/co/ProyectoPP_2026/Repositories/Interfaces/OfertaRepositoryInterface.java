package com.itm.edu.co.ProyectoPP_2026.Repositories.Interfaces;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;

import java.util.List;

public interface OfertaRepositoryInterface {
    List<OfertaLaborSocial> listarOfertas();
    OfertaLaborSocial buscarPorId(Integer id);
    OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta);
    OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta);
    Boolean eliminarOferta(Integer id);
}
