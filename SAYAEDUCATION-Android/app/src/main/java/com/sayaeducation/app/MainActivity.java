package com.sayaeducation.app;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
  private static final String START_URL="https://sayaeducation-preview.vercel.app/";
  private WebView browser;
  private ProgressBar progress;
  @SuppressLint("SetJavaScriptEnabled")
  @Override public void onCreate(Bundle state) {
    super.onCreate(state);
    LinearLayout root=new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);
    progress=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
    progress.setIndeterminate(true);
    progress.setVisibility(View.GONE);
    root.addView(progress,new LinearLayout.LayoutParams(-1,5));
    browser=new WebView(this);
    root.addView(browser,new LinearLayout.LayoutParams(-1,0,1));
    setContentView(root);
    WebSettings s=browser.getSettings();
    s.setJavaScriptEnabled(true);
    s.setDomStorageEnabled(true);
    s.setAllowFileAccess(false);
    s.setAllowContentAccess(false);
    s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
    CookieManager.getInstance().setAcceptCookie(true);
    CookieManager.getInstance().setAcceptThirdPartyCookies(browser,false);
    browser.setWebChromeClient(new WebChromeClient(){
       @Override public void onProgressChanged(WebView view,int value){progress.setVisibility(value>=100?View.GONE:View.VISIBLE);}
    });
    browser.setWebViewClient(new WebViewClient(){
       @Override public boolean shouldOverrideUrlLoading(WebView view,WebResourceRequest request){
          Uri u=request.getUrl();String scheme=u.getScheme(),host=u.getHost();
          if("https".equalsIgnoreCase(scheme)&&
             ("sayaeducation-preview.vercel.app".equalsIgnoreCase(host)||"sayaeducation.com".equalsIgnoreCase(host)))return false;
          if(!request.isForMainFrame())return false;
          if(!"https".equalsIgnoreCase(scheme)&&!"mailto".equalsIgnoreCase(scheme)&&!"tel".equalsIgnoreCase(scheme))return true;
          try{startActivity(new Intent(Intent.ACTION_VIEW,u));}catch(ActivityNotFoundException ignored){}
          return true;
       }
    });
    if(state==null)browser.loadUrl(START_URL);
    else browser.restoreState(state);
  }
  @Override public void onBackPressed(){if(browser!=null&&browser.canGoBack())browser.goBack();else super.onBackPressed();}
  @Override protected void onSaveInstanceState(Bundle out){super.onSaveInstanceState(out);if(browser!=null)browser.saveState(out);}
  @Override protected void onDestroy(){if(browser!=null){browser.stopLoading();browser.destroy();browser=null;}super.onDestroy();}
}
