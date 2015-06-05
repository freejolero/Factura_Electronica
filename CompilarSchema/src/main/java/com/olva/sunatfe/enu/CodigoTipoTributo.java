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
public enum CodigoTipoTributo {

    IGV("1000", "VAT"),
    ISC("2000", "EXC"),
    OTROS("9999", "OTH");
    private final String id;
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    public String getId() {
        return id;
    }

    private CodigoTipoTributo(String id, String codigo) {
        this.id = id;
        this.codigo = codigo;
    }
}
