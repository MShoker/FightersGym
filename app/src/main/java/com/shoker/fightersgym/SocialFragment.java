package com.shoker.fightersgym;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SocialFragment extends Fragment {

    int id;
    ProgressBar progressBar;
    WebView webView;
    View v;

    public static SocialFragment newInstance(int id){
        SocialFragment socialFragment = new SocialFragment();
        Bundle args = new Bundle();
        args.putInt("id",id);
        socialFragment.setArguments(args);
        return  socialFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.fragment_social, container, false);
        progressBar = v.findViewById(R.id.progressbar_social);
        webView = v.findViewById(R.id.wv_social);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                progressBar.setVisibility(View.VISIBLE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(final WebView view, String url) {

                progressBar.setVisibility(View.GONE);
            }
        } );
        webView.getSettings().setJavaScriptEnabled(true);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            id = savedInstanceState.getInt("id");
        }else {
            id=getArguments().getInt("id");
        }
        switch (id) {
            case 1:
                setWebUrl("https://www.facebook.com/fayjitrjym/");
                break;
            case 2:
                setWebUrl("https://www.instagram.com/fightersgymqenaclub/");
                break;
            case 3:
                setWebUrl("http://www.developers.android.com");
                break;

            default:
                break;
        }
    }
    public void setWebUrl(String url){
        webView.loadUrl(url);
    }

}

