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
public enum CodigoTipoDocumento {

    FACTURA("01"),
    BOLETA("03"),
    NCREDITO("07"),
    NDEBITO("08"),
    GUIA_REMISION("09"),
    TICKET("12"),
    DOC_SBS("13"),
    DOC_AFP("18"),
    GUIA_TRANSPORTISTA("31"),
    SEAE("56");
    
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }

    private CodigoTipoDocumento(String codigo) {
        this.codigo = codigo;
    }
}
