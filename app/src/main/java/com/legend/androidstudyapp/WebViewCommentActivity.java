package com.legend.androidstudyapp;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * date：2021/12/31 11:32
 *
 * @author wanglezhi
 * desc:
 */
public class WebViewCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_comment);
        WebView webView = findViewById(R.id.webview);
        FrameLayout fmContainer = findViewById(R.id.fm_bottom);
        //系统默认会通过手机浏览器打开网页，为了能够直接通过WebView显示网页，则必须设置
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //使用WebView加载显示url
                view.loadUrl(url);
                //返回true
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           super.onProgressChanged(view, newProgress);
                                           if (newProgress == 100) {
                                               CommentFragment commentFragment = new CommentFragment();
                                               FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                               fragmentTransaction.replace(R.id.fm_bottom, commentFragment).commit();
                                           }
                                       }
                                   }
        );
        webView.loadUrl("https://open-hl.toutiao.com/a7047515753733423647/?compact_mode=&showOriginalComments=true&utm_campaign=open&utm_medium=webview&sticky=1&isNews=1&label=push&showComments=0&utm_source=vivoliulanqi&vivo_news_source=1&vivo_news_comment_data=%7B%7D&vivo_news_comment_data_checksum=99914b932bd37a50b983c5e7c90ae93b");
        TextView textView = new TextView(this);
        textView.setBackgroundColor(getResources().getColor(R.color.red));
        textView.setTextColor(getResources().getColor(R.color.white));
        textView.setText("底部文案AAAAAAAAAAAAAAAAA");
        textView.setGravity(Gravity.CENTER);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,500);
        textView.setLayoutParams(layoutParams);
//        fmContainer.addView(textView);

    }

}