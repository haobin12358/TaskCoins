package com.etech.wyc.taskcoins.adapter;

import java.util.List;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.entitys.TaskEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter{
	
	private Context context;
	private List<TaskEntity> entitys;
	
	public TaskAdapter(Context context, List<TaskEntity> entitys){
		this.context = context;
		this.entitys = entitys;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return entitys.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return entitys.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_task, null);
			holder.Tname = (TextView)convertView.findViewById(R.id.tv_2);
			holder.Tcoin = (TextView)convertView.findViewById(R.id.tv_4);
			holder.Ttype = (TextView)convertView.findViewById(R.id.tv_6);
			holder.Tstatus = (TextView)convertView.findViewById(R.id.tv_8);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		TaskEntity entity = entitys.get(position);
		holder.Tname.setText(entity.getTname());
		holder.Tcoin.setText(entity.getTcointype() + entity.getTcoinnum());
		holder.Tstatus.setText(entity.getTstatus());
		holder.Ttype.setText(entity.getTtype());
		convertView.setTag(holder);
		return convertView;
	}
	
	
	
	public class ViewHolder{
		private TextView Tname, Tcoin, Tstatus, Ttype;
	}

}
