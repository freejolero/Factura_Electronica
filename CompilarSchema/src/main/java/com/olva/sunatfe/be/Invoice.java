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
import com.olva.sunatfe.enu.CodigoTipoTributo;
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
    private PartyType partyTypeSupplier, partyTypeCustomer;
    private AddressType addressType;
    private CountryType countryType;
    private PartyLegalEntityType partyLegalEntityTypeSupplier, partyLegalEntityTypeCustomer;
    private CustomerPartyType customerPartyType;
    private TaxTotalType taxTotalType;
    private TaxSubtotalType taxSubtotalType;
    private TaxCategoryType taxCategoryType;
    private TaxSchemeType taxSchemeType;
    private MonetaryTotalType monetaryTotalType;
    private InvoiceLineType invoiceLineType;

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
        supplierPartyType.setParty(partyTypeSupplier);
        partyTypeSupplier.setPostalAddress(addressType);
        addressType.setCountry(countryType);
        partyTypeSupplier.getPartyLegalEntity().add(partyLegalEntityTypeSupplier);
        invoiceType.setAccountingCustomerParty(customerPartyType);
        customerPartyType.setParty(partyTypeCustomer);
        partyTypeCustomer.getPartyLegalEntity().add(partyLegalEntityTypeCustomer);
        invoiceType.getTaxTotal().add(taxTotalType);
        taxTotalType.getTaxSubtotal().add(taxSubtotalType);
        taxSubtotalType.setTaxCategory(taxCategoryType);
        taxCategoryType.setTaxScheme(taxSchemeType);
        invoiceType.setLegalMonetaryTotal(monetaryTotalType);
        invoiceType.getInvoiceLine().add(invoiceLineType);

    }

    private void inicializarElementosBasicos() {
        invoiceType = FACTORIA.createInvoiceType();
        extensiones = FACTORIA.createUBLExtensionsType();
        extensionInfAdicional = FACTORIA.createUBLExtensionType();
        contenidoDeExtension = FACTORIA.createExtensionContentType();
        informacionAdicional = FACTORIA.createAdditionalInformationTypeSunatAgg();
        supplierPartyType = FACTORIA.createSupplierPartyType();
        partyTypeSupplier = FACTORIA.createPartyType();
        addressType = FACTORIA.createAddressType();
        countryType = FACTORIA.createCountryType();
        partyLegalEntityTypeSupplier = FACTORIA.createPartyLegalEntityType();
        customerPartyType = FACTORIA.createCustomerPartyType();
        partyTypeCustomer = FACTORIA.createPartyType();
        partyLegalEntityTypeCustomer = FACTORIA.createPartyLegalEntityType();
        taxTotalType = FACTORIA.createTaxTotalType();
        taxSubtotalType = FACTORIA.createTaxSubtotalType();
        taxCategoryType = FACTORIA.createTaxCategoryType();
        taxSchemeType = FACTORIA.createTaxSchemeType();
        monetaryTotalType = FACTORIA.createMonetaryTotalType();
        invoiceLineType = FACTORIA.createInvoiceLineType();
    }

    public void addInvoiceLine(InvoiceDetail det) {
        InvoiceLineType ilt = FACTORIA.createInvoiceLineType();
        IDType iDType = FACTORIA.createIDType();
        iDType.setValue(det.getId());
        ilt.setID(iDType);
        InvoicedQuantityType iqt = FACTORIA.createInvoicedQuantityType();
        iqt.setValue(det.getInvoicedQuantityMonto());
        iqt.setUnitCode(det.getInvoicedQuantityUnitCode());
        ilt.setInvoicedQuantity(iqt);
        LineExtensionAmountType leat = FACTORIA.createLineExtensionAmountType();
        leat.setCurrencyID(det.getLineExtensionAmountCurrencyCode());
        leat.setValue(det.getLineExtensionAmountMonto());
        ilt.setLineExtensionAmount(leat);
        PricingReferenceType prt = FACTORIA.createPricingReferenceType();
        PriceType priceType = FACTORIA.createPriceType();
        PriceAmountType amountType = FACTORIA.createPriceAmountType();
        amountType.setValue(det.getPriceAmountMonto());
        amountType.setCurrencyID(det.getPriceAmountCurrencyCode());
        priceType.setPriceAmount(amountType);
        prt.getAlternativeConditionPrice().add(priceType);
        ilt.setPricingReference(prt);
        TaxTotalType ttt = FACTORIA.createTaxTotalType();
        TaxAmountType tat = FACTORIA.createTaxAmountType();
        tat.setValue(det.getTaxTotalTaxAmountMonto());
        tat.setCurrencyID(det.getTaxTotalTaxAmountCodigo());
        ttt.setTaxAmount(tat);
        TaxSubtotalType tst = FACTORIA.createTaxSubtotalType();
        TaxAmountType taxAmountSub = FACTORIA.createTaxAmountType();
        taxAmountSub.setValue(det.getTaxTotalTaxSubtotalTaxAmountMonto());
        taxAmountSub.setCurrencyID(det.getTaxTotalTaxSubtotalTaxAmountCodigo());
        tst.setTaxAmount(taxAmountSub);
        TaxCategoryType tct = FACTORIA.createTaxCategoryType();
        TaxExemptionReasonCodeType terct = FACTORIA.createTaxExemptionReasonCodeType();
        terct.setValue(det.getTaxExemptionReasonCode().getCodigo());
        tct.setTaxExemptionReasonCode(terct);
        TaxSchemeType taxSchemeType2 = FACTORIA.createTaxSchemeType();
        IDType iDType2 = FACTORIA.createIDType();
        NameTypeCommBas nameTypeCommBas = FACTORIA.createNameTypeCommBas();
        TaxTypeCodeType taxTypeCodeType = FACTORIA.createTaxTypeCodeType();
        iDType2.setValue(det.getCategoryTaxScheme().getId());
        nameTypeCommBas.setValue(det.getCategoryTaxScheme().name());
        taxTypeCodeType.setValue(det.getCategoryTaxScheme().getCodigo());
        taxSchemeType2.setID(iDType2);
        taxSchemeType2.setName(nameTypeCommBas);
        taxSchemeType2.setTaxTypeCode(taxTypeCodeType);
        tct.setTaxScheme(taxSchemeType2);
        tst.setTaxCategory(tct);
        ttt.getTaxSubtotal().add(tst);
        ilt.getTaxTotal().add(ttt);
    }

    public void setLegalMonetaryTotalPayableAmount(BigDecimal monto, CurrencyCodeContentType codigo) {
        PayableAmountType payableAmountType = FACTORIA.createPayableAmountType();
        payableAmountType.setCurrencyID(codigo);
        payableAmountType.setValue(monto);
        monetaryTotalType.setPayableAmount(payableAmountType);
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

    public void setAccountingSupplierPartyPartyPostalAddressId(String id) {
        IDType idType = FACTORIA.createIDType();
        idType.setValue(id);
        addressType.setID(idType);
    }

    public void setAccountingSupplierPartyPartyPostalAddressStreetName(String direccion) {
        StreetNameType nombreDireccion = FACTORIA.createStreetNameType();
        nombreDireccion.setValue(direccion);
        addressType.setStreetName(nombreDireccion);
    }

    public void setAccountingSupplierPartyPartyPostalAddressCitySubdivisionName(String nombre) {
        CitySubdivisionNameType nameType = FACTORIA.createCitySubdivisionNameType();
        nameType.setValue(nombre);
        addressType.setCitySubdivisionName(nameType);
    }

    public void setAccountingSupplierPartyPartyPostalAddressCityName(String nombre) {
        CityNameType cityNameType = FACTORIA.createCityNameType();
        cityNameType.setValue(nombre);
        addressType.setCityName(cityNameType);
    }

    public void setAccountingSupplierPartyPartyPostalAddressCountrySubentity(String nombre) {
        CountrySubentityType countrySubentityType = FACTORIA.createCountrySubentityType();
        countrySubentityType.setValue(nombre);
        addressType.setCountrySubentity(countrySubentityType);
    }

    public void setAccountingSupplierPartyPartyPostalAddressDistrict(String nombre) {
        DistrictType districtType = FACTORIA.createDistrictType();
        districtType.setValue(nombre);
        addressType.setDistrict(districtType);
    }

    public void setAccountingSupplierPartyPartyPostalAddressCountryIdentificationCode(String codigo) {
        IdentificationCodeType codeType = FACTORIA.createIdentificationCodeType();
        codeType.setValue(codigo);
        countryType.setIdentificationCode(codeType);
    }

    public void setAccountingSupplierPartyPartyPartyLegalEntityRegistrationName(String nombre) {
        RegistrationNameType nameType = FACTORIA.createRegistrationNameType();
        nameType.setValue(nombre);
        partyLegalEntityTypeSupplier.setRegistrationName(nameType);
    }

    public void setAccountingCustomerPartyCustomerAssignedAccountID(String rucCliente) {
        CustomerAssignedAccountIDType customerRuc = FACTORIA.createCustomerAssignedAccountIDType();
        customerRuc.setValue(rucCliente);
        customerPartyType.setCustomerAssignedAccountID(customerRuc);
    }

    public void setAccountingCustomerPartyAdditionalAccountID(CodTipDocIdentidad codigo) {
        AdditionalAccountIDType tipDoc = FACTORIA.createAdditionalAccountIDType();
        tipDoc.setValue(codigo.getCodigo());
        customerPartyType.getAdditionalAccountID().add(tipDoc);
    }

    public void setAccountingCustomerPartyPartyPartyLegalEntityRegistrationName(String nombre) {
        RegistrationNameType nameType = FACTORIA.createRegistrationNameType();
        nameType.setValue(nombre);
        partyLegalEntityTypeCustomer.setRegistrationName(nameType);
    }

    public void setTaxTotalTaxAmount(BigDecimal monto, CurrencyCodeContentType codigo) {
        TaxAmountType taxAmountType = FACTORIA.createTaxAmountType();
        taxAmountType.setValue(monto);
        taxAmountType.setCurrencyID(codigo);
        taxTotalType.setTaxAmount(taxAmountType);
    }

    public void setTaxTotalTaxSubtotalTaxAmount(BigDecimal monto, CurrencyCodeContentType codigo) {
        TaxAmountType taxAmountType = FACTORIA.createTaxAmountType();
        taxAmountType.setValue(monto);
        taxAmountType.setCurrencyID(codigo);
        taxSubtotalType.setTaxAmount(taxAmountType);
    }

    public void setTaxTotalTaxSubtotalTaxCategoryTaxScheme(CodigoTipoTributo codigo) {
        IDType iDType = FACTORIA.createIDType();
        NameTypeCommBas nameTypeCommBas = FACTORIA.createNameTypeCommBas();
        TaxTypeCodeType taxTypeCodeType = FACTORIA.createTaxTypeCodeType();
        iDType.setValue(codigo.getId());
        nameTypeCommBas.setValue(codigo.name());
        taxTypeCodeType.setValue(codigo.getCodigo());
        taxSchemeType.setID(iDType);
        taxSchemeType.setName(nameTypeCommBas);
        taxSchemeType.setTaxTypeCode(taxTypeCodeType);
    }
}
