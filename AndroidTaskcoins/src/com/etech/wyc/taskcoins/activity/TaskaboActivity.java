package com.etech.wyc.taskcoins.activity;

import org.json.JSONObject;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.common.HttpgetEntity;
import com.etech.wyc.taskcoins.common.HttppostEntity;
import com.etech.wyc.taskcoins.common.StringToJSON;
import com.etech.wyc.taskcoins.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TaskaboActivity extends Activity{

	private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
	private ViewGroup tv_top;
	private Button btn1, btn2;
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private String get_taskabo = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/tasks/task_abo?Tid=";
	
	private String do_task = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/tasks/get_task?Uid=";
	
	private String delete_task = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/tasks/update_tasks_status?Uid=";
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_abo);
		new Thread(){
			public void run(){
				getText();
			}
		}.start();
		getBd();
		init();
	}
	
	private String Uid;
	private int index;
	private String Tid;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			Tid = bd.getString("Tid");
			index = bd.getInt("index");
		}catch(Exception e){
			e.printStackTrace();
			index = 1;
		}
	}
	
	private void init(){
		tv1 = (TextView)findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_2);
		tv3 = (TextView)findViewById(R.id.tv_3);
		tv4 = (TextView)findViewById(R.id.tv_4);
		tv5 = (TextView)findViewById(R.id.tv_5);
		tv6 = (TextView)findViewById(R.id.tv_6);
		tv7 = (TextView)findViewById(R.id.tv_7);
		tv_top = (ViewGroup)findViewById(R.id.tv_group);
		tv8 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_1);
		tv8.setText("悬赏详情");
		tv9 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_2);
		tv9.setText("返回");
		tv9.setOnClickListener(returnto);
		btn1 = (Button)findViewById(R.id.btn_1);
		btn2 = (Button)findViewById(R.id.btn_2);
		
		btn1.setOnClickListener(delete);
		btn2.setOnClickListener(solve);
		
		//getText();
		setText(task_text);
	}
	
	private OnClickListener delete = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run(){
					JSONObject obj = new JSONObject();
					postEntity = new HttppostEntity();
					try {
						obj.put("Tstatus", 604);
						response = postEntity.doPost(obj, do_task + Uid + "?Tid=" + Tid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			while(true){
				if(response!=null){
					break;
				}
			}
			Intent intent = new Intent(TaskaboActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private String response;
	private OnClickListener solve = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			new Thread(){
				public void run(){
					JSONObject obj = new JSONObject();
					postEntity = new HttppostEntity();
					try {
						response = postEntity.doPost(obj, delete_task + Uid + "?Tid=" + Tid);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			while(true){
				if(response!=null){
					break;
				}
			}
			Intent intent = new Intent(TaskaboActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private String task_text = null;
	private void getText(){
		getEntity = new HttpgetEntity();
		try {
			Log.e("Url", get_taskabo + Tid);
			task_text = getEntity.doGet(get_taskabo + Tid);
			Log.e("personal_text", task_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("getpersonal", "error");
			e.printStackTrace();
		}
	}
	private void setText(String task_abo){
		if(task_abo != null){
			JSONObject json_task = StringToJSON.toJSONObject(task_abo);
			String string_data = json_task.optString("data");
			JSONObject json_data = StringToJSON.toJSONObject(string_data);
			tv1.setText(json_data.optString("Tname"));
			tv2.setText(deal_cointype(json_data.optInt("Tcointype")) + json_data.optDouble("Tcoinnum"));
			tv3.setText(deal_status(json_data.optInt("Tstatus")));
			tv4.setText(deal_type(json_data.optInt("Ttype")));
			tv5.setText(json_data.optString("Tlocation"));
			tv6.setText(json_data.optString("Uname"));
			tv7.setText(json_data.optString("Tabo"));
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
	
	private OnClickListener returnto = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(TaskaboActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
}
