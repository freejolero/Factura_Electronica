/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.olva.sunatfe.util;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

/**
 *
 * @author christian
 */
public class MyPrefixMapper extends NamespacePrefixMapper {

    private static final String EXT_PREFIX = "ext"; // DEFAULT NAMESPACE
    private static final String EXT_URI = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2";

    private static final String CBC_PREFIX = "cbc";
    private static final String CBC_URI = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean bln) {
        if (null != namespaceUri) {
            switch (namespaceUri) {
                case EXT_URI:
                    return EXT_PREFIX;
                case CBC_URI:
                    return CBC_PREFIX;
            }
        }
        return suggestion;
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[]{EXT_PREFIX, EXT_URI};
    }

}
