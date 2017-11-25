package com.example.abhishekdewan.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;
import okhttp3.TlsVersion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTests();
    }

    private void initTests() {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(180, TimeUnit.SECONDS);
        client.connectTimeout(180, TimeUnit.SECONDS);

        try {


            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream iStream = getApplicationContext().getResources().openRawResource(R.raw.lad);
            X509Certificate cert = (X509Certificate) cf.generateCertificate(iStream);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca",cert);


            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            // Creating an SSLSocketFactory that uses our TrustManager
            sslContext.init(null, tmf.getTrustManagers(), null);

//            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//            trustManagerFactory.init(keyStore);
//            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
//            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
//            sslContext.init(null, trustAllCerts, new SecureRandom());
            client.sslSocketFactory(sslContext.getSocketFactory());
//                    .hostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    });
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


//        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.COMPATIBLE_TLS)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .cipherSuites(
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA,
//                        CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384,
//                        CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA256,
//                        CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256,
//                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA,
//                        CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256,
//                        CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA256,
//                        CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA,
//                        CipherSuite.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA,
//                        CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA,
//                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
//                        CipherSuite.TLS_RSA_WITH_RC4_128_SHA,
//                        CipherSuite.TLS_RSA_WITH_RC4_128_MD5)
//                .build();
//
//        OkHttpClient.Builder client = new OkHttpClient.Builder()
//                .connectionSpecs(Collections.singletonList(spec))
//                .certificatePinner(new CertificatePinner.Builder()
//                        .add("*.aeshealth.com", "sha256/HBOflQow6Va1dN2O4ONjYY54hnqqvrLWGe7Utf6c/Fk=")
//                        .build());
//
//        try {
//            SSLContext sslContext = SSLContext.getInstance("TLS");
//            sslContext.init(null,null,new SecureRandom());
//            client.sslSocketFactory(sslContext.getSocketFactory())
//                        .hostnameVerifier(new HostnameVerifier() {
//                            @Override
//                            public boolean verify(String hostname, SSLSession session) {
//                                return true;
//                            }
//                        });
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://ae-us-aed-stg.aeshealth.com/aed/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();

        ApiInterface mInterface = retrofit.create(ApiInterface.class);

        mInterface.getCommitsByName().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.e(TAG, response.toString());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    final TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    Log.e(TAG,authType);
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                    Log.e(TAG,authType);
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[]{};
                }
            }
    };
}
