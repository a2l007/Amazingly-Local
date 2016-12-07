package com.iu.amazelocal.db;

import java.sql.*;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Payment;
import com.iu.amazelocal.models.ShopCart;
import com.iu.amazelocal.utils.AppConstants;

public class PaymentCrud {

	public void insertCardPayment(Payment card ){
		
		String insertCardQuery = "INSERT INTO AL_PAYMENT_DETAILS " + "VALUES " + "(?,?,?,?,?,?,?)";
		try{
			Connection con = ConnectionFactory.getConnObject();
			
			System.out.println("In payment crud insertCardPayment :");
			Long paymentId=AppConstants.PAYMENTIDSEQ;
			
			Long newPaymentId=UserCrud.fetchLatestId("AL_PAYMENT_DETAILS", "PaymentId");
			
			if(newPaymentId!= 0L){
				paymentId = newPaymentId;
			}
			
			PreparedStatement stmt = con.prepareStatement(insertCardQuery);
			stmt.setLong(1, paymentId);
			stmt.setLong(2,card.getUserId());
			stmt.setString(3, card.getCardNumber());
			stmt.setString(4, card.getName());
			stmt.setString(5,card.getCVV());
			stmt.setInt(6, card.getExpiryMonth());
			stmt.setInt(7, card.getExpiryYear());
			stmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println(e);
		}
	}
	
	public ArrayList<Payment> getSavedCards(Long userId) {
		
		ArrayList<Payment> cards = new ArrayList<Payment>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			
			String selectCardQuery = "SELECT * FROM AL_PAYMENT_DETAILS WHERE UserId = ?";
			
			PreparedStatement stmt = con.prepareStatement(selectCardQuery);
			stmt.setLong(1, userId);
			
			ResultSet res=stmt.executeQuery();
			
			while(res.next()){
				Payment card = new Payment();
				card.setPaymentId(Long.parseLong(res.getString("PaymentId")));
				card.setCardNumber(res.getString("CardNumber"));
				card.setName(res.getString("NameOnCard"));
				card.setCVV(res.getString("CVVNumber"));
				card.setExpiryYear(Integer.parseInt(res.getString("ExpiryYear")));
				card.setExpiryMonth(Integer.parseInt(res.getString("ExpiryMonth")));
				cards.add(card);
			}
			con.close();
		} 
		  catch (Exception e) {
			System.out.println(e);
		}
		
		return cards;
	}
	
	public boolean deletePaymentCard(Payment card) {
		
		Connection con = ConnectionFactory.getConnObject();
		try {
			System.out.println("Delete payment card inside !!!");

			String deleteCardQuery = "DELETE FROM  AL_PAYMENT_DETAILS WHERE PaymentId = ? AND UserId = ?; ";
			PreparedStatement stmt = con.prepareStatement(deleteCardQuery);
			stmt.setLong(1, card.getPaymentId());
			stmt.setLong(2, card.getUserId());
			
			int rowsDeleted = stmt.executeUpdate();
			
			if (rowsDeleted > 0) {
			    System.out.println("Rows deleted successfully!");
			    return true;
			}
			con.close();
		} 
		catch(SQLException e){
			e.printStackTrace();
		}

		return false;
	}
	
}