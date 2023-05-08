package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.CardRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CardResponseDto;
import com.example.eCommerce.Enums.CardType;
import com.example.eCommerce.Exceptions.InvalidCustomerException;

import java.util.Date;
import java.util.List;

public interface CardService {

    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException;


    public List<CardResponseDto> getAllCardOfSpecificCardType(CardType cardType);

    public List<CardResponseDto> getAllCardOfGivenExpiryDateOfGivenCardType(Date date,CardType cardType);


    public CardType getCardTypeWhichIsMostlyUsed();

}
