package com.itm.edu.co.ProyectoPP_2026.Services.Interface.Jpa;

import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistrarHorasRequest;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.RegistroHorasResumen;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ResumenHoras;
import com.itm.edu.co.ProyectoPP_2026.Dtos.HorasDtos.ValidarHorasRequest;

import java.util.List;

public interface RegistroHorasJpaServiceInterface {

    RegistroHorasResumen registrar(RegistrarHorasRequest request, Integer idUsuario);

    RegistroHorasResumen actualizar(Long id, RegistrarHorasRequest request, Integer idUsuario);

    void eliminar(Long id, Integer idUsuario);

    List<RegistroHorasResumen> listar(Long idPostulacionOferta, Integer idUsuario);

    RegistroHorasResumen validar(Long id, ValidarHorasRequest request, Integer idUsuario);

    ResumenHoras resumen(Long idPostulacionOferta, Integer idUsuario);
}
