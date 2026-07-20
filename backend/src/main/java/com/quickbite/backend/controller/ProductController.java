package com.quickbite.backend.controller;


import com.quickbite.backend.dto.Response.ProductResponse;
import com.quickbite.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/v1/menu")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllItems(){
        List<ProductResponse> productResponses=productService.getAllAvailableITems();
        return new ResponseEntity<>(productResponses,HttpStatus.OK);
    }


}
