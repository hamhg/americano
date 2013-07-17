package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.meeting79.R;

public class HeartListAdapter extends ArrayAdapter<String>{

	private Context context;
	private TextView state01, state02;
	
	public HeartListAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
		//return super.getCount();
		
	}
	
	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View row = convertView;
		
		if (row == null){
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.heart_list_item, parent, false);
		}

		state01 = (TextView) row.findViewById(R.id.heartState01);
		state02 = (TextView) row.findViewById(R.id.heartState02);
		
		state02.setText("인연을 위한 시간이 모두 지나버렸네요.");
		return row;
	}
}
