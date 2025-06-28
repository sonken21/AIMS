package com.aims.son.repositories;

import com.aims.son.controller.repositories.ICDRepository;
import com.aims.son.entity.media.CD;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CDRepository extends MediaRepository implements ICDRepository {
    public CDRepository() throws SQLException {
    }

    @Override
    public CD findCDById(int id) throws SQLException {
        String sql = "SELECT * FROM CD " +
                     "INNER JOIN Media ON Media.id = CD.id " +
                     "WHERE Media.id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return mapResultSetToCD(res);
            }
        }
        return null;
    }

    @Override
    public List<CD> findAllCDs() throws SQLException {
        List<CD> cds = new ArrayList<>();
        String sql = "SELECT * FROM CD INNER JOIN Media ON Media.id = CD.id";
        try (Statement stm = connection.createStatement()) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                cds.add(mapResultSetToCD(res));
            }
        }
        return cds;
    }

    @Override
    public void saveCD(CD cd) throws SQLException {
        super.saveMedia(cd);  // Saves the media-related information in the Media table
        String sql = "INSERT INTO CD (id, artist, recordLabel, musicType, releasedDate) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, cd.getId());
            stm.setString(2, cd.getArtist());
            stm.setString(3, cd.getRecordLabel());
            stm.setString(4, cd.getMusicType());
            stm.setDate(5, new java.sql.Date(cd.getReleasedDate().getTime()));

            stm.executeUpdate();
        }
    }

    @Override
    public void deleteCD(int id) throws SQLException {
        String sql = "DELETE FROM CD WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        }

        super.deleteMedia(id);  // Deletes the media-related information from the Media table
    }

    private CD mapResultSetToCD(ResultSet res) throws SQLException {
        return new CD(
                res.getInt("id"),
                res.getString("title"),
                res.getString("category"),
                res.getInt("value"),
                res.getInt("price"),
                res.getInt("quantity"),
                res.getString("type"),
                res.getString("imageURL"),
                res.getDouble("weight"),
                res.getString("artist"),
                res.getString("recordLabel"),
                res.getString("musicType"),
                res.getDate("releasedDate")
        );
    }
}
