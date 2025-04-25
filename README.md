# 🍲 RecipeGenerator

`RecipeGenerator` is a Java-based console application that suggests recipes based on the ingredients you have. It connects to a MySQL database of recipes and helps users discover what meals they can prepare — even if they’re missing one or two ingredients!

---

## 📌 Features

- ✅ Loads recipes from a MySQL database
- 🧾 Accepts comma-separated ingredients as input
- 🍛 Suggests recipes you can make with exact ingredients
- 💡 Shows recipes you can *almost* make (missing up to 2 ingredients)
- 🧂 Automatically includes salt in every recipe and user input

---

## 🛠️ Technologies Used

- Java
- JDBC (Java Database Connectivity)
- MySQL
- Java Streams and Collections API

---

## 📂 Project Structure

📘 How it Works
Class Structure
RecipeGenerator: Main class that handles user input and recipe matching.

Recipe: A static inner class that stores recipe data like name, ingredients, instructions, etc.

Key Methods
loadRecipesFromDatabase(): Fetches and parses recipes from the MySQL database.

findPossibleRecipes(...): Filters recipes that match all user-provided ingredients.

findCloseMatches(...): Suggests recipes that are only missing up to 2 ingredients.

getMissingIngredients(...): Lists what ingredients the user is missing for a near match.

✅ To-Do / Future Improvements
 Add a GUI using JavaFX or Swing

 Implement fuzzy matching for ingredient names

 Add categories or tags (e.g., vegetarian, vegan, gluten-free)

 Save user preferences
