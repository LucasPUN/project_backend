package com.fsse2309.project_backend.domainObject;

import com.fsse2309.project_backend.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductDetailsDataOut {

    private Integer pid;
    private String name;
    private String imageUrl;
    private BigDecimal price;
    private Integer hasStock;
    private String description;

    public ProductDetailsDataOut(ProductEntity productEntity) {
        this.pid = productEntity.getPid();
        this.name = productEntity.getName();
        this.imageUrl = productEntity.getImageUrl();
        this.price = productEntity.getPrice();
        this.hasStock = productEntity.getHasStock();
        this.description = productEntity.getDescription();
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public Integer getHasStock() {
        return hasStock;
    }

    public void setHasStock(Integer hasStock) {
        this.hasStock = hasStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
