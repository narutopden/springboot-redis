package com.javaclient.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Blue_Sky 7/8/21
 */
@Data
@TableName("product")
public class Product implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String title;
    private String coverImg;
    private String detail;
    private Integer amount;
    private Integer stock;
    private Date createTime;
}
