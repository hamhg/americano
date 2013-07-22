package com.example.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meeting79.R;

public class ChatListAdapter extends ArrayAdapter<String> {

	private Context context;
	private TextView id, chat;
	private ImageView icon;

	public ChatListAdapter(Context context, int textViewResourceId,	List<String> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
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
			row = inflater.inflate(R.layout.chat_list_item, parent, false);
		}
		
		icon = (ImageView) row.findViewById(R.id.photo);
		id = (TextView) row.findViewById(R.id.id);
		chat = (TextView) row.findViewById(R.id.chat);
		
		icon.setImageResource(R.drawable.p3);
		id.setText("테스트");
		chat.setText("안녕하세요.");
		
		return row;
	}	
	
}
