package com.zbc.bean;

import java.io.Serializable;

//要想使用redis存对象，一定要让实体类实现Serializable接口，否则会报错。
public class User implements Serializable{
	
	private String USER_ID;         // 用户id
	private String USERNAME; 		// 用户名
	private String PASSWORD; 		// 密码
	private String NAME; 			// 姓名
	private String LAST_LOGIN; 		// 最后登录时间
	private String IP; 				// 用户登录ip地址
	
	public User() {
		super();
	}
	
	public User(String uSER_ID, String uSERNAME, String pASSWORD, String nAME, String lAST_LOGIN, String iP) {
		super();
		USER_ID = uSER_ID;
		USERNAME = uSERNAME;
		PASSWORD = pASSWORD;
		if(nAME==null) {
			NAME = "";
		}else {
			NAME = nAME;
		}
		if(lAST_LOGIN==null) {
			LAST_LOGIN = "";
		}else {
			LAST_LOGIN = lAST_LOGIN;
		}
		if(iP==null) {
			IP = "";
		}else {
			IP = iP;
		}
		
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getLAST_LOGIN() {
		return LAST_LOGIN;
	}
	public void setLAST_LOGIN(String lAST_LOGIN) {
		LAST_LOGIN = lAST_LOGIN;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	
	@Override
	public String toString() {
		return "User [USER_ID=" + USER_ID + ", USERNAME=" + USERNAME + ", PASSWORD=" + PASSWORD + ", NAME=" + NAME
				+ ", LAST_LOGIN=" + LAST_LOGIN + ", IP=" + IP + "]";
	}
	
	
	
	
}
