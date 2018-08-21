package com.rbware.flickrapi.ui;

/* Copyright (c) 8/21/18. Created by Ryan Bailey */

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.rbware.flickrapi.R;
import com.rbware.flickrapi.network.model.FlickrPhoto;

public class FlickrImageAdapter extends RecyclerView.Adapter<FlickrImageAdapter.ViewHolder> {

    private List<FlickrPhoto> mPhotoList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public FlickrImageAdapter(Context context, List<FlickrPhoto> items) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mPhotoList = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        FlickrPhoto photoItem = mPhotoList.get(position);

        holder.titleView.setText(photoItem.getTitle());

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        Glide
                .with(mContext)
                .load(photoItem.getImageUrl())
                .apply(options)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageWidth = resource.getIntrinsicWidth();
                        holder.imageHeight = resource.getIntrinsicHeight();
                        holder.imageView.setImageDrawable(resource);

                        int byteCount = Integer.MIN_VALUE;
                        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.KITKAT){
                            byteCount = ((BitmapDrawable)resource).getBitmap().getByteCount();
                        }else{
                            byteCount = ((BitmapDrawable)resource).getBitmap().getAllocationByteCount();
                        }
                        int sizeInKB = byteCount / 1024;
                        int sizeInMB = sizeInKB / 1024;
                        holder.imageFileSize = sizeInMB;
                    }
                });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titleView;

        private int imageWidth;
        private int imageHeight;
        private int imageFileSize;

        ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView
                    .findViewById(R.id.icon);
            titleView = (TextView) itemView.findViewById(R.id.title);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

        public ImageView getImageView() {
            return imageView;
        }

        public int getImageFileSize() {
            return imageFileSize;
        }

        public int getImageHeight() {
            return imageHeight;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public TextView getTitleView() {
            return titleView;
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
