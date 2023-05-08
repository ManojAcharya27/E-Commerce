package com.example.eCommerce.Dto.ResponseDto;

import com.example.eCommerce.Entity.Customer;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CartResponseDto {


    Integer cartTotal;

    Integer numberOfItem;


    String customerName;

    List<ItemResponseDto> itemResponseDtoList;

}
