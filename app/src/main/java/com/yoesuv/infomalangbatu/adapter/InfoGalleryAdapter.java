package com.yoesuv.infomalangbatu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.model.InfoGallery;

public class InfoGalleryAdapter extends ArrayAdapter<InfoGallery> {

    public InfoGalleryAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ThumbnailHolder holder;
        if(convertView==null){
            holder = new ThumbnailHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_gallery_thumbnail, parent, false);
            holder.thumbnail = convertView.findViewById(R.id.image_gallery_thumbnail);
            convertView.setTag(holder);
        }else{
            holder = (ThumbnailHolder) convertView.getTag();
        }

        final ThumbnailHolder tmp = holder;
        InfoGallery ig = getItem(position);
        if(ig!=null) {
            Picasso.with(getContext()).load(ig.getThumbnail()).placeholder(R.drawable.img_default).into(holder.thumbnail, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    tmp.thumbnail.setImageResource(R.drawable.img_default);
                }
            });
        }

        return convertView;
    }

    private class ThumbnailHolder{
        ImageView thumbnail;
    }
}
