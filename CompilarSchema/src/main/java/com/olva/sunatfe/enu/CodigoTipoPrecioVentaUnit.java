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
public enum CodigoTipoPrecioVentaUnit {
    PRECIO_UNITARIO("01"),
    VALOR_REF_UNIT_EN_OPER_NO_ORENOSAS("02");
    
    private final String codigo;

    public String getCodigo() {
        return codigo;
    }   

    private CodigoTipoPrecioVentaUnit(String codigo) {        
        this.codigo = codigo;
    }
}
