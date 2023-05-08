package com.example.eCommerce.Transformer;

import com.example.eCommerce.Controller.RequestDto.CardRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CardResponseDto;
import com.example.eCommerce.Entity.Card;

public class CardTransformer {

    public static Card cardRequestDtoToCard(CardRequestDto cardRequestDto){
        return  Card.builder().cardNo(cardRequestDto.getCardNo())
                .cardType(cardRequestDto.getCardType()).
                 cvv(cardRequestDto.getCvv())
                .expiryDate(cardRequestDto.getExpiryDate()).build();
    }

    public  static CardResponseDto cardToCardResponseDto(Card card){
        return CardResponseDto.builder().customerName(card.getCustomer().getName())
                .cardNo(card.getCardNo()).build();
    }
}
