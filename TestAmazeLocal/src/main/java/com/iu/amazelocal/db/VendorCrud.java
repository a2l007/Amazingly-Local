package com.iu.amazelocal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;

public class VendorCrud {
	
//	public ArrayList<Integer> fetchVendorIds(String vendorName){
//		Connection con = ConnectionFactory.getConnObject();
//		String selectVendorSql = " SELECT VendorId FROM AL_VENDORS WHERE VendorName= ?";
//		
//		try{
//			PreparedStatement stmt = con.prepareStatement(selectVendorSql);
//			stmt.setString(1, vendorName);
//			ResultSet res=stmt.executeQuery();
//			
//
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//			return null;
//		}
//		}
}
