package com.javaclient.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class VideoCardDo {

    private String title;

    private int id;

    private int weight;

    List<VideoDo> list;


}
