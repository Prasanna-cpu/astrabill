package com.spring.astrabill.backend.controller;

import com.spring.astrabill.backend.dto.ProductDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.response.ApiResponse;
import com.spring.astrabill.backend.service.abstraction.ProductService;
import com.spring.astrabill.backend.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final UserService userService;
    private final ProductService productService;

    @PostMapping("/create")
    private final ResponseEntity<ApiResponse> createProductHandler(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwt){
        UserDTO userDTO = userService.getUserByAccessToken(jwt);
        ProductDTO createdProductDTO = productService.createProduct(productDTO, userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        createdProductDTO,
                        "Product Created Successfully",
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }

    @PutMapping("/product/update/{id}")
    public ResponseEntity<ApiResponse> updateProductHandler(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String jwt, @PathVariable String id){
        UserDTO userDTO = userService.getUserByAccessToken(jwt);
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        updatedProductDTO,
                        "Product Updated Successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProductHandler(@PathVariable String id, @RequestHeader("Authorization") String jwt){
        UserDTO userDTO = userService.getUserByAccessToken(jwt);
        productService.deleteProduct(id, userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        null,
                        "Product Deleted",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getProductByIdHandler(@PathVariable String id){
        ProductDTO productDTO = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        productDTO,
                        "Product Found Successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<ApiResponse> getAllProductsByStoreHandler(@PathVariable String storeId){
        List<ProductDTO> productDTOS = productService.getAllProductsByStore(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        productDTOS,
                        "All Products Found Successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<ApiResponse> searchKeywordHandler(@RequestParam(name = "query") String query, @PathVariable String storeId){
        List<ProductDTO> productDTOS = productService.searchByKeyword(query, storeId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        productDTOS,
                        "Search Results Found Successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

}
