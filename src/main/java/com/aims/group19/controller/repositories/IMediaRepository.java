package com.aims.group19.controller.repositories;

import com.aims.group19.entity.media.Media;

import java.sql.SQLException;
import java.util.List;

public interface IMediaRepository {
    Media findById(int id) throws SQLException;
    List<Media> findAll() throws SQLException;
}
