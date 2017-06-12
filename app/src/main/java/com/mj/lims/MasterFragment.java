package com.mj.lims;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MasterFragment extends Fragment {


    private static final String ARG_URL = "kbsjds";
    private WebView webView;

    public MasterFragment() {
        // Required empty public constructor
    }


    public static MasterFragment newInstance(String url) {
        MasterFragment fragment = new MasterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        String url = "http://medium.com";
        Bundle bundle = this.getArguments();
        if (bundle != null) url = bundle.getString(ARG_URL);

        View root = inflater.inflate(R.layout.fragment_master, container, false);
        webView = (WebView) root.findViewById(R.id.webview_master);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        webView.setWebViewClient(new WebClient());
        webView.loadUrl(url);
        return  root;
    }


    class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            MyApp.log("Loading url: "+ request.toString());
            return true;
        }

    }

}
