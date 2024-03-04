package com.example.demo.lib;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class CustomTrustManager {

    private static TrustManager[] trustManager = new TrustManager[1];

    private static class X509 implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            // 만약 인증이 필요하다면 server에 대한 정보를 처리하도록
            //    try{
            //          	KeyStore trustStore = KeyStore.getInstance("SSL");
            //            String cacertsPath = System.getProperty("java.home") + " /lib/security/cacerts";
            //          	trustStore.load(new FileInputStream(cacertsPath), "changeit".toCharArray());
            //
            //            //defaultAlogorithm은 X.509
            //            TrustManagerFactory tmf= TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //            tmf.init(trustStore);
            //            TrustManager[] tms = tmf.getTrustManagers();
            //            ( (X509TrustManager) tms[0].checkServerTrusted(chain, authType);
            //
            //          }catch ( KeyStoreException e) {
            //          	//keyStore Exception 처리
            //          }catch ( NoSuchAlgorithmException e){
            //
            //          }catch ( IOException e ) {
            //          }
            //출처: https://chinggin.tistory.com/566 [Once Run:티스토리]
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    public static void trustManagerInit() throws NoSuchAlgorithmException, KeyManagementException {
        trustManager[0] = new X509();
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustManager, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
}
