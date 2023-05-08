package com.example.eCommerce.Dto.ResponseDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UpdatedCustomerResponseDto {
    String customerName;

    String customerEmail;

    String mobNo;

    String Address;

    int age;
}
