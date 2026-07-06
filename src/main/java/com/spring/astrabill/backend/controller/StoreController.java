package com.spring.astrabill.backend.controller;


import com.spring.astrabill.backend.dto.StoreDTO;
import com.spring.astrabill.backend.dto.UserDTO;
import com.spring.astrabill.backend.entity.Store;
import com.spring.astrabill.backend.enums.StoreStatus;
import com.spring.astrabill.backend.response.ApiResponse;
import com.spring.astrabill.backend.service.abstraction.StoreService;
import com.spring.astrabill.backend.service.abstraction.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
@Slf4j
public class StoreController {

    private final StoreService storeService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createStoreHandler(@RequestBody StoreDTO storeDTO, @RequestHeader("Authorization") String jwt) {
        UserDTO userDTO = userService.getUserByAccessToken(jwt);
        StoreDTO createdStoreDTO = storeService.createStore(storeDTO, userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse(
                        createdStoreDTO,
                        "Store Created",
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED
                )
        );
    }

    @GetMapping("/store/{id}")
    public ResponseEntity<ApiResponse> getStoreByIdHandler(@PathVariable String id){
        StoreDTO storeDTO = storeService.getStoreById(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        storeDTO,
                        "Store fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/all-stores")
    public ResponseEntity<ApiResponse> getAllStoresHandler(){
        List<StoreDTO> storeDTOS = storeService.getAllStores();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        storeDTOS,
                        "Stores fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/store-by-admin")
    public ResponseEntity<ApiResponse> getStoreByAdminHandler(){
        StoreDTO storeDTO = storeService.getStoreByAdminId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        storeDTO,
                        "Store fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/store-by-employee")
    public ResponseEntity<ApiResponse> getStoreByEmployee(){
        StoreDTO storeDTO = storeService.getStoreByEmployee();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        storeDTO,
                        "Store fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/update/store/{id}")
    public ResponseEntity<ApiResponse> updateStoreHandler(
            @PathVariable String id,
            @RequestBody StoreDTO storeDTO
    ){
        StoreDTO updatedStoreDTO = storeService.updateStore(storeDTO, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        updatedStoreDTO,
                        "Store updated successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @DeleteMapping("/delete/store/{id}")
    public ResponseEntity<ApiResponse> deleteStoreHandler(
            @PathVariable String id
    ){
        storeService.deleteStore(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        null,
                        "Store deleted successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @GetMapping("/employees/store/{storeId}")
    public ResponseEntity<ApiResponse> getEmployeesByStore(@PathVariable String storeId){
        List<UserDTO> employees = storeService.getEmployeesByStore(storeId);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        employees,
                        "Employees fetched successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }

    @PutMapping("/moderate/store/{id}")
    public ResponseEntity<ApiResponse> moderateStore(
            @RequestParam(name = "action") StoreStatus action,
            @PathVariable String id
    ) {
        StoreDTO storeDTO = storeService.moderateStore(action, id);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        storeDTO,
                        "Store moderated successfully",
                        HttpStatus.OK.value(),
                        HttpStatus.OK
                )
        );
    }


}
