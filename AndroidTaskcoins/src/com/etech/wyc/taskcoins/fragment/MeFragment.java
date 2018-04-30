package com.etech.wyc.taskcoins.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.etech.wyc.taskcoins.R;
import com.etech.wyc.taskcoins.activity.MainActivity;
import com.etech.wyc.taskcoins.activity.SubjectActivity;
import com.etech.wyc.taskcoins.common.HttpgetEntity;
import com.etech.wyc.taskcoins.common.HttppostEntity;
import com.etech.wyc.taskcoins.common.StringToJSON;
import com.etech.wyc.taskcoins.global.AppConst;

public class MeFragment extends Fragment{
	
	private HttpgetEntity getEntity;
	private HttppostEntity postEntity;
	
	private String get_personal_url = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/users/user_info?Uid=";
	
	private String update_personal_url = "http://" 
			+ AppConst.sServerURL 
			+ "/wyc/users/update_info?Uid=";
	
	private String Uid;
	
	private TextView tv1, tv2, tv3;
	private EditText et1, et2;
	private ViewGroup tv_top;
	private Button btn1;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getBd();
		View view = inflater.inflate(R.layout.fragment_me, container, false);
		new Thread(){
			public void run(){
				getMeText();
			}
		}.start();
		while(true){
			if(personal_text != null){
				break;
			}
		}
		init(view);
		return view;
	}
	
	private String personal_text = null;
	private void getMeText(){
		getEntity = new HttpgetEntity();
		try {
			Log.e("Url", get_personal_url + Uid);
			personal_text = getEntity.doGet(get_personal_url + Uid);
			Log.e("personal_text", personal_text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("getpersonal", "error");
			e.printStackTrace();
		}
	}
	
	private void getBd(){
		Uid = ((MainActivity)getActivity()).getUid();
	}
	
	private void init(View view){
		tv1 = (TextView)view.findViewById(R.id.tv_1);
		et1 = (EditText)view.findViewById(R.id.et_1);
		et2 = (EditText)view.findViewById(R.id.et_2);
		btn1 = (Button)view.findViewById(R.id.btn_1);
		tv_top = (ViewGroup)view.findViewById(R.id.tv_group);
		tv2 = (TextView)view.findViewById(R.id.tv_group).findViewById(R.id.tv_1);
		tv3 = (TextView)view.findViewById(R.id.tv_group).findViewById(R.id.tv_2);
		tv2.setText("个人中心");
		tv3.setText("编辑");
		tv3.setOnClickListener(edit);
		btn1.setOnClickListener(subject);
		et1.setFocusable(false);
		et1.setFocusableInTouchMode(false);
		et2.setFocusable(false);
		et2.setFocusableInTouchMode(false);
		
		setMeText(personal_text);
	}
	
	String result_of_post;
	private OnClickListener edit = new OnClickListener(){

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			if(tv3.getText().toString().equals("编辑")){
				tv3.setText("确定");
				et1.setFocusable(true);
				et1.setFocusableInTouchMode(true);
				et2.setFocusable(true);
				et2.setFocusableInTouchMode(true);
			}else if(tv3.getText().toString().equals("确定")){
				getText();
				new Thread(){
					public void run(){
						JSONObject obj = new JSONObject();
						try {
							obj.put("Uname", Uname);
							obj.put("Usex", Usex);
							postEntity = new HttppostEntity();
							result_of_post = postEntity.doPost(obj, update_personal_url + Uid);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				tv3.setText("编辑");
				et1.setFocusable(false);
				et1.setFocusableInTouchMode(false);
				et2.setFocusable(false);
				et2.setFocusableInTouchMode(false);
			}
			
		}
		
	};
	
	private String Uname, string_Usex;
	private int Usex;
	private void getText(){
		Uname = et1.getText().toString();
		string_Usex = et2.getText().toString();
		if(string_Usex.equals("男")){
			Usex = 301;
		}else if(string_Usex.equals("女")){
			Usex = 302;
		}else{
			Usex = 303;
		}
	}
	
	private OnClickListener subject = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(), SubjectActivity.class);
			intent.putExtra("index", 1);
			intent.putExtra("Uid", Uid);
			startActivity(intent);
			getActivity().finish();
		}
		
	};
	
	private void setMeText(String personal_text){
		if(personal_text == null){
			Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
		}else{
			final JSONObject json_obj = StringToJSON.toJSONObject(personal_text);
			if(json_obj.optInt("status") == 200){
				new Thread(){
					public void run(){
						String string_data = json_obj.optString("data");
						JSONObject json_data = StringToJSON.toJSONObject(string_data);
						if(json_obj.optString("Uname").length() == 0){
							et1.setText(json_obj.optString("Utel"));
						}else{
							et1.setText(json_obj.optString("Uname"));
						}
						if(json_obj.optInt("Usex") == 301){
							et2.setText("男");
						}else if(json_obj.optInt("Usex") == 302){
							et2.setText("女");
						}else{
							
						}
						if(json_obj.optInt("Ustatus") == 101){
							tv1.setText("未审核");
						}else if(json_obj.optInt("Ustatus") == 102){
							tv1.setText("审核中");
						}else if(json_obj.optInt("Ustatus") == 103){
							tv1.setText("审核通过");
						}else if(json_obj.optInt("Ustatus") == 104){
							tv1.setText("审核未通过");
						}
						
					}
				}.start();
			}else{
				Toast.makeText(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
			}
		}
	}
}
