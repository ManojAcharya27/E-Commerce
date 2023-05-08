package com.example.eCommerce.Dto.ResponseDto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ItemResponseDto {

    String productName;

    int priceOfOneProduct;

    int totalPrice;

    int quantity;


}
