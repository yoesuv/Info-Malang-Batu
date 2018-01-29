package com.yoesuv.infomalangbatu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.yoesuv.infomalangbatu.R;
import com.yoesuv.infomalangbatu.model.ListPlace;

public class ListPlaceAdapter extends ArrayAdapter<ListPlace> {

    public ListPlaceAdapter(Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_list_place, parent, false);
            holder.thumbnail = convertView.findViewById(R.id.imageView_thumbnail);
            holder.nama = convertView.findViewById(R.id.textView_nama);
            holder.lokasi = convertView.findViewById(R.id.textView_lokasi);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ListPlace lp = getItem(position);
        if(lp!=null) {
            holder.nama.setText(lp.getNama());
            holder.lokasi.setText(lp.getLokasi());
            final ViewHolder tmp = holder;
            Picasso.with(getContext()).load(lp.getThumbnail()).placeholder(R.drawable.img_default).into(holder.thumbnail, new Callback() {
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

    private class ViewHolder{
        ImageView thumbnail;
        TextView nama;
        TextView lokasi;
    }
}
