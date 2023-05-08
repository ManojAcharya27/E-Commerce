package com.example.eCommerce.Service.ServiceImpl;


import com.example.eCommerce.Controller.RequestDto.ProductRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ProductResponseDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Entity.Ordered;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Entity.Seller;
import com.example.eCommerce.Enums.ProductCategory;
import com.example.eCommerce.Enums.ProductStatus;
import com.example.eCommerce.Exceptions.InvalidProductException;
import com.example.eCommerce.Exceptions.InvalidSellerException;
import com.example.eCommerce.Repository.ProductRepository;
import com.example.eCommerce.Repository.SellerRepository;
import com.example.eCommerce.Service.ServiceInterface.ProductService;
import com.example.eCommerce.Transformer.ProductTransformer;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    SellerRepository sellerRepository;
    @Override
    public ProductResponseDto addSeller(ProductRequestDto productRequestDto) throws InvalidSellerException {

        Seller seller;
        try {
            seller=sellerRepository.findById(productRequestDto.getSellerId()).get();
        }catch (Exception e){
            throw new InvalidSellerException("Seller does not exist");
        }

        Product product= ProductTransformer.productRequestDtoToProduct(productRequestDto);
        product.setSeller(seller);

        seller.getProducts().add(product);
        sellerRepository.save(seller);
        return ProductTransformer.productToResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProductByCategory(ProductCategory productCategory) {
        List<Product> products=productRepository.findByProductCategory(productCategory);
        List<ProductResponseDto> productResponseDtoS=new ArrayList<>();
        for(Product product: products){
            productResponseDtoS.add(ProductTransformer.productToResponseDto(product));
        }
        return productResponseDtoS;

    }

    @Override
    public List<ProductResponseDto> getAllProductByPriceAndCategory(int price, String productCategory) {

        List<Product> products=productRepository.getAllProductByPriceAndCategory(price,productCategory);
        List<ProductResponseDto> productResponseDtoS=new ArrayList<>();
        for(Product product: products){
            productResponseDtoS.add(ProductTransformer.productToResponseDto(product));
        }
        return productResponseDtoS;
    }



    @Override
    public void decreseSavedProductQunatity(Item item) throws InvalidProductException {

            Product product=item.getProduct();
            int quantity=item.getRequiredQuantity();
            int currentQuantity=product.getQuantity();
            if(quantity>currentQuantity){
                throw new InvalidProductException("Out of stock");
            }
            product.setQuantity(currentQuantity-quantity);
            if(product.getQuantity()==0){
                product.setProductStatus(ProductStatus.OUT_OF_STOCK);
            }

    }

    @Override
    public List<ProductResponseDto> getAllProductBySellerEmail(String email) {
        List<Product> products=productRepository.findAll();
        List<ProductResponseDto> productResponseDtos=new ArrayList<>();

        for(Product product: products){
            if(product.getSeller().getEmailId().equals(email)){
                ProductResponseDto productResponseDto=new ProductResponseDto();
                productResponseDto=ProductTransformer.productToResponseDto(product);
                productResponseDtos.add(productResponseDto);
            }
        }
        return productResponseDtos;
    }

    @Override
    public String deleteProductBySellerEmail(String email) throws InvalidSellerException {
        List<Product> productList=productRepository.findAll();
        boolean flag=false;

        for (Product product: productList){
            if(product.getSeller().getEmailId().equals(email)){
                flag=true;
                int id=product.getId();
                productRepository.deleteById(id);
            }
        }
         if(flag){
             return "Product has been removed";
         }else{
             throw new InvalidSellerException("Seller  not found");
         }
    }

    @Override
    public List<ProductResponseDto> top5CostliestProduct() {
        List<Product> products=productRepository.top5CostliestProduct();
        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto=new ProductResponseDto();
            productResponseDto=ProductTransformer.productToResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;
    }

    @Override
    public List<ProductResponseDto> top5CheapestProduct() {

        List<Product> products=productRepository.top5cheapestProduct();
        List<ProductResponseDto> productResponseDtoList=new ArrayList<>();
        for(Product product: products){
            ProductResponseDto productResponseDto=new ProductResponseDto();
            productResponseDto=ProductTransformer.productToResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }
        return productResponseDtoList;

    }

    @Override
    public List<ProductResponseDto> getAllProductHavingOutOfStockStatus(){
        List<Product> productList=productRepository.findAll();
        List<ProductResponseDto>productResponseDtos=new ArrayList<>();
        for(Product product:productList){
            if(product.getProductStatus()==ProductStatus.OUT_OF_STOCK){
                ProductResponseDto productResponseDto=new ProductResponseDto();
                productResponseDto=ProductTransformer.productToResponseDto(product);
                productResponseDtos.add(productResponseDto);
            }
        }
        return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> top5CostliestProductOfParticularCategory(String category) throws InvalidProductException {
        List<Product> productList=productRepository.top5CostliestProductOfParticularCategory(category);
        List<ProductResponseDto>productResponseDtos=new ArrayList<>();
        for(Product product:productList){
            ProductResponseDto productResponseDto=ProductTransformer.productToResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }
         return productResponseDtos;
    }

    @Override
    public List<ProductResponseDto> top5CheapestProductOfParticularCategory(String category) {
        List<Product> productList=productRepository.top5CheapestProductOfParticularCategory(category);
        List<ProductResponseDto>productResponseDtos=new ArrayList<>();
        for(Product product:productList){
            ProductResponseDto productResponseDto=ProductTransformer.productToResponseDto(product);
            productResponseDtos.add(productResponseDto);
        }
        return productResponseDtos;
    }

}

