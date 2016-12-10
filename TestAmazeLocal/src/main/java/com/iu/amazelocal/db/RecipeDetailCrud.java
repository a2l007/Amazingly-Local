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

public class RecipeDetailCrud {
	public Recipe displayRecipe(long RecipeId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = ConnectionFactory.getConnObject();
			// here sonoo is database name, root is username and password
			String selectTypeSQL = "SELECT * FROM AL_RECIPES WHERE RecipeId = ?";
			System.out.println("SQL IS "+selectTypeSQL);
			PreparedStatement stmt = con.prepareStatement(selectTypeSQL);
			stmt.setLong(1, RecipeId);
			ResultSet detailrecipe=stmt.executeQuery();	
			detailrecipe.next();
			Recipe r=new Recipe(detailrecipe.getString("RecipeName"),detailrecipe.getString("Ingredients"),detailrecipe.getString("Instructions"),detailrecipe.getString("RecipeImage"),detailrecipe.getString("Description"));
			r.setRecipeId(detailrecipe.getLong("RecipeId"));
			return r;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}