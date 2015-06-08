/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ddlab.rnd.xml.digsig;

import java.io.File;

/**
 * This class is used as a test class to sign an xml document digitally.
 *
 * @author <a href="mailto:debadatta.mishra@gmail.com">Debadatta Mishra</a>
 * @since 2013
 */
public class TestDigitalSignature {
    /*
     * Main method to generate a digitally signed xml document.
     */
    public static void main(String[] args) {
        String xmlFilePath = "xml" + File.separator + "employeesalary.xml";
        String signedXmlFilePath = "xml" + File.separator + "digitallysignedEmpSal.xml";
        String privateKeyFilePath = "keys" + File.separator + "privatekey.key";
        String publicKeyFilePath = "keys" + File.separator + "publickey.key";
        XmlDigitalSignatureGenerator xmlSig = new XmlDigitalSignatureGenerator();
        xmlSig.generateXMLDigitalSignature(xmlFilePath, signedXmlFilePath, privateKeyFilePath, publicKeyFilePath);
    }
}
