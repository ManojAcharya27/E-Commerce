package com.example.eCommerce.Controller.RequestDto;

import com.example.eCommerce.Enums.ProductCategory;
import com.example.eCommerce.Enums.ProductStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequestDto {


    int sellerId;

    String productName;

    int price;

    int quantity;


    ProductCategory productCategory;


}
