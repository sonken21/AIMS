package com.aims.son.controller.repositories;

import com.aims.son.entity.media.CD;

import java.sql.SQLException;
import java.util.List;

public interface ICDRepository extends IMediaRepository {
    CD findCDById(int id) throws SQLException;
    List<CD> findAllCDs() throws SQLException;
    void saveCD(CD cd) throws SQLException;
    void deleteCD(int id) throws SQLException;
}