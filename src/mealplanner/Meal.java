package mealplanner;

import java.util.*;

public class Meal {
    Scanner scanner = new Scanner(System.in);
    static LinkedList<Meal> meals = new LinkedList<>();
    String category;
    String name;
    List<String> ingredients = new ArrayList<>();


    public void fill() {
        System.out.println("Which meal do you want to add (breakfast, lunch, dinner)?");
        setCategory(scanner.nextLine().trim());
        while (!Category.isCategory(getCategory())) {
            System.out.println("Wrong meal category! Choose from: breakfast, lunch, dinner.");
            setCategory(scanner.nextLine());
        }

        System.out.println("Input the meal's name:");
        setName(scanner.nextLine().trim());
        while (!getName().matches("[a-zA-Z\\s]+")) {
            System.out.println("Wrong format. Use letters only!");
            setName(scanner.nextLine().trim());
        }

        System.out.println("Input the ingredients:");
        String[] tmp = scanner.nextLine().split(",");
        while (Arrays.stream(tmp).anyMatch(s -> !s.trim().matches("[a-zA-Z][a-zA-Z\\s]*"))) {
            System.out.println("Wrong format. Use letters only!");
            tmp = scanner.nextLine().split(",");
        }

        Arrays.stream(tmp).forEach(m -> ingredients.add(m.trim()));
        meals.add(this);
        System.out.println("The meal has been added!");
    }

    public void printAll() {
        System.out.printf("""
                Category: %s
                Name: %s
                Ingredients:
                """, getCategory(), getName());

        ingredients.forEach(System.out::println);
    }

//    getters and setters

    public static void printMeals() {
        var meals = Meal.getMeals();

        if (meals.isEmpty()) {
            System.out.println("No meals saved. Add a meal first.");
        } else {
            meals.forEach(Meal::printAll);
        }

    }

    public static LinkedList<Meal> getMeals() {
        return meals;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients.stream().reduce((a, b) -> a + "\n" + b).orElse("");
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    enum Category {
        BREAKFAST("breakfast"),
        LUNCH("lunch"),
        DINNER("dinner");

        final String category;

        Category(String category) {
            this.category = category;
        }

        public String getCategory() {
            return category;
        }

        static boolean isCategory(String word) {
            for (Category value : Category.values()) {
                if (value.getCategory().equals(word)) {
                    return true;
                }
            }
            return false;
        }
    }
}
