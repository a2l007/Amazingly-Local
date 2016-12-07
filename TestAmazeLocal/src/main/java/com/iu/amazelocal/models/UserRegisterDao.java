package com.iu.amazelocal.models;

public class UserRegisterDao {
	public String regPeriod;
	public float buyerCount;
	public UserRegisterDao(String payP, float buyers){
		this.regPeriod=payP;
		this.buyerCount=buyers;
	}
	public String getRegPeriod() {
		return regPeriod;
	}
	public void setRegPeriod(String regPeriod) {
		this.regPeriod = regPeriod;
	}
	public float getBuyerCount() {
		return buyerCount;
	}
	public void setBuyerCount(float buyerCount) {
		this.buyerCount = buyerCount;
	}
}
