package com.javaclient.demo.dao;

import com.javaclient.demo.model.VideoCardDo;
import com.javaclient.demo.model.VideoDo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class VideoCardDao {

    public List<VideoCardDo> list()  {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
        }catch (Exception e){
            e.printStackTrace();
        }


        List<VideoCardDo> list = new ArrayList<>();


        VideoCardDo videoCardDo = new VideoCardDo();
        videoCardDo.setId(1);
        videoCardDo.setTitle("hot videos");

        VideoDo videoDo1 = new VideoDo(1,"spring cloud in depth","topden.com",100);
        VideoDo videoDo2 = new VideoDo(2,"spring cloud 111111","topden.com",200);
        VideoDo videoDo3 = new VideoDo(3,"spring cloud 2222222","topden.com",300);
        VideoDo videoDo4 = new VideoDo(4,"spring cloud 3333333","topden.com",400);


        List<VideoDo> videoDoList = new ArrayList<>();

        videoDoList.add(videoDo1);
        videoDoList.add(videoDo2);
        videoDoList.add(videoDo3);
        videoDoList.add(videoDo4);
        videoCardDo.setList(videoDoList);



        VideoCardDo videoCardDo2 = new VideoCardDo();
        videoCardDo.setId(1);
        videoCardDo.setTitle("live example Spring project");

        VideoDo videoDo5 = new VideoDo(1,"spring cloud in depth project","topden.com",100);
        VideoDo videoDo6 = new VideoDo(2,"spring cloud 111111 project","topden.com",200);
        VideoDo videoDo7 = new VideoDo(3,"spring cloud 2222222 project ","topden.com",300);
        VideoDo videoDo8 = new VideoDo(4,"spring cloud 3333333 project","topden.com",400);


        List<VideoDo> videoDoList2 = new ArrayList<>();

        videoDoList2.add(videoDo5);
        videoDoList2.add(videoDo6);
        videoDoList2.add(videoDo7);
        videoDoList2.add(videoDo8);
        videoCardDo2.setList(videoDoList2);

        list.add(videoCardDo);
        list.add(videoCardDo2);

    return list;
    }
}
