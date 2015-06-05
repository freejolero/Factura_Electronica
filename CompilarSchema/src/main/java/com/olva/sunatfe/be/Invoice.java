/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.be;

import com.olva.sunatfe.enu.CodTipDocIdentidad;
import com.olva.sunatfe.enu.CodigoConceptosTributarios;
import com.olva.sunatfe.enu.CodigoElementosAdicionalesComprobante;
import com.olva.sunatfe.enu.CodigoTipoDocumento;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author CTELLO
 */
public class Invoice {

    private static final ObjectFactory FACTORIA = new ObjectFactory();
    private final static GregorianCalendar c = new GregorianCalendar();
    private static JAXBContext context;
    private static Marshaller marshallerElement, marshallerInvoice;
    private InvoiceType invoiceType;
    private UBLExtensionsType extensiones;
    private UBLExtensionType extensionInfAdicional;
    private ExtensionContentType contenidoDeExtension;
    private AdditionalInformationTypeSunatAgg informacionAdicional;
    private SupplierPartyType supplierPartyType;
    private PartyType partyType;
    private AddressType addressType;
    private CountryType countryType;
    private PartyLegalEntityType partyLegalEntityType;

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
        invoiceType.setAccountingSupplierParty(supplierPartyType);
        supplierPartyType.setParty(partyType);
        partyType.setPostalAddress(addressType);
        addressType.setCountry(countryType);
        partyType.getPartyLegalEntity().add(partyLegalEntityType);
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
        UBLVersionIDType ublIdVersion = FACTORIA.createUBLVersionIDType();
        ublIdVersion.setValue(version);
        invoiceType.setUBLVersionID(ublIdVersion);
    }

    public void setCustomizationId(String id) {
        CustomizationIDType cidt = FACTORIA.createCustomizationIDType();
        cidt.setValue(id);
        invoiceType.setCustomizationID(cidt);
    }

    public void setId(String id) {
        IDType invoiceId = FACTORIA.createIDType();
        invoiceId.setValue(id);
        invoiceType.setID(invoiceId);
    }

    public void setIssueDate(Date fecha) throws DatatypeConfigurationException {
        IssueDateType issueDate = FACTORIA.createIssueDateType();
        c.setTime(fecha);
        issueDate.setValue(DatatypeFactory.newInstance().newXMLGregorianCalendarDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), DatatypeConstants.FIELD_UNDEFINED));
        invoiceType.setIssueDate(issueDate);
    }

    public void setInvoiceTypeCode(CodigoTipoDocumento codigo) {
        InvoiceTypeCodeType invoiceTypeCod = FACTORIA.createInvoiceTypeCodeType();
        invoiceTypeCod.setValue(codigo.getCodigo());
        invoiceType.setInvoiceTypeCode(invoiceTypeCod);
    }

    public void setDocumentCurrencyCode(String codigo) {
        DocumentCurrencyCodeType currencyCode = FACTORIA.createDocumentCurrencyCodeType();
        currencyCode.setValue(codigo);
        invoiceType.setDocumentCurrencyCode(currencyCode);
    }

    public void setAccountingSupplierPartyCustomerAssignedAccountID(String rucProveedor) {
        CustomerAssignedAccountIDType customerRuc = FACTORIA.createCustomerAssignedAccountIDType();
        customerRuc.setValue(rucProveedor);
        supplierPartyType.setCustomerAssignedAccountID(customerRuc);
    }
    
    public void setAccountingSupplierPartyAdditionalAccountID(CodTipDocIdentidad codigo) {
        AdditionalAccountIDType tipDoc = FACTORIA.createAdditionalAccountIDType();
        tipDoc.setValue(codigo.getCodigo());
        supplierPartyType.getAdditionalAccountID().add(tipDoc);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressId(String id){
        IDType idType = FACTORIA.createIDType();
        idType.setValue(id);
        addressType.setID(idType);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressStreetName(String direccion){
        StreetNameType nombreDireccion = FACTORIA.createStreetNameType();
        nombreDireccion.setValue(direccion);
        addressType.setStreetName(nombreDireccion);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressCitySubdivisionName(String nombre){
        CitySubdivisionNameType nameType = FACTORIA.createCitySubdivisionNameType();
        nameType.setValue(nombre);
        addressType.setCitySubdivisionName(nameType);        
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressCityName(String nombre){
        CityNameType cityNameType = FACTORIA.createCityNameType();
        cityNameType.setValue(nombre);
        addressType.setCityName(cityNameType);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressCountrySubentity(String nombre){
        CountrySubentityType countrySubentityType = FACTORIA.createCountrySubentityType();
        countrySubentityType.setValue(nombre);
        addressType.setCountrySubentity(countrySubentityType);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressDistrict(String nombre){
        DistrictType districtType = FACTORIA.createDistrictType();
        districtType.setValue(nombre);
        addressType.setDistrict(districtType);
    }
    
    public void setAccountingSupplierPartyPartyPostalAddressCountryIdentificationCode(String codigo){
        IdentificationCodeType codeType = FACTORIA.createIdentificationCodeType();
        codeType.setValue(codigo);
        countryType.setIdentificationCode(codeType);
    }
    
    public void setAccountingSupplierPartyPartyPartyLegalEntityRegistrationName(String nombre){
        RegistrationNameType nameType = FACTORIA.createRegistrationNameType();
        nameType.setValue(nombre);
        partyLegalEntityType.setRegistrationName(nameType);
    }

    private void inicializarElementosBasicos() {
        invoiceType = FACTORIA.createInvoiceType();
        extensiones = FACTORIA.createUBLExtensionsType();
        extensionInfAdicional = FACTORIA.createUBLExtensionType();
        contenidoDeExtension = FACTORIA.createExtensionContentType();
        informacionAdicional = FACTORIA.createAdditionalInformationTypeSunatAgg();
        supplierPartyType = FACTORIA.createSupplierPartyType();
        partyType = FACTORIA.createPartyType();
        addressType = FACTORIA.createAddressType();
        countryType = FACTORIA.createCountryType();
        partyLegalEntityType = FACTORIA.createPartyLegalEntityType();
    }
}
