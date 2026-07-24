package com.quickbite.backend.service;

import com.quickbite.backend.Repository.ProductRepository;
import com.quickbite.backend.dto.Request.ProductRequest;
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
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("product not found with id "+ id));
        return mapToDTO(product);
    }

    public ProductResponse createProduct(ProductRequest productRequest){
        Product product=Product.builder()
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .build();
        Product saveProduct=productRepository.save(product);
        return mapToDTO(saveProduct);
    }

    public ProductResponse updateProduct(Long id , ProductRequest productRequest){
        Product exixtingProduct=productRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Product not found with id: \" + id"));

        exixtingProduct.setName(productRequest.getName());
        exixtingProduct.setPrice(productRequest.getPrice());
        exixtingProduct.setDescription(productRequest.getDescription());

        Product updatedProduct=productRepository.save(exixtingProduct);
        return mapToDTO(updatedProduct);

    }

    public String deleteProduct(Long id){
        if(!productRepository.existsById(id)){
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        return "product with " +id +"id deleted";
    }


    private ProductResponse mapToDTO(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status("Available")
                .build();
    }

}
