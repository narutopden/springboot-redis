package com.javaclient.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDo {
    private int id;

    private String title;

    private String img;

    private int price;
}
