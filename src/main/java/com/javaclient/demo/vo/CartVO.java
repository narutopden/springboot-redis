package com.javaclient.demo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Blue_Sky 7/8/21
 */
@Data
public class CartVO {
    private List<CardItemVO> cartItems;

    private Integer totalAmount;

    /**
     * to get total amount from the card
     * @return
     */
    public int getTotalAmount(){
        return cartItems.stream().mapToInt(CardItemVO::getTotalPrice).sum();
    }
}
