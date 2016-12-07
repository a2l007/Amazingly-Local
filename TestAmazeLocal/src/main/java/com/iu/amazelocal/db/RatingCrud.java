package com.iu.amazelocal.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Rating;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.Inventory;
import com.iu.amazelocal.utils.AppConstants;
public class RatingCrud {
    public void insertRating(Rating r) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = ConnectionFactory.getConnObject();
            // here sonoo is database name, root is username and password
            String insertTableSQL = "INSERT INTO AL_RATING " + "VALUES " + "(?,?,?,?,?)";
            Long RatingId=AppConstants.RATINGIDSEQ;
            PreparedStatement stmt = con.prepareStatement(insertTableSQL);
            Long newRatingId=UserCrud.fetchLatestId("AL_RATING", "RatingId");
            if(newRatingId> 1L){
                RatingId=newRatingId+1;
            }
            stmt.setLong(1, RatingId);
            stmt.setLong(2, r.getInventoryId());
            stmt.setLong(3, r.getUserId());
            stmt.setLong(4, r.getRating());
            stmt.setString(5, r.getComment());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public ArrayList<Rating> displayRating(long InventoryId) {
    	ArrayList<Rating> RatingList=new ArrayList<Rating>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String selectTypeSQL = "SELECT InventoryId, UserId, IFNULL(Rating,0) AS Rating, Comments FROM AL_RATING WHERE InventoryId = ?";
			PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
			stmt.setLong(1, InventoryId);
			ResultSet displayRating=stmt.executeQuery();	
			while(displayRating.next())
			{
				Rating r=new Rating();			
			r.setInventoryId(displayRating.getLong("InventoryId"));
			r.setUserId(displayRating.getLong("UserId"));
			long rat=displayRating.getLong("Rating");
			r.setRating(rat);
			r.setComment(displayRating.getString("Comments"));
			RatingList.add(r);}
			return RatingList;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}