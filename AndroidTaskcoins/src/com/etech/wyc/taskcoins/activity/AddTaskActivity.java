package com.etech.wyc.taskcoins.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.common.HttppostEntity;
import com.etech.wyc.taskcoins.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AddTaskActivity extends Activity{
	
	private TextView tv1, tv2;
	private EditText et1, et2, et3, et4;
	private ViewGroup tv_top;
	private RadioGroup rg1;
	private RadioButton rb1, rb2;
	private Button btn1;
	
	private String add_task = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/tasks/new_task?Uid=";
	
	private HttppostEntity postEntity;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		getBd();
		init();
	}
	
	private void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_group);
		tv1 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_1);
		tv2 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_2);
		tv1.setText("填写悬赏信息");
		tv2.setText("返回");
		tv2.setOnClickListener(returnto);
		
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		et3 = (EditText)findViewById(R.id.et_3);
		et4 = (EditText)findViewById(R.id.et_4);
		
		rg1 = (RadioGroup)findViewById(R.id.rg_group);
		rb1 = (RadioButton)findViewById(R.id.rb_1);
		rb2 = (RadioButton)findViewById(R.id.rb_2);
		
		btn1 = (Button)findViewById(R.id.btn_1);
		btn1.setOnClickListener(add);
	}
	
	private OnClickListener returnto = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
			intent.putExtra("index", 0);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private String Uid;
	private int index;
	private void getBd(){
		Intent intent = getIntent();
		try{
			Bundle bd = intent.getExtras();
			Uid = bd.getString("Uid");
			index = bd.getInt("index");
		}catch(Exception e){
			e.printStackTrace();
			index = 1;
		}
	}
	
	private String response_post;
	private OnClickListener add = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			getText();
			new Thread(){
				public void run(){
					postText();
				}
			}.start();
			while(true){
				if(response_post != null){
					break;
				}
			}
			Intent intent = new Intent(AddTaskActivity.this, MainActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Tname", Tname);
			obj.put("Tcoinnum", Tcoinnum);
			obj.put("Tlocation", Tlocation);
			obj.put("Ttype", Ttype);
			obj.put("Tabo", Tabo);
			response_post = postEntity.doPost(obj, add_task + Uid);
			Log.e("log", response_post);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String Tname, Tlocation, Tabo;
	private double Tcoinnum;
	private int Ttype;
	private void getText(){
		Tname = et1.getText().toString();
		Tlocation = et3.getText().toString();
		Tabo = et4.getText().toString();
		Tcoinnum = Double.parseDouble(et2.getText().toString());
		if(rb1.isChecked()){
			Ttype = 501;
		}else if(rb2.isChecked()){
			Ttype = 502;
		}else{
			Ttype = 503;
		}
	}

}
