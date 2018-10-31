package com.example.admin_linux.csdevproject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;

public class EnterPhoneActivity extends AppCompatActivity {

    Spinner spinner;
    LayoutInflater mInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone);

        setContentView(R.layout.activity_enter_phone);
        spinner = findViewById(R.id.spinner_activity_enter_phone);
        spinner.setAdapter(new NewAdapter());
        mInf = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    class NewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInf.inflate(R.layout.list_item_spinner, null);
            }
            return convertView;
        }

    }
}
