package com.spring.webProject.dto;


public class UserDto{

	String uId;
	String uPw;
	String uName;
	String uPhone;//- ���͵� ������
	String uBirth;//YYYY-MM-DDs string
	String uGender; //���� or ���� 
	String uEmail;
	String uAdress;
	String uInterestedList; //���ɸ�� ;�� �����ؼ� �߰� �� ���� manager �ʿ�
	//int uisAdmin; //0�� �Ϲ�user 1�� admin
	int enabled;
	String authority;
	
	
	public String getuId() {
		return uId;
	}
	public void setuId(String uId) {
		this.uId = uId;
	}
	public String getuPw() {
		return uPw;
	}
	public void setuPw(String uPw) {
		this.uPw = uPw;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPhone() {
		return uPhone;
	}
	public void setuPhone(String uPhone) {
		this.uPhone = uPhone;
	}
	public String getuBirth() {
		return uBirth;
	}
	public void setuBirth(String uBirth) {
		this.uBirth = uBirth;
	}
	public String getuGender() {
		return uGender;
	}
	public void setuGender(String uGender) {
		this.uGender = uGender;
	}
	public String getuEmail() {
		return uEmail;
	}
	public void setuEmail(String uEmail) {
		this.uEmail = uEmail;
	}
	public String getuAdress() {
		return uAdress;
	}
	public void setuAdress(String uAdress) {
		this.uAdress = uAdress;
	}
	public String getuInterestedList() {
		return uInterestedList;
	}
	public void setuInterestedList(String uInterestedList) {
		this.uInterestedList = uInterestedList;
	}
	public int getEnabled() {
		return enabled;
	}
	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	
	
	
	
	
	
	
}
