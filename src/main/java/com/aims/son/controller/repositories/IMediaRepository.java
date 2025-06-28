package com.aims.son.controller.repositories;

import com.aims.son.entity.media.Media;

import java.sql.SQLException;
import java.util.List;

public interface IMediaRepository {
    Media findById(int id) throws SQLException;
    List<Media> findAll() throws SQLException;
}
