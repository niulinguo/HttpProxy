package com.lingo.httpproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private HttpUtils mHttpUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.tv_text);

        mHttpUtils = new HttpUtils();
        mHttpUtils.configSSLSocketFactory(newSSLSocketFactory());
    }


    private SSLSocketFactory newSSLSocketFactory() {

        try {
            KeyStore keyStore = KeyStore.getInstance("BKS");

            InputStream inputStream = getResources().openRawResource(R.raw.baidu);

            keyStore.load(inputStream, null);

            inputStream.close();

            SSLSocketFactory sslSocketFactory = new SSLSocketFactory(keyStore);

            sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);

            return sslSocketFactory;

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void requestHttp(View view) {
        mTextView.setText("");
        mHttpUtils.send(HttpRequest.HttpMethod.POST,
                "https://www.baidu.com",
                new RequestCallBack<String>() {
                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
//                        mTextView.setText(current + "/" + total);
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        mTextView.setText(responseInfo.result);
                    }

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        mTextView.setText(error.toString());
                    }
                });
    }
}
