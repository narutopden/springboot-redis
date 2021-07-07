package com.javaclient.demo.service.impl;

import com.javaclient.demo.dao.VideoCardDao;
import com.javaclient.demo.model.VideoCardDo;
import com.javaclient.demo.service.VideoCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoCardServiceImpl implements VideoCardService {

    @Autowired
    VideoCardDao videoCardDao;


    @Override
    public List<VideoCardDo> list() {
        return videoCardDao.list();
    }
}
