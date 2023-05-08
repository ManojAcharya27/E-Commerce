package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.ProductRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ProductResponseDto;
import com.example.eCommerce.Enums.ProductCategory;
import com.example.eCommerce.Service.ServiceInterface.ProductService;
import jakarta.persistence.GeneratedValue;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;


// to add product
    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody ProductRequestDto productRequestDto){

        try {
            ProductResponseDto productResponseDto=productService.addSeller(productRequestDto);
            return new ResponseEntity(productResponseDto, HttpStatus.CREATED);

        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // To get  all product based on product category
    @GetMapping("/get/{category}")
    public ResponseEntity<List>getAllProductByCategory(@PathVariable("category")ProductCategory productCategory){
        return new ResponseEntity<>(productService.getAllProductByCategory(productCategory),HttpStatus.CREATED);
    }


    // API for to get all product of given category
    @GetMapping("get/{price}/{category}")
    public ResponseEntity<List>getAllProductByPriceAndCategory(@PathVariable("price")int price,@PathVariable("category")String productCategory){
        return new ResponseEntity<>(productService.getAllProductByPriceAndCategory(price,productCategory),HttpStatus.CREATED);
    }

    // API for get all product based on seller email

    @GetMapping("/get_all_product_by_email/{email}")
    public ResponseEntity<List>getAllProductBySellerEmail(@PathVariable("email")String email){
       return new ResponseEntity<>(productService.getAllProductBySellerEmail(email),HttpStatus.CREATED);
    }

    // API for deleting product w r t Seller email

    @DeleteMapping("/delete_product_by_seller_emailid")
    public ResponseEntity deleteProductBySellerEmailId(@PathParam("email")String email){
        try {
            String ans=productService.deleteProductBySellerEmail(email);
            return new ResponseEntity(ans,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // API to get costliest product
    @GetMapping("/get_top5_costliest")
     public ResponseEntity<List> top5CostliestProduct(){
        return new ResponseEntity<>(productService.top5CostliestProduct(),HttpStatus.CREATED);
    }
    //API to get top 5 cheapest product
    @GetMapping("/get_top5_cheapest")
    public ResponseEntity<List> top5CheapestProduct(){
        return new ResponseEntity<>(productService.top5CheapestProduct(),HttpStatus.CREATED);
    }

    // API to get out of stocks product
    @GetMapping("/get/out_of_stock")
    public ResponseEntity<List> getAllProductWithRespectToStatus(){
        return new ResponseEntity<>(productService.getAllProductHavingOutOfStockStatus(),HttpStatus.CREATED);
    }

    // API to get top 5 costliest product of particular category


    @GetMapping("/get/top5_costliest_product/{category}")
    public ResponseEntity<List> top5CostliestProductOfParticularCategory(@PathVariable("category")String category){

        try {
            List<ProductResponseDto> productResponseDtos=productService.top5CostliestProductOfParticularCategory(category);
            return new ResponseEntity<>(productResponseDtos,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    // API to get top 5 cheapest product of particular category
    @GetMapping("get/top5_cheapest_product/{category}")
    public ResponseEntity<List> top5CheapestProductOfParticularCategory(@PathVariable("category")String category){
        try {
            List<ProductResponseDto> productResponseDtos=productService.top5CheapestProductOfParticularCategory(category);
            return new ResponseEntity<>(productResponseDtos,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }
}
