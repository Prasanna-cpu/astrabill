package com.spring.astrabill.backend.mapper;

import com.spring.astrabill.backend.dto.ProductDTO;
import com.spring.astrabill.backend.entity.Product;

public class ProductMapper {

    public static ProductDTO mapToProductDTO(Product product) {

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setStockKeepingUnit(product.getStockKeepingUnit());
        productDTO.setMaximumRetailPrice(product.getMaximumRetailPrice());
        productDTO.setSellingPrice(product.getSellingPrice());
        productDTO.setBrand(product.getBrand());
        productDTO.setImage(product.getImage());

        if(product.getStore() != null){
            productDTO.setStoreId(product.getStore().getId());
            productDTO.setStore(StoreMapper.mapToStoreDTO(product.getStore()));
        }

        return productDTO;

    }

    public static Product mapToProduct(ProductDTO productDTO){
        Product product = new Product();

        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }

        product.setName(productDTO.getName());
        product.setStockKeepingUnit(productDTO.getStockKeepingUnit());
        product.setMaximumRetailPrice(productDTO.getMaximumRetailPrice());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setBrand(productDTO.getBrand());
        product.setImage(productDTO.getImage());

        if(productDTO.getStoreId() != null){
            product.setStore(StoreMapper.mapToStore(productDTO.getStore()));}

        return product;
    }

    public static void updateProductFromProductDTO(ProductDTO productDTO, Product product){
        if (productDTO.getName() != null) {
            product.setName(productDTO.getName());
        }

        if (productDTO.getStockKeepingUnit() != null) {
            product.setStockKeepingUnit(productDTO.getStockKeepingUnit());
        }

        if (productDTO.getDescription() != null) {
            product.setDescription(productDTO.getDescription());
        }

        if (productDTO.getMaximumRetailPrice() != null) {
            product.setMaximumRetailPrice(productDTO.getMaximumRetailPrice());
        }

        if (productDTO.getSellingPrice() != null) {
            product.setSellingPrice(productDTO.getSellingPrice());
        }

        if (productDTO.getBrand() != null) {
            product.setBrand(productDTO.getBrand());
        }

        if (productDTO.getImage() != null) {
            product.setImage(productDTO.getImage());
        }
    }

}
