package com.bfs.papertoss;

import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* loaded from: classes.dex */
public class PTWebViewClient extends WebViewClient {
    @Override // android.webkit.WebViewClient
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }

    @Override // android.webkit.WebViewClient
    public void onPageFinished(WebView view, String url) {
        CookieSyncManager.getInstance().sync();
        super.onPageFinished(view, url);
    }
}
