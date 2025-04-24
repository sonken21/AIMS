package com.aims.group19.controller.repositories;

import com.aims.group19.entity.media.CD;

import java.sql.SQLException;
import java.util.List;

public interface ICDRepository extends IMediaRepository {
    CD findCDById(int id) throws SQLException;
    List<CD> findAllCDs() throws SQLException;
    void saveCD(CD cd) throws SQLException;
    void deleteCD(int id) throws SQLException;
}