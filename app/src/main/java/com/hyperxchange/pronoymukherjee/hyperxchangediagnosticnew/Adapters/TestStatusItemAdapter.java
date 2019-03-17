package com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.Test;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.Objects.TestStatusItem;
import com.hyperxchange.pronoymukherjee.hyperxchangediagnosticnew.R;

import java.util.ArrayList;

public class TestStatusItemAdapter extends ArrayAdapter {
    ArrayList<Test> dataSet;

    public TestStatusItemAdapter(Activity activityContext, ArrayList<Test> dataSet) {
        super(activityContext, 0, dataSet);
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.test_status_list_item,
                    parent, false);
        }
        AppCompatTextView textView = view.findViewById(R.id.testStatusListTestName);
        ImageView imageView = view.findViewById(R.id.testStatusListTestImage);
        Test item = dataSet.get(position);
        textView.setText(item.getTestName());
        imageView.setImageResource(item.getTestIconID());
        return view;
    }
}
