package com.group6.BancoAlimentos.Features.Remito;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RemitoEspecificacion {
    public static Specification<Remito> mesEquals(Integer mes){
        return (root, query, cb) -> mes == null
                ? cb.conjunction()
                : cb.equal(cb.function("MONTH", Integer.class, root.get("fecha")), mes);
    }

    public static Specification<Remito> anioEquals(Integer anio){
        return (root, query, cb) -> anio == null
                ? cb.conjunction()
                : cb.equal(cb.function("YEAR", Integer.class, root.get("fecha")), anio);
    }

    public static Specification<Remito> fechaDesde(LocalDate desde){
        return (root, query, cb) -> desde == null
                ? cb.conjunction()
                : cb.greaterThanOrEqualTo(root.get("fecha"), desde);
    }
}
