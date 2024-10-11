package mealplanner;

import java.sql.*;

public class ManagerDB {
    String URL = "jdbc:postgresql://localhost:5432/meals_db";
    String Username = "postgres";
    String Password = "1111";
    Connection con;

    public ManagerDB() {
        try {
            this.con = DriverManager.getConnection(URL, Username, Password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTables() {
        try {
            con.prepareStatement("""
                    CREATE TABLE meals (
                        meal_id SERIAL PRIMARY KEY,
                        meal VARCHAR NOT NULL,
                        category VARCHAR NOT NULL
                    );
                    """).executeQuery();

            con.prepareStatement("""
                    CREATE TABLE ingredients (
                        ingredient_id SERIAL PRIMARY KEY,
                        ingredient VARCHAR NOT NULL,
                        meal_id INT UNIQUE,
                        FOREIGN KEY (meal_id) REFERENCES meals (meal_id)
                    );
                    """).executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void connect() {
        try {
            con = DriverManager.getConnection(URL, Username, Password);
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void addMeal(Meal meal) {
        try {
            PreparedStatement ps = con.prepareStatement
                    ("""
                        WITH insertedMeal AS (
                            INSERT INTO meals (category, meal)
                            VALUES (?, ?)
                            RETURNING meal_id
                        )
                        INSERT INTO ingredients (ingredient, meal_id)
                        SELECT ?, meal_id FROM insertedMeal;
                        """);

//                        PreparedStatement ps = con.prepareStatement
//                    (" INSERT INTO meals (category, meal) VALUES (?, ?);");
//            PreparedStatement ps2 = con.prepareStatement
//                    ("INSERT INTO ingredients (ingredient, meal_id) " +
//                            "VALUES (?, (SELECT meal_id FROM meals ORDER BY meal_id DESC LIMIT 1));");

            ps.setString(1, meal.getCategory());
            ps.setString(2, meal.getName());
            ps.setString(3, meal.getIngredients());

            System.out.println(ps.executeUpdate());
            System.out.println("The meal has been added!");
        } catch (SQLException e) {
            System.out.println("add fail");
            throw new RuntimeException(e);
        }
    }

    public void showAllMeals() {
        try {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM meals;");
            ResultSet rs2 = con.createStatement().executeQuery("SELECT * FROM ingredients;");

            while (rs.next() && rs2.next()) {

                System.out.printf("""
                Category: %s
                Name: %s
                Ingredients:
                %s
                """, rs.getString("category"), rs.getString("meal"),
                        rs2.getString("ingredient"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
