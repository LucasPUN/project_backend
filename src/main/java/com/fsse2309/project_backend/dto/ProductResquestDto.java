package com.fsse2309.project_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;

import java.math.BigDecimal;

public class ProductResquestDto {
   @JsonProperty("name")
    private String name;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("has_stock")
    private Integer hasStock;

    @JsonProperty("description")
    private String description;

    public ProductResquestDto(String name, String imageUrl, BigDecimal price, Integer hasStock, String description) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.hasStock = hasStock;
        this.description = description;
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
