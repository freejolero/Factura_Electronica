/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.test.bc;

import com.olva.sunatfe.bc.HeaderHandlerResolver;
import com.olva.sunatfe.be.CurrencyCodeContentType;
import com.olva.sunatfe.be.Invoice;
import com.olva.sunatfe.be.InvoiceDetail;
import com.olva.sunatfe.be.InvoiceType;
import com.olva.sunatfe.be.ObjectFactory;
import com.olva.sunatfe.enu.CodTipDocIdentidad;
import com.olva.sunatfe.enu.CodigoConceptosTributarios;
import com.olva.sunatfe.enu.CodigoElementosAdicionalesComprobante;
import com.olva.sunatfe.enu.CodigoTipoAfectacionIgv;
import com.olva.sunatfe.enu.CodigoTipoDocumento;
import com.olva.sunatfe.enu.CodigoTipoPrecioVentaUnit;
import com.olva.sunatfe.enu.CodigoTipoTributo;
import com.olva.sunatfe.service.BillService;
import com.olva.sunatfe.service.BillService_Service;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.soap.SOAPFaultException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author christian
 */
public class FacturaElectronicaTest {

    public FacturaElectronicaTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void marshallFacturaElectronica() {
        try {
            Invoice invoice = new Invoice();
            invoice.addFacturaExtensionesExtensionContenidoDeExtensionInformacionAdicionalTotalMonetario(CodigoConceptosTributarios.TOTAL_VALO_VENTA_OPERACIONES_GRABADAS, new BigDecimal("348199.15"));
            invoice.addFacturaExtensionesExtensionContenidoDeExtensionInformacionAdicionalTotalMonetario(CodigoConceptosTributarios.TOTAL_VALO_VENTA_OPERACIONES_EXONERADAS, new BigDecimal("12350.00"));
            invoice.addFacturaExtensionesExtensionContenidoDeExtensionInformacionAdicionalPropiedadAdicional(CodigoElementosAdicionalesComprobante.MONTO_EN_LETRAS, "CUATROCIENTOS VEINTITRES MIL DOSCIENTOS VEINTICINCO Y 00/100");
            invoice.setUBLIdVersion("2.0");
            invoice.setCustomizationId("1.0");
            invoice.setId("F001-4355");
            invoice.setIssueDate(new Date());
            invoice.setInvoiceTypeCode(CodigoTipoDocumento.FACTURA);
            invoice.setDocumentCurrencyCode("PEN");
            
            invoice.setSignatureId("IDSignOlva");
            invoice.setSignatureSignatoryPartyPartyIdentificationId("10428482072");
            invoice.setSignatureSignatoryPartyPartyNameName("OLVA COURIER SAC");
            invoice.setSignatureDigitalSignatureAttachmentExternalReferenceURI("#SignatureOlva");
            
            invoice.setAccountingSupplierPartyCustomerAssignedAccountID("10428482072");
            invoice.setAccountingSupplierPartyAdditionalAccountID(CodTipDocIdentidad.RUC);
            invoice.setAccountingSupplierPartyPartyPostalAddressId("150111");
            invoice.setAccountingSupplierPartyPartyPostalAddressStreetName("AV. LOS PRECURSORES #1245");
            invoice.setAccountingSupplierPartyPartyPostalAddressCitySubdivisionName("URB. MIGUEL GRAU");
            invoice.setAccountingSupplierPartyPartyPostalAddressCityName("LIMA");
            invoice.setAccountingSupplierPartyPartyPostalAddressCountrySubentity("LIMA");
            invoice.setAccountingSupplierPartyPartyPostalAddressDistrict("EL AGUSTINO");
            invoice.setAccountingSupplierPartyPartyPostalAddressCountryIdentificationCode("PE");
            invoice.setAccountingSupplierPartyPartyPartyLegalEntityRegistrationName("SOPORTE TECNOLOGICOS EIRL");
            invoice.setAccountingCustomerPartyCustomerAssignedAccountID("20587896411");
            invoice.setAccountingCustomerPartyAdditionalAccountID(CodTipDocIdentidad.RUC);
            invoice.setAccountingCustomerPartyPartyPartyLegalEntityRegistrationName("SERVICABINAS S.A.");
            invoice.setTaxTotalTaxAmount(new BigDecimal("62675.85"), CurrencyCodeContentType.PEN);
            invoice.setTaxTotalTaxSubtotalTaxAmount(new BigDecimal("62675.85"), CurrencyCodeContentType.PEN);
            invoice.setTaxTotalTaxSubtotalTaxCategoryTaxScheme(CodigoTipoTributo.IGV);
            invoice.setLegalMonetaryTotalPayableAmount(new BigDecimal("423225.00"), CurrencyCodeContentType.PEN);

            InvoiceDetail det1 = new InvoiceDetail();
            det1.setId("1");
            det1.setInvoicedQuantity("NIU", new BigDecimal("2000"));
            det1.setLineExtensionAmount(CurrencyCodeContentType.PEN, new BigDecimal("149491.53"));
            det1.setPricingReferenceAlternativeConditionPricePriceAmount(CurrencyCodeContentType.PEN, new BigDecimal("98.00"));
            det1.setPricingReferenceAlternativeConditionPricePriceTypeCode(CodigoTipoPrecioVentaUnit.PRECIO_UNITARIO); //cambiar
            det1.setTaxTotalTaxAmount(new BigDecimal("26908.47"), CurrencyCodeContentType.PEN);
            det1.setTaxTotalTaxSubtotalTaxAmount(new BigDecimal("26908.47"), CurrencyCodeContentType.PEN);
            det1.setTaxTotalTaxSubtotalTaxCategoryTaxExemptionReasonCode(CodigoTipoAfectacionIgv.GRABADO_OPERACION_ONEROSA);
            det1.setTaxTotalTaxSubtotalTaxCategoryTaxScheme(CodigoTipoTributo.IGV);
            det1.setItemDescription("Grabadora LG Externo Modelo: GE20LU10");
            det1.setItemDescriptionSellersItemIdentificationId("GLG199");
            det1.setPricePriceAmount(new BigDecimal("83.05"), CurrencyCodeContentType.PEN);
            invoice.addInvoiceLine(det1);
            
            invoice.generar();
        } catch (JAXBException ex) {
            Logger.getLogger(FacturaElectronicaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(FacturaElectronicaTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    @Test
    public void unMarshallFacturaElectronica() {
        try {

            File file = new File("F:\\factura.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<InvoiceType> invoice = (JAXBElement<InvoiceType>) jaxbUnmarshaller.unmarshal(file);
            System.out.println(invoice.toString());

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendBill(){
        
        try { // Call Web Service Operation
            BillService_Service service = new BillService_Service();
            service.setHandlerResolver(new HeaderHandlerResolver());
            BillService port = service.getBillServicePort();
            // TODO initialize WS operation arguments here
            java.lang.String fileName = "10428482072-01-F001-1.zip";
            DataSource fds = new FileDataSource("F:\\10428482072-01-F001-1.zip");
            javax.activation.DataHandler contentFile = new javax.activation.DataHandler(fds);
            // TODO process result here
            byte[] result = port.sendBill(fileName, contentFile);
            System.out.println("Result = "+result);
        }catch(SOAPFaultException ex){
            System.out.println(ex.getFault().getFaultCode());
            System.out.println(ex.getFault().getFaultString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
    }
}
