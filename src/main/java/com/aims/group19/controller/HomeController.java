package com.aims.group19.controller;

import com.aims.group19.controller.repositories.IMediaRepository;
import com.aims.group19.entity.media.Media;
import com.aims.group19.repositories.MediaRepository;

import java.sql.SQLException;
import java.util.List;


/**
 * This class controls the flow of events in homescreen
 * @author nguyenlm
 */
public class HomeController extends BaseController{

    private IMediaRepository mediaRepository;

    public HomeController(IMediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    /**
     * this method gets all Media in DB and return back to home to display
     * @return List[Media]
     * @throws SQLException
     */
    @SuppressWarnings("rawtypes")
    public List getAllMedia() throws SQLException{
        return mediaRepository.findAll();
    }

}
