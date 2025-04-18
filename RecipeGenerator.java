import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class RecipeGenerator {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/recipe_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    static class Recipe {
        List<String> ingredients;
        String instructions;
        int cookTime;
        String cuisine;
        String name;

        public Recipe(String name, List<String> ingredients, String instructions, int cookTime, String cuisine) {
            this.name = name;
            this.ingredients = ingredients;
            this.instructions = instructions;
            this.cookTime = cookTime;
            this.cuisine = cuisine;
        }
    }

    public static void main(String[] args) {
        List<Recipe> recipes = loadRecipesFromDatabase();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter ingredients you have (comma separated):");
            String input = scanner.nextLine();
            List<String> availableIngredients = Arrays.stream(input.split(","))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
            availableIngredients.add("salt"); // Always add salt to user's input

            List<Recipe> possibleRecipes = findPossibleRecipes(availableIngredients, recipes);

            if (possibleRecipes.isEmpty()) {
                System.out.println("No recipes found. Try different ingredients!");
            } else {
                System.out.println("\nYou can make:");
                for (Recipe recipe : possibleRecipes) {
                    System.out.println("\n► " + recipe.name + " (" + recipe.cuisine + ", " + recipe.cookTime + " mins)");
                    System.out.println("Required Ingredients: " + String.join(", ", recipe.ingredients));
                    System.out.println("Instructions:\n" + recipe.instructions);
                }
            }
        }
    }

    private static List<Recipe> loadRecipesFromDatabase() {
        List<Recipe> recipes = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM recipes");

            while (rs.next()) {
                String name = rs.getString("name");
                String ingredientsStr = rs.getString("ingredients");
                String instructions = rs.getString("instructions");
                int cookTime = rs.getInt("cook_time");
                String cuisine = rs.getString("cuisine");

                Set<String> ingredientsSet = new HashSet<>(Arrays.stream(ingredientsStr.split(","))
                        .map(String::trim)
                        .map(String::toLowerCase)
                        .collect(Collectors.toSet()));
                ingredientsSet.add("salt"); // Always add salt

                List<String> ingredients = new ArrayList<>(ingredientsSet);

                recipes.add(new Recipe(name, ingredients, instructions, cookTime, cuisine));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Error loading recipes from database: " + e);
        }

        return recipes;
    }

    private static List<Recipe> findPossibleRecipes(List<String> availableIngredients, List<Recipe> allRecipes) {
        return allRecipes.stream()
                .filter(recipe -> availableIngredients.containsAll(recipe.ingredients))
                .sorted(Comparator.comparingInt(r -> r.ingredients.size()))
                .collect(Collectors.toList());
    }
}
