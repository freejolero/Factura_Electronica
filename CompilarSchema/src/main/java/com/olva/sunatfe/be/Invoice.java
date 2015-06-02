/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.be;

import com.olva.sunatfe.enu.CodigoConceptosTributarios;
import com.olva.sunatfe.enu.CodigoElementosAdicionalesComprobante;
import java.math.BigDecimal;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author CTELLO
 */
public class Invoice {

    private static final ObjectFactory FACTORIA = new ObjectFactory();
    private static JAXBContext context;
    private static Marshaller marshallerElement, marshallerInvoice;
    private InvoiceType invoiceType;
    private UBLExtensionsType extensiones;
    private UBLExtensionType extensionInfAdicional;
    private ExtensionContentType contenidoDeExtension;
    private AdditionalInformationTypeSunatAgg informacionAdicional;
    private UBLVersionIDType ublIdVersion;

    public Invoice() throws JAXBException {
        if (context == null) {
            context = JAXBContext.newInstance(ObjectFactory.class);
        }
        if (marshallerElement == null) {
            marshallerElement = context.createMarshaller();
        }
        if (marshallerInvoice == null) {
            marshallerInvoice = context.createMarshaller();
            marshallerInvoice.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshallerInvoice.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
        }
        inicializarElementosBasicos();
        invoiceType.setUBLExtensions(extensiones);
        extensiones.getUBLExtension().add(extensionInfAdicional);
        extensionInfAdicional.setExtensionContent(contenidoDeExtension);
    }

    public void generar() throws JAXBException {
        JAXBElement<AdditionalInformationTypeSunatAgg> jeAits = FACTORIA.createAdditionalInformation(informacionAdicional);
        DOMResult res = new DOMResult();
        marshallerElement.marshal(jeAits, res);
        Element elem = ((Document) res.getNode()).getDocumentElement();
        contenidoDeExtension.setAny(elem);
        marshallerInvoice.marshal(FACTORIA.createInvoice(invoiceType), System.out);
    }

    public void addFacturaExtensionesExtensionContenidoDeExtensionInformacionAdicionalTotalMonetario(CodigoConceptosTributarios cod, BigDecimal monto) {
        IDType idAMonetaryTotal = FACTORIA.createIDType();
        idAMonetaryTotal.setValue(cod.getCodigo());
        PayableAmountType pa = FACTORIA.createPayableAmountType();
        pa.setValue(monto);
        AdditionalMonetaryTotalType amtt = FACTORIA.createAdditionalMonetaryTotalType();
        amtt.setID(idAMonetaryTotal);
        amtt.setPayableAmount(pa);
        informacionAdicional.getAdditionalMonetaryTotal().add(amtt);
    }
    
    public void addFacturaExtensionesExtensionContenidoDeExtensionInformacionAdicionalPropiedadAdicional(CodigoElementosAdicionalesComprobante cod, String valor) {
        IDType IdPropiedadAdicional = FACTORIA.createIDType();
        IdPropiedadAdicional.setValue(cod.getCodigo());
        ValueType valorDePropiedad = FACTORIA.createValueType();
        valorDePropiedad.setValue(valor);
        AdditionalPropertyType propiedadAdicional = FACTORIA.createAdditionalPropertyType();
        propiedadAdicional.setID(IdPropiedadAdicional);
        propiedadAdicional.setValue(valorDePropiedad);
        informacionAdicional.getAdditionalProperty().add(propiedadAdicional);
    }

    public void setUBLIdVersion(String version) {
        ublIdVersion.setValue(version);
        invoiceType.setUBLVersionID(ublIdVersion);
    }

    private void inicializarElementosBasicos() {
        invoiceType = FACTORIA.createInvoiceType();
        extensiones = FACTORIA.createUBLExtensionsType();
        extensionInfAdicional = FACTORIA.createUBLExtensionType();
        contenidoDeExtension = FACTORIA.createExtensionContentType();
        informacionAdicional = FACTORIA.createAdditionalInformationTypeSunatAgg();
        ublIdVersion = FACTORIA.createUBLVersionIDType();
    }
}
