package com.aims.son.repositories;

import com.aims.son.controller.repositories.IMediaRepository;
import com.aims.son.entity.media.Media;
import com.aims.son.repositories.DbConnection.SQLiteConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MediaRepository implements IMediaRepository {
    protected final Connection connection;

    public MediaRepository() {
        connection = SQLiteConnectionManager.getConnection();
    }

    @Override
    public Media findById(int id) throws SQLException {
        String sql = "SELECT * FROM Media WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return mapResultSetToMedia(res);
            }
        }
        return null;
    }

    @Override
    public List<Media> findAll() throws SQLException {
        List<Media> mediaList = new ArrayList<>();
        String sql = "SELECT * FROM Media";
        try (Statement stm = connection.createStatement()) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                mediaList.add(mapResultSetToMedia(res));
            }
        }
        return mediaList;
    }

    protected void saveMedia(Media media) throws SQLException {
        String sql = "INSERT INTO Media (title, category, value, price, quantity, type, imageUrl, weight) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stm.setString(1, media.getTitle());
            stm.setString(2, media.getCategory());
            stm.setInt(3, media.getValue());
            stm.setInt(4, media.getPrice());
            stm.setInt(5, media.getQuantity());
            stm.setString(6, media.getType());
            stm.setString(7, media.getImageURL());
            stm.setDouble(8, media.getWeight());

            stm.executeUpdate();

            try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    media.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    protected void deleteMedia(int id) throws SQLException {
        String sql = "DELETE FROM Media WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        }
    }

    protected Media mapResultSetToMedia(ResultSet res) throws SQLException {
        return new Media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"))
                .setWeight(res.getDouble("weight"));
    }
}
