/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.enu;

/**
 *
 * @author christian
 */
public enum CodTipDocIdentidad {

    DOC_TRIB_NO_DOM_SIN_RUC("0"),
    DNI("1"),
    EXTRANJERIA("4"),
    RUC("6"),
    PASAPORTE("7"),
    DEC_DIPLOMATICA("A");
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    private CodTipDocIdentidad(String codigo) {
        this.codigo = codigo;
    }
}
