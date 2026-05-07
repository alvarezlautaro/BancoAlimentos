package com.group6.BancoAlimentos.Common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InstitucionNoEncontradaException.class)
    public ResponseEntity<String> manejarInstitucionNoEncontrada(InstitucionNoEncontradaException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(MethodArgumentNotValidException ex){
        Map<String, String> errores = new HashMap<>();

        //getBindingResult: tiene el detalle completo de que fallo en la validacion.
        //getFieldErrors: del detalle completo de lo que fallo, da la lista solo de los atributos del DTO que fallaron.
        //forEach: recorre la lista de errores
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            //getField: agarra el nombre de la variable.
            //getDefaultMessage: saca el texto que escribi en el Schema del DTO.
            //errores.put mete los datos en el mapa.
            errores.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> manejarErrorDeConversion(MethodArgumentTypeMismatchException ex){
        ProblemDetail problema = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "El valor no coincide con el tipo esperado");

        //Verifica que el dato que se esperaba era un ENUM
        if(ex.getRequiredType() != null && ex.getRequiredType().isEnum()){
            //Agrega el valor recibido en la consulta
            problema.setProperty("valor recibido: ", ex.getValue());
            //Muestra los valores permitidos que tiene el ENUM
            problema.setProperty("valores permitidos: ", ex.getRequiredType().getEnumConstants());
        }

        return ResponseEntity.badRequest().body(problema);
    }
}
