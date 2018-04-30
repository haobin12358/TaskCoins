package com.etech.wyc.taskcoins.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.activity.AddTaskActivity;
import com.etech.wyc.taskcoins.activity.MainActivity;
import com.etech.wyc.taskcoins.activity.TaskaboActivity;
import com.etech.wyc.taskcoins.adapter.TaskAdapter;
import com.etech.wyc.taskcoins.common.HttpgetEntity;
import com.etech.wyc.taskcoins.common.StringToJSON;
import com.etech.wyc.taskcoins.entitys.TaskEntity;
import com.etech.wyc.taskcoins.global.AppConst;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TaskFragment extends Fragment{

	private String Uid;
	private HttpgetEntity getEntity;
	
	private ListView lst;
	private TaskAdapter adapter;
	private List<TaskEntity> entitys = new ArrayList<TaskEntity>();
	
	private ViewGroup tv_top;
	private TextView tv1, tv2;
	
	private String url_get_tasklist = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/tasks/task_list";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.fragment_taskcoin_list, container, false);
		new Thread(){
			public void run(){
				getTaskList();
			}
		}.start();
		while(true){
			if(task_list != null){
				break;
			}
		}
		init(view);
		return view;
	}
	
	private void init(View view){
		tv_top = (ViewGroup)view.findViewById(R.id.tv_group);
		lst = (ListView)view.findViewById(R.id.lst);
		adapter = new TaskAdapter(getActivity(), entitys);
		lst.setAdapter(adapter);
		lst.setOnItemClickListener(get_abo);
		tv1 = (TextView)view.findViewById(R.id.tv_group).findViewById(R.id.tv_1);
		tv2 = (TextView)view.findViewById(R.id.tv_group).findViewById(R.id.tv_2);
		tv1.setText("悬赏");
		tv2.setText("发布");
		tv2.setOnClickListener(add);
		
		setText(task_list);
	}
	
	private OnClickListener add = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), AddTaskActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("index", 0);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private OnItemClickListener get_abo = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), TaskaboActivity.class);
			intent.putExtra("Uid", Uid);
			intent.putExtra("Tid", entitys.get(position).getTid());
			intent.putExtra("index", 0);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private String task_list;
	private void getTaskList(){
		getEntity = new HttpgetEntity();
		try {
			task_list = getEntity.doGet(url_get_tasklist);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setText(String task_list){
		if(task_list == null){
			Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
		}else{
			JSONObject json_response = StringToJSON.toJSONObject(task_list);
			if(json_response.optInt("status") == 200){
				try{
					String string_data = json_response.optString("data");
					JSONArray json_data = StringToJSON.toJSONArray(string_data);
					entitys.clear();
					for(int i = 0; i < json_data.length(); i++){
						JSONObject json_item = json_data.getJSONObject(i);
						TaskEntity entity = new TaskEntity();
						entity.setTid(json_item.optString("Tid"));
						entity.setTcointype(deal_cointype(json_item.optInt("Tcointype")));
						entity.setTcoinnum(json_item.optDouble("Tcoinnum"));
						entity.setTname(json_item.optString("Tname"));
						entity.setTstatus(deal_status(json_item.optInt("Tstatus")));
						entity.setTtype(deal_type(json_item.optInt("Ttype")));
						entitys.add(entity);
					}
				}catch(Exception e){
					
				}
			}else{
				Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
			}
		}
	}
	
	private String deal_cointype(int cointype){
		String deal_cointype = "";
		if(cointype == 401){
			deal_cointype = "￥";
		}else if(cointype == 402){
			deal_cointype = "$";
		}else if(cointype == 403){
			deal_cointype = "英镑";
		}else if(cointype == 404){
			deal_cointype = "欧元";
		}else if(cointype == 405){
			deal_cointype = "卢布";
		}else{
			deal_cointype = "未知币种";
		}
		return deal_cointype;
	}
	
	private String deal_status(int status){
		String deal_status = "";
		if(status == 601){
			deal_status = "未接受";
		}else if(status == 602){
			deal_status = "已接受";
		}else if(status == 603){
			deal_status = "已完成";
		}else if(status == 604){
			deal_status = "已废弃";
		}else{
			deal_status = "未知状态";
		}
		return deal_status;
	}
	
	private String deal_type(int type){
		String deal_type = "";
		if(type == 501){
			deal_type = "生活类";
		}else if(type == 502){
			deal_type = "线下类";
		}else{
			deal_type = "未知";
		}
		return deal_type;
	}
}
