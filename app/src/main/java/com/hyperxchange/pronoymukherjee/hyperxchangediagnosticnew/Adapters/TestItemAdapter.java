package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.ArrayList;

public class TestItemAdapter extends RecyclerView.Adapter<TestItemAdapter.ViewHolder> {
    private ArrayList<Test> dataset;

    public TestItemAdapter(ArrayList<Test> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public TestItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataset.get(position).getTestName());
        holder.imageView.setImageResource(dataset.get(position).getTestIconID());
    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.testName);
            imageView = itemView.findViewById(R.id.imageTest);
        }

    }
}
