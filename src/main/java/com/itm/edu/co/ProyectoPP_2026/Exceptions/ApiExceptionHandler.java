package com.itm.edu.co.ProyectoPP_2026.Exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice(basePackages = "com.itm.edu.co.ProyectoPP_2026.Controllers.Api")
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ProblemDetail> manejarApiException(ApiException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(ex.getStatus(), ex.getMessage());
        detalle.setTitle("Error de negocio");
        detalle.setProperty("codigo", ex.getCode());
        return ResponseEntity.status(ex.getStatus())
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(detalle);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> manejarCuerpoInvalido(HttpMessageNotReadableException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud no tiene un formato valido");
        detalle.setTitle("Solicitud invalida");
        detalle.setProperty("codigo", "CUERPO_INVALIDO");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(detalle);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> manejarParametroInvalido(MethodArgumentTypeMismatchException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "El parametro '" + ex.getName() + "' tiene un valor invalido");
        detalle.setTitle("Solicitud invalida");
        detalle.setProperty("codigo", "PARAMETRO_INVALIDO");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(detalle);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> manejarConflictoDeDatos(DataIntegrityViolationException ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, "La operacion viola una restriccion de integridad de datos");
        detalle.setTitle("Conflicto de datos");
        detalle.setProperty("codigo", "CONFLICTO_DATOS");
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(detalle);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> manejarErrorGeneral(Exception ex) {
        ProblemDetail detalle = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error interno al procesar la solicitud");
        detalle.setTitle("Error interno");
        detalle.setProperty("codigo", "ERROR_INTERNO");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(detalle);
    }
}
