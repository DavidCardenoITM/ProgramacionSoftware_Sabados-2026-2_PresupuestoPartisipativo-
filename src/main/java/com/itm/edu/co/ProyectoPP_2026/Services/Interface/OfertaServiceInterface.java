package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.OfertaLaborSocial;

import java.util.List;

public interface OfertaServiceInterface {

    List<OfertaLaborSocial> listarOfertas();

    OfertaLaborSocial buscarPorId(Integer id);

    OfertaLaborSocial insertarOferta(OfertaLaborSocial oferta);

    OfertaLaborSocial actualizarOferta(OfertaLaborSocial oferta);

    Boolean eliminarOferta(Integer id);
}
