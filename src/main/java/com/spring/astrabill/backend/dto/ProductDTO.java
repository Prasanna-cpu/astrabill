package com.spring.astrabill.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDTO {

    private String id;

    private String name;

    private String stockKeepingUnit;

    private String description;

    private Double maximumRetailPrice;

    private Double sellingPrice;

    private String brand;

    private String image;

    private String storeId;

    private String categoryId;

    private StoreDTO store;
}
