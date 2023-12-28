package com.fsse2309.project_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fsse2309.project_backend.domainObject.ProductDetailsDataOut;
import com.fsse2309.project_backend.domainObject.TransactionProductDataOut;

import java.math.BigDecimal;

public class ProductResponseDto {
    @JsonProperty("product_id")
    private Integer productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("has_stock")
    private boolean hasStock;


    public ProductResponseDto(ProductDetailsDataOut productDetailsDataOut) {
        this.productId = productDetailsDataOut.getPid();
        this.name = productDetailsDataOut.getName();
        this.imageUrl = productDetailsDataOut.getImageUrl();
        this.price = productDetailsDataOut.getPrice();
        this.hasStock = productDetailsDataOut.getHasStock() > 0 ;
    }

    public ProductResponseDto(TransactionProductDataOut transactionProductDataOut) {
        this.productId = transactionProductDataOut.getPid();
        this.name = transactionProductDataOut.getName();
        this.imageUrl = transactionProductDataOut.getImageUrl();
        this.price = transactionProductDataOut.getPrice();
        this.hasStock = transactionProductDataOut.getHasStock() > 0 ;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isHasStock() {
        return hasStock;
    }

    public void setHasStock(boolean hasStock) {
        this.hasStock = hasStock;
    }
}
