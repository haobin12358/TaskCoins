package com.etech.wyc.taskcoins.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.common.HttpgetEntity;
import com.etech.wyc.taskcoins.common.HttppostEntity;
import com.etech.wyc.taskcoins.global.AppConst;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SubjectActivity extends Activity{
	
	private TextView tv1, tv2, tv3, tv4;
	private EditText et1, et2;
	private ViewGroup tv_top;
	private RadioGroup rg1;
	private RadioButton rb1, rb2, rb3;
	private Button btn1;
	
	private String subject_info = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/users/subject_info?Uid=";
	
	private HttppostEntity postEntity;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_subject);
		getBd();
		init();
	}
	
	private void init(){
		tv_top = (ViewGroup)findViewById(R.id.tv_group);
		tv3 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_1);
		tv4 = (TextView)findViewById(R.id.tv_group).findViewById(R.id.tv_2);
		tv3.setText("填写审批信息");
		tv4.setText("返回");
		tv4.setOnClickListener(returnto);
		
		et1 = (EditText)findViewById(R.id.et_1);
		et2 = (EditText)findViewById(R.id.et_2);
		
		rg1 = (RadioGroup)findViewById(R.id.rg_group);
		rb1 = (RadioButton)findViewById(R.id.rb_1);
		rb2 = (RadioButton)findViewById(R.id.rb_2);
		rb3 = (RadioButton)findViewById(R.id.rb_3);
		
		btn1 = (Button)findViewById(R.id.btn_1);
		btn1.setOnClickListener(subject);
	}
	
	private OnClickListener returnto = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(SubjectActivity.this, MainActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private String response_post;
	private OnClickListener subject = new OnClickListener(){

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
			Intent intent = new Intent(SubjectActivity.this, MainActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			finish();
		}
		
	};
	
	private void postText(){
		JSONObject obj = new JSONObject();
		try {
			obj.put("Utruename", Utruename);
			obj.put("Ucardtype", Ucardtype);
			obj.put("Ucardno", Ucardno);
			response_post = postEntity.doPost(obj, subject_info + Uid);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
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
	
	private String Utruename, Ucardno;
	private int Ucardtype;
	private void getText(){
		Utruename = et1.getText().toString();
		Ucardno = et2.getText().toString();
		if(rb1.isChecked()){
			Ucardtype = 201;
		}else if(rb2.isChecked()){
			Ucardtype = 202;
		}else if(rb3.isChecked()){
			Ucardtype = 203;
		}else{
			Ucardtype = 204;
		}
	}

}
