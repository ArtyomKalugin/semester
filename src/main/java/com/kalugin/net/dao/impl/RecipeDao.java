package com.kalugin.net.dao.impl;

import com.kalugin.net.dao.Dao;
import com.kalugin.net.helper.PostgresConnectionHelper;
import com.kalugin.net.model.Recipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeDao implements Dao<Recipe> {
    public static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    private final Connection connection = PostgresConnectionHelper.getConnection();

    @Override
    public Recipe get(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM recipe";
            ResultSet resultSet = statement.executeQuery(sql);

            Recipe recipe = null;

            while (resultSet.next()) {
                if(resultSet.getInt("id") == id) {
                    recipe = new Recipe(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("title"),
                            resultSet.getString("text"),
                            resultSet.getString("photo"),
                            resultSet.getString("date")
                    );
                }
            }

            return recipe;
        } catch (SQLException throwables) {
            LOGGER.warn("Failed execute get query.", throwables);
            return null;
        }
    }

    @Override
    public List<Recipe> getAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM recipe";
            ResultSet resultSet = statement.executeQuery(sql);

            List<Recipe> recipes = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getString("photo"),
                        resultSet.getString("date")
                );

                recipes.add(recipe);
            }

            return recipes;
        } catch (SQLException throwables) {
            LOGGER.warn("Failed execute getAll query.", throwables);
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Recipe recipe) {
        String sql = "INSERT INTO recipe (user_id, title, text, photo, date) " +
                "VALUES (?, ?, ?, ?, ?);";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, recipe.getUserId());
            preparedStatement.setString(2, recipe.getTitle());
            preparedStatement.setString(3, recipe.getText());
            preparedStatement.setString(4, recipe.getPhoto());
            preparedStatement.setString(5, recipe.getData());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.warn("Failed to save new recipe.", throwables);
        }
    }

    public List<Recipe> getByTitle(String title) {
        try {
            String sql = "SELECT * FROM recipe WHERE title ILIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipes = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getString("photo"),
                        resultSet.getString("date")
                );

                recipes.add(recipe);
            }

            return recipes;
        } catch (SQLException throwables) {
            LOGGER.warn("Failed execute getAll query.", throwables);
            return new ArrayList<>();
        }
    }

    public List<Recipe> getByUserId(int userId) {
        try {
            String sql = "SELECT * FROM recipe WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipes = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getString("photo"),
                        resultSet.getString("date")
                );

                recipes.add(recipe);
            }

            return recipes;
        } catch (SQLException throwables) {
            LOGGER.warn("Failed execute getAll query.", throwables);
            return new ArrayList<>();
        }
    }

    public List<Recipe> getByUserIdAndTitle(int userId, String title) {
        try {
            String sql = "SELECT * FROM recipe WHERE user_id = ? AND title ILIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, "%" + title + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Recipe> recipes = new ArrayList<>();

            while (resultSet.next()) {
                Recipe recipe = new Recipe(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("title"),
                        resultSet.getString("text"),
                        resultSet.getString("photo"),
                        resultSet.getString("date")
                );

                recipes.add(recipe);
            }

            return recipes;
        } catch (SQLException throwables) {
            LOGGER.warn("Failed execute getAll query.", throwables);
            return new ArrayList<>();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM recipe WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            LOGGER.warn("Failed to delete recipe.", throwables);
        }
    }
}
