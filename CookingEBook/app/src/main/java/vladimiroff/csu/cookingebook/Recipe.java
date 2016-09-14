package vladimiroff.csu.cookingebook;

public class Recipe {
    private String name;
    private int image;
    private String recipe;

    public Recipe(String name, int image, String recipe) {
        this.name = name;
        this.image = image;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }

    public String getRecipe() {
        return recipe;
    }
}
