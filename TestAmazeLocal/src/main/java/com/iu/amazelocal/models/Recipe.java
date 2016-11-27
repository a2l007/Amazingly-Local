package com.iu.amazelocal.models;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
public class Recipe {
      private long RecipeId;
          
      private String RecipeName;
      private String Ingredients;
      
      private String Instructions;
      private String RecipeImage;
      private String Description;
      public long getRecipeId() {
        return RecipeId;
    }
    public void setRecipeId(long recipeId) {
        RecipeId = recipeId;
    }
    public String getRecipeName() {
        return RecipeName;
    }
    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }
    public String getIngredients() {
        return Ingredients;
    }
    public void setIngredients(String ingredients) {
        Ingredients = ingredients;
    }
    public String getInstructions() {
        return Instructions;
    }
     public void setInstructions(String instructions){
         Instructions=instructions;
     }
     
     public String getRecipeImage()
     {
         return RecipeImage;
     }
     public void getRecipeImage(String recipeImage)
     {
         RecipeImage=recipeImage;
     }
     public String getDescription()
     {
         return Description;
     }
    public void setDescription(String description)
    {
        Description=description;
    }
    
      public Recipe() { }
      public Recipe(long RecipeId) { 
        this.RecipeId = RecipeId;
      }
      public Recipe(String RecipeName, String Ingredients, String Instructions, String RecipeImage, String Description) {
        this.RecipeName = RecipeName;
        this.Ingredients = Ingredients;
        this.Instructions=Instructions;
        this.RecipeImage=RecipeImage;
        this.Description=Description;
      }
}