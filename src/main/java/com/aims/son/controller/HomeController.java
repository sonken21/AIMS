package com.aims.son.controller;

import com.aims.son.controller.repositories.IMediaRepository;
import com.aims.son.entity.media.Media;
import com.aims.son.repositories.MediaRepository;

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
