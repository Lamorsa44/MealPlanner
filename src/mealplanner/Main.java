package mealplanner;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    String input = "";
    ManagerDB managerDB = new ManagerDB();
    boolean bye = false;

    managerDB.connect();

    while (!bye) {
      System.out.println("What would you like to do (add, show, exit)?");
      input = scanner.nextLine().trim();

      switch (input) {
        case "add" -> {
          Meal meal = new Meal();
          meal.fill();
          managerDB.addMeal(meal);
        }
        case "show" -> {
          managerDB.showAllMeals();
        }
        case "exit" -> {
          System.out.println("Bye!");
          bye = true;
        }
      }
    }
  }
}