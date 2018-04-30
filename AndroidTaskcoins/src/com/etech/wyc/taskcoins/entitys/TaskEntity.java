package com.etech.wyc.taskcoins.entitys;

public class TaskEntity {
	
	private String Tid;
	private String Tname;
	private String Tcointype;
	private double Tcoinnum;
	private String Tstatus;
	private String Ttype;
	
	public String getTid() {
		return Tid;
	}
	public void setTid(String tid) {
		Tid = tid;
	}
	public String getTname() {
		return Tname;
	}
	public void setTname(String tname) {
		Tname = tname;
	}
	public String getTcointype() {
		return Tcointype;
	}
	public void setTcointype(String tcointype) {
		Tcointype = tcointype;
	}
	public double getTcoinnum() {
		return Tcoinnum;
	}
	public void setTcoinnum(double tcoinnum) {
		Tcoinnum = tcoinnum;
	}
	public String getTstatus() {
		return Tstatus;
	}
	public void setTstatus(String tstatus) {
		Tstatus = tstatus;
	}
	public String getTtype() {
		return Ttype;
	}
	public void setTtype(String ttype) {
		Ttype = ttype;
	}
}
