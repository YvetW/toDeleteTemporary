package com.shiyu.web01.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SslSocketClient extends Thread {
    private SSLSocket socket = null;

    public SslSocketClient() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        SslContextFactory sslContextFactory = new SslContextFactory();
        SSLContext sslContext = sslContextFactory.getClientSslContext();
        socket = (SSLSocket) sslContext.getSocketFactory().createSocket(InetAddress.getLocalHost(), 6666);
    }

    @Override
    public void run() {
        byte[] msgBytes = null;
        try {
            msgBytes = "Hello, I am client.".getBytes("UTF-8");
            socket.getOutputStream().write(msgBytes, 0, msgBytes.length);
            socket.getOutputStream().flush();
            socket.close();
            System.out.println("Client send message: " + new String(msgBytes, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new SslSocketClient().start();
            System.out.println("Client started.");
        } catch (UnrecoverableKeyException | CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
