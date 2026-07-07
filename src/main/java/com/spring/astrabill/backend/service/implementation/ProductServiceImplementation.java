package com.spring.astrabill.backend.service.implementation;

import com.spring.astrabill.backend.dto.ProductDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.Product;
import com.spring.astrabill.backend.entity.Store;
import com.spring.astrabill.backend.entity.User;
import com.spring.astrabill.backend.enums.UserRoles;
import com.spring.astrabill.backend.exceptions.ForbiddenActivityException;
import com.spring.astrabill.backend.exceptions.ObjectNotFoundException;
import com.spring.astrabill.backend.mapper.ProductMapper;
import com.spring.astrabill.backend.mapper.UserMapper;
import com.spring.astrabill.backend.repository.ProductRepository;
import com.spring.astrabill.backend.repository.StoreRepository;
import com.spring.astrabill.backend.repository.UserRepository;
import com.spring.astrabill.backend.service.abstraction.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {Exception.class, ObjectNotFoundException.class, RuntimeException.class, ForbiddenActivityException.class})
public class ProductServiceImplementation implements ProductService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Boolean checkAuthority(Store store, User user){
        if (user.getRole().equals(UserRoles.ROLE_STORE_MANAGER)) {
            return user.getStore().getId().equals(store.getId());
        }
        if (user.getRole().equals(UserRoles.ROLE_STORE_ADMIN)) {
            return store.getStoreAdmin().getId().equals(user.getId());
        }
        return false;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, UserDTO userDTO) {
        Store store = storeRepository.findById(productDTO.getStoreId())
                .orElseThrow(() -> new ObjectNotFoundException("Store not found with id: " + productDTO.getStoreId()));

        Boolean isAuthorized = checkAuthority(store, UserMapper.mapToUser(userDTO));
        if (!isAuthorized) {
            throw new ForbiddenActivityException("User is not authorized to create product");
        }
        Product product = ProductMapper.mapToProduct(productDTO);
        product.setStore(store);
        Product savedProduct = productRepository.save(product);
        ProductDTO savedProductDTO = ProductMapper.mapToProductDTO(savedProduct);
        return savedProductDTO;
    }

    @Override
    public ProductDTO updateProduct(String id, ProductDTO productDTO, UserDTO userDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id: " + id));

        Boolean isAuthorized = checkAuthority(product.getStore(), UserMapper.mapToUser(userDTO));

        if(!isAuthorized){
            throw new ForbiddenActivityException("User is not authorized to update this product");
        }


        ProductMapper.updateProductFromProductDTO(productDTO, product);
        if(productDTO.getStoreId() != null){
            Store store = storeRepository.findById(productDTO.getStoreId())
                    .orElseThrow(() -> new ObjectNotFoundException("Store not found with id: " + productDTO.getStoreId()));
            Boolean isAuthorizedForNewStore = checkAuthority(store, UserMapper.mapToUser(userDTO));
            if(!isAuthorizedForNewStore){
                throw new ForbiddenActivityException("User is not authorized to update this product to new store");
            }
            product.setStore(store);
        }
        Product savedProduct = productRepository.save(product);
        ProductDTO savedProductDTO = ProductMapper.mapToProductDTO(savedProduct);
        return savedProductDTO;


    }

    @Override
    public void deleteProduct(String id, UserDTO userDTO) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id: " + id));

        Boolean isAuthorized = checkAuthority(product.getStore(), UserMapper.mapToUser(userDTO));
        if(!isAuthorized){
            throw new ForbiddenActivityException("User is not authorized to delete this product");
        }

        productRepository.delete(product);

    }

    @Override
    public ProductDTO getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product not found with id: " + id));
        ProductDTO productDTO = ProductMapper.mapToProductDTO(product);
        return productDTO;
    }

    @Override
    public List<ProductDTO> getAllProductsByStore(String storeId) {
        List<Product> products = productRepository.findByStoreId(storeId);
        List<ProductDTO> productDTOS = products.stream().map(ProductMapper::mapToProductDTO).toList();
        return productDTOS;
    }

    @Override
    public List<ProductDTO> searchByKeyword(String query, String storeId) {
        List<Product> products = productRepository.searchByKeyword(query, storeId);
        List<ProductDTO> productDTOS = products.stream().map(ProductMapper::mapToProductDTO).toList();
        return productDTOS;
    }
}
