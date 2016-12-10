package com.iu.amazelocal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.utils.AppConstants;

public class RecipeViewCrud {
	public ArrayList<Recipe> viewRecipe() {
		ArrayList<Recipe> RecipeList=new ArrayList<Recipe>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String selectTypeSQL = "SELECT * FROM AL_RECIPES";
			Statement stmt = con.createStatement();
			ResultSet allrecipe=stmt.executeQuery(selectTypeSQL);
			Recipe rec;
			while(allrecipe.next()){
				Recipe r=new Recipe(allrecipe.getString(2),allrecipe.getString(3),allrecipe.getString(4),allrecipe.getString(5),allrecipe.getString(6));
				r.setRecipeId(allrecipe.getLong(1));
				RecipeList.add(r);
			}
			return RecipeList;
		} 
		catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}