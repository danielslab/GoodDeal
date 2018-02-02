package com.example.puneetchugh.gooddealapplication.model;

/**
 * Created by puneetchugh on 1/29/18.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductBestBuy {

    @SerializedName("customerReviewAverage")
    @Expose
    private Double customerReviewAverage;
    @SerializedName("customerReviewCount")
    @Expose
    private Integer customerReviewCount;
    @SerializedName("sku")
    @Expose
    private Integer sku;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("salePrice")
    @Expose
    private Double salePrice;
    @SerializedName("image")
    @Expose
    private String image;

    public Double getCustomerReviewAverage() {
        return customerReviewAverage;
    }

    public void setCustomerReviewAverage(Double customerReviewAverage) {
        this.customerReviewAverage = customerReviewAverage;
    }

    public Integer getCustomerReviewCount() {
        return customerReviewCount;
    }

    public void setCustomerReviewCount(Integer customerReviewCount) {
        this.customerReviewCount = customerReviewCount;
    }

    public Integer getSku() {
        return sku;
    }

    public void setSku(Integer sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}