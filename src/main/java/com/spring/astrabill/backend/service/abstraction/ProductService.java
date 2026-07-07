package com.spring.astrabill.backend.service.abstraction;

import com.spring.astrabill.backend.dto.ProductDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.User;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, UserDTO userDTO);
    ProductDTO updateProduct(String id, ProductDTO productDTO, UserDTO userDTO);
    void deleteProduct(String id, UserDTO userDTO);
    ProductDTO getProductById(String id);
    List<ProductDTO> getAllProductsByStore(String storeId);
    List<ProductDTO> searchByKeyword(String query, String storeId);

}
