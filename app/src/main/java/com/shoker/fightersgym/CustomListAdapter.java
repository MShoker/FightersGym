package com.shoker.fightersgym;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    Activity activity;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private LayoutInflater inflater;
    ArrayList<VideoDetails> singletons;

    public CustomListAdapter(Activity activity, ArrayList<VideoDetails> singletons) {
        this.activity = activity;
        this.singletons = singletons;
    }

    public int getCount() {
        return this.singletons.size();
    }

    public Object getItem(int i) {
        return this.singletons.get(i);
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (this.inflater == null) {
            this.inflater = (LayoutInflater) this.activity.getLayoutInflater();
            // getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.video_item, null);
        }
        if (this.imageLoader == null) {
            this.imageLoader = AppController.getInstance().getImageLoader();
        }
        NetworkImageView networkImageView = (NetworkImageView) convertView.findViewById(R.id.video_image);
        final TextView imgtitle = (TextView) convertView.findViewById(R.id.video_title);
        final TextView imgdesc = (TextView) convertView.findViewById(R.id.video_descriptio);
        final  TextView tvVideoID=(TextView)convertView.findViewById(R.id.tv_videoId);

        ((LinearLayout) convertView.findViewById(R.id.asser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChannelActivity channelActivity =(ChannelActivity)activity;
                channelActivity.play(tvVideoID.getText().toString());

            }
        });

        VideoDetails singleton = (VideoDetails) this.singletons.get(i);
        networkImageView.setImageUrl(singleton.getURL(), this.imageLoader);
        tvVideoID.setText(singleton.getVideoId());
        imgtitle.setText(singleton.getVideoName());
        imgdesc.setText(singleton.getVideoDesc());
        return convertView;
    }
}