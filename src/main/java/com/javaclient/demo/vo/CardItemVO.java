package com.javaclient.demo.vo;

import lombok.Data;

/**
 * @author Blue_Sky 7/8/21
 */
//VO means value object and DO means Data Object

@Data
public class CardItemVO {
    /**
     * Commodity id
     */
    private Integer productId;

    /**
     * Purchase quantity
     */
    private Integer buyNum;

    /**
     * Product title
     */
    private String productTitle;

    /**
     * Picture
     */
    private String productImg;

    /**
     * Commodity price
     */
    private int price;

    /**
     * Total price, unit price + quantity
     */
    private int totalPrice;

    public int getTotalPrice(){
        return this.price * this.buyNum;
    }

}
