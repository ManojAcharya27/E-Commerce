package com.example.eCommerce.Controller.RequestDto;

import com.example.eCommerce.Enums.CardType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CardRequestDto {

    String  mobNo;

    String cardNo;

    int cvv;

    Date expiryDate;


    CardType cardType;
}
