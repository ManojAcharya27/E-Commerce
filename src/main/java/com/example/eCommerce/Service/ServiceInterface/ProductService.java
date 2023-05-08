package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.ProductRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ProductResponseDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Entity.Ordered;
import com.example.eCommerce.Enums.ProductCategory;
import com.example.eCommerce.Exceptions.InvalidProductException;
import com.example.eCommerce.Exceptions.InvalidSellerException;

import java.util.List;

public interface ProductService {

    public ProductResponseDto addSeller(ProductRequestDto productRequestDto) throws InvalidSellerException;

    public List<ProductResponseDto> getAllProductByCategory(ProductCategory productCategory);

    public List<ProductResponseDto> getAllProductByPriceAndCategory(int price,String productCategory);

    void decreseSavedProductQunatity(Item item) throws InvalidProductException;

    public List<ProductResponseDto> getAllProductBySellerEmail(String email);

    public String deleteProductBySellerEmail(String email) throws InvalidSellerException;

    public List<ProductResponseDto> top5CostliestProduct();

    public List<ProductResponseDto> top5CheapestProduct();

    public List<ProductResponseDto> getAllProductHavingOutOfStockStatus();

    public List<ProductResponseDto> top5CostliestProductOfParticularCategory(String category) throws InvalidProductException;

    public List<ProductResponseDto> top5CheapestProductOfParticularCategory(String category);
}
