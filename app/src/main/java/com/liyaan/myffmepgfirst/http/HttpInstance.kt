package com.liyaan.myffmepgfirst.http

import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

//初始化httpConnection
object HttpInstance {
    private var mHttpUrl: URL? = null
    private var mConnection: HttpURLConnection? = null
    val DO_NOT_VERIFY: HostnameVerifier = HostnameVerifier { hostname, session -> true }
    fun httpUrl(mUrl:String): HttpURLConnection? {
       mUrl?.let {
            mHttpUrl = URL(it)
            mHttpUrl?.let { mUrl->
                if (mUrl.protocol?.toLowerCase() == "https"){
                    trustAllHosts();
                    val connection = mUrl.openConnection() as HttpsURLConnection?
                    connection?.hostnameVerifier = DO_NOT_VERIFY
                    mConnection = connection
                }else{
                    mConnection = mUrl.openConnection() as HttpURLConnection?
                }
            }
            mConnection?.let { it1 -> httpConnection(it1) }
        }
        return mConnection
    }

    fun httpConnection(connection:HttpURLConnection) {
        connection.connectTimeout = 2000// 建立连接超时时间
        connection.readTimeout = 2000//数据传输超时时间，很重要，必须设置。
//            mConnection.setDoOutput(true); // 从连接中读取数据
        connection.useCaches = false // 禁止缓存
        connection.instanceFollowRedirects = true
        connection.setRequestProperty("Charset", "UTF-8");

    }

    private fun trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(x509Certificates: Array<X509Certificate?>?, s: String?) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(x509Certificates: Array<X509Certificate?>?,
                                                s: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

            }
        )

        // Install the all-trusting trust manager
        try {
            val sc: SSLContext = SSLContext.getInstance("TLS")
            sc.init(null, trustAllCerts, SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}