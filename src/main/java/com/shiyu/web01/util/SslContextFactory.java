package com.shiyu.web01.util;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

public class SslContextFactory {
    private SSLContext serverSslContext = null;
    private SSLContext clientSslContext = null;

    public SslContextFactory()
            throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        String serverKeyStorePath = "file/jks/server.jks";
        String serverKeyStorePassword = "123456";

        String trustStorePath = "file/jks/root.jks";
        String trustStorePassword = "cccccc";

        String clientKeyStorePath = "file/jks/client.jks";
        String clientKeyStorePassword = "654321";

        if (serverSslContext == null) {
            serverSslContext = createServerSslContext(serverKeyStorePath, serverKeyStorePassword,
                    trustStorePath, trustStorePassword);
        }
        if (clientSslContext == null) {
            clientSslContext = createClientSslContext(clientKeyStorePath, clientKeyStorePassword,
                    trustStorePath, trustStorePassword);
        }
    }

    private SSLContext createServerSslContext(String keyStorePath,
                                                     String keyStorePassword,
                                                     String trustStorePath,
                                                     String trustStorePassword) throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(
                getKeyManagers(keyStorePath, keyStorePassword),
                getTrustManagers(trustStorePath, trustStorePassword),
                null);

        return sslContext;
    }

    private SSLContext createClientSslContext(String keyStorePath,
                                                     String keyStorePassword,
                                                     String trustStorePath,
                                                     String trustStorePassword) throws NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(
                getKeyManagers(keyStorePath, keyStorePassword),
                getTrustManagers(trustStorePath, trustStorePassword),
                null);

        return sslContext;
    }

    private KeyManager[] getKeyManagers(String keyStorePath, String keyStorePassword) throws
            KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        // get keyStore
        assert KeyStore.getDefaultType() == "JKS";
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(keyStorePath), keyStorePassword.toCharArray());
        // get key managers
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());
        KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
        return keyManagers;
    }

    private TrustManager[] getTrustManagers(String trustStorePath, String trustStorePassword)
            throws NoSuchAlgorithmException, KeyStoreException, IOException, CertificateException {
        // get trust managers
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        trustStore.load(new FileInputStream(trustStorePath), trustStorePassword.toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
        trustManagerFactory.init(trustStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        return trustManagers;
    }

    public SSLContext getServerSslContext() {
        return serverSslContext;
    }

    public void setServerSslContext(SSLContext serverSslContext) {
        this.serverSslContext = serverSslContext;
    }

    public SSLContext getClientSslContext() {
        return clientSslContext;
    }

    public void setClientSslContext(SSLContext clientSslContext) {
        this.clientSslContext = clientSslContext;
    }
}
