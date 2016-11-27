package com.iu.amazelocal.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.iu.amazelocal.config.ConnectionFactory;
import com.iu.amazelocal.models.Recipe;
import com.iu.amazelocal.models.Users;
import com.iu.amazelocal.utils.AppConstants;
public class RecipeCrud {
    public void insertRecipe(Recipe r) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = ConnectionFactory.getConnObject();
            // here sonoo is database name, root is username and password
            String insertTableSQL = "INSERT INTO AL_RECIPES " + "VALUES " + "(?,?,?,?,?,?)";
            Long recipeId=AppConstants.RECIPEIDSEQ;
            PreparedStatement stmt = con.prepareStatement(insertTableSQL);
            Long newRecipeId=UserCrud.fetchLatestId("AL_RECIPES", "RecipeId");
            if(newRecipeId> 1L){
                recipeId=newRecipeId+1;
            }
            stmt.setLong(1, recipeId);
            stmt.setString(2, r.getRecipeName());
            stmt.setString(3, r.getIngredients());
            stmt.setString(4, r.getInstructions());
            stmt.setString(5, r.getRecipeImage());
            stmt.setString(6, r.getDescription());
            stmt.executeUpdate();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}