/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddlab.rnd.xml.digsig;

import java.io.File;

/**
 * This class is used as a test class to verify a digitally signed XMl document.
 *
 * @author <a href="mailto:debadatta.mishra@gmail.com">Debadatta Mishra</a>
 * @since 2013
 */
public class TestVerifyXmlDigitalSignature {

    /**
     * Method used to validate an actual signed XML document
     */
    public static void testSignedXMLDoc() {
        String signedXmlFilePath = "xml" + File.separator + "digitallysignedEmpSal.xml";
        String publicKeyFilePath = "keys" + File.separator + "publickey.key";
        try {
            boolean validFlag = XmlDigitalSignatureVerifier.
                    isXmlDigitalSignatureValid(signedXmlFilePath, publicKeyFilePath);
            System.out.println("Validity of XML Digital Signature : " + validFlag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method used to validate a tampered signed XML document
     */
    public static void testSignedTamperedXMLDoc() {
        String signedXmlFilePath = "xml" + File.separator + "digitallytamperdEmpSal.xml";
        String publicKeyFilePath = "keys" + File.separator + "publickey.key";
        try {
            boolean validFlag = XmlDigitalSignatureVerifier.
                    isXmlDigitalSignatureValid(signedXmlFilePath, publicKeyFilePath);
            System.out.println("Validity of XML Digital Signature : " + validFlag);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //Test for Valid one
        testSignedXMLDoc();
        //Test for tampered one
        testSignedTamperedXMLDoc();
    }
}
