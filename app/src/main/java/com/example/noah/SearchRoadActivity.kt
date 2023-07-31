package com.example.noah

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class SearchRoadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_road)

        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(BridgeInterface(), "Android")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                //Android->Javascript 함수 호출
                webView.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }

        //최초 웹뷰 로드
        webView.loadUrl("https://noah-46984.web.app")

    }

    class BridgeInterface {

        @JavascriptInterface
        fun processDATA(data: String) {
            //다음(카카오) 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달
            val intent = Intent()
            intent.putExtra("data", data)
            Log.d("dataMove", data)
            (context as? Activity)?.run {
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }

}