package com.aims.son.repositories;

import com.aims.son.controller.repositories.IDVDRepository;
import com.aims.son.entity.media.DVD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DVDRepository extends MediaRepository implements IDVDRepository {

    public DVDRepository() throws SQLException {
    }

    @Override
    public DVD findDVDById(int id) throws SQLException {
        String sql = "SELECT * FROM DVD " +
                     "INNER JOIN Media ON Media.id = DVD.id " +
                     "WHERE Media.id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            ResultSet res = stm.executeQuery();
            if (res.next()) {
                return mapResultSetToDVD(res);
            }
        }
        return null;
    }

    @Override
    public List<DVD> findAllDVDs() throws SQLException {
        List<DVD> dvds = new ArrayList<>();
        String sql = "SELECT * FROM DVD INNER JOIN Media ON Media.id = DVD.id";
        try (Statement stm = connection.createStatement()) {
            ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                dvds.add(mapResultSetToDVD(res));
            }
        }
        return dvds;
    }

    @Override
    public void saveDVD(DVD dvd) throws SQLException {
        super.saveMedia(dvd);  // Save the common media properties
        String sql = "INSERT INTO DVD (id, discType, director, runtime, studio, subtitles, releasedDate, filmType) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, dvd.getId());
            stm.setString(2, dvd.getDiscType());
            stm.setString(3, dvd.getDirector());
            stm.setInt(4, dvd.getRuntime());
            stm.setString(5, dvd.getStudio());
            stm.setString(6, dvd.getSubtitles());
            stm.setDate(7, new java.sql.Date(dvd.getReleasedDate().getTime()));
            stm.setString(8, dvd.getFilmType());
            stm.executeUpdate();
        }
    }

    @Override
    public void deleteDVD(int id) throws SQLException {
        String sql = "DELETE FROM DVD WHERE id = ?";
        try (PreparedStatement stm = connection.prepareStatement(sql)) {
            stm.setInt(1, id);
            stm.executeUpdate();
        }
        super.deleteMedia(id);  // Delete the media record associated with the DVD
    }

    private DVD mapResultSetToDVD(ResultSet res) throws SQLException {
        return new DVD(
                res.getInt("id"),
                res.getString("title"),
                res.getString("category"),
                res.getInt("value"),
                res.getInt("price"),
                res.getInt("quantity"),
                res.getString("type"),
                res.getString("imageURL"),
                res.getDouble("weight"),
                res.getString("discType"),
                res.getString("director"),
                res.getInt("runtime"),
                res.getString("studio"),
                res.getString("subtitles"),
                res.getDate("releasedDate"),
                res.getString("filmType")
        );
    }
}
