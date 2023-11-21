package com.shiyu.web01;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.junit.jupiter.api.Test;

public class SslContextUtilTest {
    @Test
    public void testGetSslContext() throws Exception {
        // Load the keystore
        File clientJks = new File("src/test/resources/client.jks");
        if (!clientJks.exists()) {
            throw new FileNotFoundException("Keystore file not found: " +
                    clientJks.getAbsolutePath());
        }

        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(clientJks), "123456".toCharArray());

        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            System.out.println("alias: " + alias);
            Certificate[] certificateChain = keyStore.getCertificateChain(alias);
            for (Certificate certificate : certificateChain) {
                X509Certificate x509Certificate = (X509Certificate) certificate;
                System.out.println("x509Certificate: " + x509Certificate.getSigAlgName());
                x509Certificate.checkValidity();
            }
        }

    }
}
