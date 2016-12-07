package com.iu.amazelocal.models;

public class VendorRevenueDao {

		public String payPeriod;
		public float Profit;
		public String vendorName;
		public String getVendorName() {
			return vendorName;
		}
		public void setVendorName(String vendorName) {
			this.vendorName = vendorName;
		}
		public VendorRevenueDao(float profit){
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
