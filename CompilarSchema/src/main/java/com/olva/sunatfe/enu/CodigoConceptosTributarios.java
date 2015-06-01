/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.enu;

/**
 *
 * @author CTELLO
 */
public enum CodigoConceptosTributarios {

    TOTAL_VALO_VENTA_OPERACIONES_GRABADAS("1001"),
    TOTAL_VALO_VENTA_OPERACIONES_INAFECTAS("1002"),
    TOTAL_VALO_VENTA_OPERACIONES_EXONERADAS("1003"),
    TOTAL_VALO_VENTA_OPERACIONES_GRATUITAS("1004"),
    SUB_TOTAL_VENTA("1005"),
    PERCEPCIONES("2001"),
    RETENCIONES("2002"),
    DETRACCIONES("2003"),
    BONIFICACIONES("2004"),
    TOTAL_DESCUENTO("2005"),
    FISE("3001");
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    private CodigoConceptosTributarios(String codigo) {
        this.codigo = codigo;
    }

}
