package com.example.network.domains.models;

public class BasketParams {
    public Integer idProduct;
    public Integer count;
    public BasketParams(Integer idProduct, Integer count){
        this.idProduct = idProduct;
        this.count = count;
    }
}
