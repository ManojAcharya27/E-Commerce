package com.example.eCommerce.Dto.ResponseDto;

import com.example.eCommerce.Entity.Item;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderResponseDto {

    String orderNo;

    int totalCost;

    Date orderDate;

    String cardUsed;

    List<ItemResponseDto> items;

    String customerName;
}
