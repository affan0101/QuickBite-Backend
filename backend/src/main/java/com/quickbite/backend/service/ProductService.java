package com.quickbite.backend.service;

import com.quickbite.backend.Repository.ProductRepository;
import com.quickbite.backend.dto.Response.ProductResponse;
import com.quickbite.backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductResponse> getAllAvailableITems(){
        List<Product> products=productRepository.findAll();

        return products.stream()
                .map(product -> ProductResponse
                        .builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .status("Available")
                        .build())
                .collect(Collectors.toList());

    }

}
