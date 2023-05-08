package com.example.eCommerce.Transformer;

import com.example.eCommerce.Controller.RequestDto.ProductRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ProductResponseDto;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Enums.ProductStatus;

public class ProductTransformer {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto){
        Product product= Product.builder()
                .price(productRequestDto.getPrice())
                .name(productRequestDto.getProductName())
                .quantity(productRequestDto.getQuantity()).productCategory(productRequestDto.getProductCategory())
                .productStatus(ProductStatus.AVAILABLE)
                .build();
        return  product;
    }

    public static ProductResponseDto productToResponseDto(Product product){
        return ProductResponseDto.builder()
                .productName(product.getName())
                .productStatus(product.getProductStatus())
                .quantity(product.getQuantity())
                .sellerName(product.getSeller().getName())
                .build();
    }
}
