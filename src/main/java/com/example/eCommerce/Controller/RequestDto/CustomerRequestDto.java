package com.example.eCommerce.Controller.RequestDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CustomerRequestDto {


    String name;

    String emailId;

    int age;

    String mobNo;

    String address;
}
