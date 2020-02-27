package io.xbird.freefalldetection.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.xbird.freefalldetection.R;
import io.xbird.freefalldetection.database.FallEntity;

public class FallAdapter extends RecyclerView.Adapter<FallAdapter.ViewHolder> {

    private final List<FallEntity> mFalls;
    private final Context mContext;

    public FallAdapter(List<FallEntity> falls, Context mContext) {
        this.mFalls = falls;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fall_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final FallEntity fall = mFalls.get(position);
        holder.mTextView.setText(fall.getDuration());

    }


    @Override
    public int getItemCount() {
        return mFalls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.note_text);
        }
    }
}
