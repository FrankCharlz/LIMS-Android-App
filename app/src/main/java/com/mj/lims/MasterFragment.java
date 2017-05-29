package com.mj.lims;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class MasterFragment extends Fragment {


    private static final String ARG_URL = "kbsjds";

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
        WebView webView = (WebView) root.findViewById(R.id.webview_master);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        return  root;
    }

}
