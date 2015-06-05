/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.test.bc;

import com.olva.sunatfe.be.Invoice;
import com.olva.sunatfe.be.InvoiceType;
import com.olva.sunatfe.be.ObjectFactory;
import com.olva.sunatfe.enu.CodigoConceptosTributarios;
import com.olva.sunatfe.enu.CodigoElementosAdicionalesComprobante;
import com.olva.sunatfe.service.BillService;
import com.olva.sunatfe.service.BillService_Service;
import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
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
    
//    public void sendBill(){
//        
//        try { // Call Web Service Operation
//            BillService_Service service = new BillService_Service();
//            BillService port = service.getBillServicePort();
//            // TODO initialize WS operation arguments here
//            java.lang.String fileName = "";
//            javax.activation.DataHandler contentFile = new javax.activation.DataHandler();
//            // TODO process result here
//            byte[] result = port.sendBill(fileName, contentFile);
//            System.out.println("Result = "+result);
//        } catch (Exception ex) {
//            // TODO handle custom exceptions here
//        } 
//    }
}
