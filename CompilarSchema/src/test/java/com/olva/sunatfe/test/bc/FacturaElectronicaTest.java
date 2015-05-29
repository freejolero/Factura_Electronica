/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.test.bc;

import com.olva.sunatfe.be.AdditionalInformationTypeSunatAgg;
import com.olva.sunatfe.be.AdditionalMonetaryTotalType;
import com.olva.sunatfe.be.ExtensionContentType;
import com.olva.sunatfe.be.IDType;
import com.olva.sunatfe.be.InvoiceType;
import com.olva.sunatfe.be.ObjectFactory;
import com.olva.sunatfe.be.PayableAmountType;
import com.olva.sunatfe.be.UBLExtensionType;
import com.olva.sunatfe.be.UBLExtensionsType;
import com.olva.sunatfe.be.UBLVersionIDType;
import java.io.File;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Element;

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
            ObjectFactory ob = new ObjectFactory();
            IDType idAMonetaryTotal = ob.createIDType();
            idAMonetaryTotal.setValue("1001");
            PayableAmountType pa = ob.createPayableAmountType();
            pa.setValue(new BigDecimal("348199.15"));
            AdditionalMonetaryTotalType amtt = ob.createAdditionalMonetaryTotalType();
            amtt.setID(idAMonetaryTotal);
            amtt.setPayableAmount(pa);
            IDType idAMonetaryTotal2 = ob.createIDType();
            idAMonetaryTotal2.setValue("1003");
            AdditionalMonetaryTotalType amtt2 = ob.createAdditionalMonetaryTotalType();
            PayableAmountType pa2 = ob.createPayableAmountType();
            pa2.setValue(new BigDecimal("12350.00"));
            amtt2.setID(idAMonetaryTotal2);
            amtt2.setPayableAmount(pa2);
            AdditionalInformationTypeSunatAgg aits = ob.createAdditionalInformationTypeSunatAgg();            
            aits.getAdditionalMonetaryTotal().add(amtt);
            aits.getAdditionalMonetaryTotal().add(amtt2);
            ExtensionContentType ect = ob.createExtensionContentType();
            
            JAXBElement<AdditionalInformationTypeSunatAgg> jeAits = ob.createAdditionalInformation(aits);
            
//            Element elem = (Element) jeAits;
//            ect.setAny(elem);
            UBLExtensionType uet = ob.createUBLExtensionType();
            uet.setExtensionContent(ect);
            UBLExtensionsType uest = ob.createUBLExtensionsType();
            uest.getUBLExtension().add(uet);
            InvoiceType invoiceType = ob.createInvoiceType();
//            invoiceType.setUBLExtensions(uest);
            UBLVersionIDType lVersionIDType = ob.createUBLVersionIDType();
            lVersionIDType.setValue("2.0");
            invoiceType.setUBLVersionID(lVersionIDType);
            JAXBContext jc = JAXBContext.newInstance(ObjectFactory.class);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
            marshaller.marshal(ob.createInvoice(invoiceType), System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(FacturaElectronicaTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(FacturaElectronicaTest.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    @Test
    public void unMarshallFacturaElectronica(){
         try {
 
		File file = new File("F:\\factura.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
 
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		InvoiceType invoice = (InvoiceType) jaxbUnmarshaller.unmarshal(file);
		System.out.println(invoice);
 
	  } catch (JAXBException e) {
		e.printStackTrace();
	  }
    }
}
