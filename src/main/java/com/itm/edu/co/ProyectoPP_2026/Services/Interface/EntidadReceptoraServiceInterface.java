package com.itm.edu.co.ProyectoPP_2026.Services.Interface;

import com.itm.edu.co.ProyectoPP_2026.Identities.EntidadReceptora;

import java.util.List;

public interface EntidadReceptoraServiceInterface {

     List<EntidadReceptora> listaEntidad();

     EntidadReceptora listarPorId(Integer id);

     EntidadReceptora insertarEntidad(EntidadReceptora entidad);

     EntidadReceptora actualizarEntidad(EntidadReceptora entidad);

     Boolean eliminarEntidad(Integer id);
}
