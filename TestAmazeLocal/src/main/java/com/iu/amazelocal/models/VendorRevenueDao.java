package com.iu.amazelocal.models;

public class VendorRevenueDao {

		public String payPeriod;
		public float Profit;
		public VendorRevenueDao(String payP, float profit){
			this.payPeriod=payP;
			this.Profit=profit;
		}
		public String getPayPeriod() {
			return payPeriod;
		}
		public void setPayPeriod(String payPeriod) {
			this.payPeriod = payPeriod;
		}
		public float getProfit() {
			return Profit;
		}
		public void setProfit(float profit) {
			Profit = profit;
		}
		
}
