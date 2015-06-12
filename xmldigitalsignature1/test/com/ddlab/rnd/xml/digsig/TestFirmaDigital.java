/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddlab.rnd.xml.digsig;

import java.io.File;
import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author christian
 */
public class TestFirmaDigital {
    
    public TestFirmaDigital() {
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
    public void firmar(){
        String xmlFilePath = "xml" + File.separator + "invoice.xml";
        String signedXmlFilePath = "xml" + File.separator + "digitallysignedInvoice.xml";
        String privateKeyFilePath = "keys" + File.separator + "privatekey.key";
        String publicKeyFilePath = "keys" + File.separator + "publickey.key";
        XmlDigitalSignatureGenerator xmlSig = new XmlDigitalSignatureGenerator();
        try {
            xmlSig.generateXMLDigitalSignature(xmlFilePath, signedXmlFilePath, privateKeyFilePath, publicKeyFilePath);
        } catch (KeyStoreException ex) {
            Logger.getLogger(TestFirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestFirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TestFirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(TestFirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnrecoverableEntryException ex) {
            Logger.getLogger(TestFirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
