package com.framgia.gotosalon.screen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.framgia.gotosalon.R;
import com.framgia.gotosalon.data.model.Salon;

import java.util.List;

public class SalonManageAdapter extends RecyclerView.Adapter<SalonManageAdapter.ViewHolder> {
    private static final int BASE_COUNT = 0;
    private static final int START_ITEM = 0;
    private List<Salon> mSalons;
    private Context mContext;
    private OnItemClickListener mListener;

    public SalonManageAdapter(Context context, List<Salon> salons) {
        mSalons = salons;
        mContext = context;
    }

    public void setData(List<Salon> salons) {
        mSalons.clear();
        mSalons.addAll(salons);
        notifyItemRangeChanged(START_ITEM, mSalons.size());
    }

    public void setOnClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_salon_manager, viewGroup, false);
        return new ViewHolder(itemView, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Salon salon = mSalons.get(i);
        viewHolder.bindData(mContext, salon);
    }

    @Override
    public int getItemCount() {
        return mSalons != null ? mSalons.size() : BASE_COUNT;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageSalon;
        private ImageView mImageDelete;
        private ImageView mImageEdit;
        private TextView mTextSalonNane;
        private TextView mTextSalonView;
        private TextView mTextSalonAddress;
        private OnItemClickListener mListener;

        public ViewHolder(final View view, final OnItemClickListener listener) {
            super(view);
            mImageSalon = view.findViewById(R.id.image_salon);
            mImageDelete = view.findViewById(R.id.image_delete);
            mImageEdit = view.findViewById(R.id.image_edit);
            mTextSalonNane = view.findViewById(R.id.text_salon_name);
            mTextSalonAddress = view.findViewById(R.id.text_salon_address);
            mTextSalonView = view.findViewById(R.id.text_view_number);
            mListener = listener;

            mImageSalon.setOnClickListener(this);
            mImageEdit.setOnClickListener(this);
            mImageDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    switch (v.getId()) {
                        case R.id.image_salon:
                            mListener.onItemClick(mImageSalon, position);
                            break;
                        case R.id.image_delete:
                            mListener.onItemClick(mImageDelete, position);
                            break;
                        case R.id.image_edit:
                            mListener.onItemClick(mImageEdit, position);
                            break;
                    }
                }
            }
        }

        private void bindData(final Context context, final Salon salon) {
            Glide.with(context).load(salon.getImageUrl()).into(mImageSalon);
            if (salon.getSalonView() > 1) {
                mTextSalonView.setText(salon.getSalonView() +
                        context.getResources().getString(R.string.title_views));
            } else {
                mTextSalonView.setText(salon.getSalonView() +
                        context.getResources().getString(R.string.title_view));
            }
            mTextSalonAddress.setText(salon.getSalonAddress());
            mTextSalonNane.setText(salon.getSalonName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
}
