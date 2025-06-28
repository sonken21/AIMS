package com.aims.son.controller.repositories;

import com.aims.son.entity.media.DVD;

import java.sql.SQLException;
import java.util.List;
public interface IDVDRepository extends IMediaRepository {
    DVD findDVDById(int id) throws SQLException;
    List<DVD> findAllDVDs() throws SQLException;
    void saveDVD(DVD dvd) throws SQLException;
    void deleteDVD(int id) throws SQLException;
}