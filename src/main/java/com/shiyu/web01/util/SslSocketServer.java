package com.shiyu.web01.util;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class SslSocketServer extends Thread {
    private Socket socket = null;
    private SSLServerSocket serverSocket = null;

    public SslSocketServer() throws UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
        SslContextFactory sslContextFactory = new SslContextFactory();
        SSLContext sslContext = sslContextFactory.getServerSslContext();
        serverSocket = (SSLServerSocket) sslContext.getServerSocketFactory()
                .createServerSocket(6666);
        serverSocket.setNeedClientAuth(true);
    }

    @Override
    public void run() {
        byte[] msgBytes = new byte[1024];
        try {
            socket = serverSocket.accept();
            socket.getInputStream().read(msgBytes);

            String msgFromClient = new String(msgBytes, "UTF-8");
            System.out.println("Server receive message: " + msgFromClient);

            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            new SslSocketServer().start();
            System.out.println("Server started.");
        } catch (UnrecoverableKeyException | CertificateException | KeyStoreException | IOException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }
}
