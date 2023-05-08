package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.CardRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CardResponseDto;
import com.example.eCommerce.Entity.Card;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Enums.CardType;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidSellerException;
import com.example.eCommerce.Repository.CardRepository;
import com.example.eCommerce.Repository.CustomerRepository;
import com.example.eCommerce.Service.ServiceInterface.CardService;
import com.example.eCommerce.Transformer.CardTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CardRepository cardRepository;
    @Override
    public CardResponseDto addCard(CardRequestDto cardRequestDto) throws InvalidCustomerException {
        Customer customer=customerRepository.findByMobNo(cardRequestDto.getMobNo());

        if(customer==null){
            throw  new InvalidCustomerException("Sorry!. Customer Does not exist");
        }
        Card card= CardTransformer.cardRequestDtoToCard(cardRequestDto);

        card.setCustomer(customer);

        customer.getListOfCards().add(card);

        customerRepository.save(customer);

        return CardTransformer.cardToCardResponseDto(card);
    }

    @Override
    public List<CardResponseDto> getAllCardOfSpecificCardType(CardType cardType) {
        List<Card> cardList=cardRepository.findAll();
        List<CardResponseDto> cardResponseDtos=new ArrayList<>();
        for(Card card: cardList){
            if(card.getCardType().equals(cardType)){
                CardResponseDto cardResponseDto=CardTransformer.cardToCardResponseDto(card);
                cardResponseDtos.add(cardResponseDto);
            }
        }
        return cardResponseDtos;
    }

    @Override
    public List<CardResponseDto> getAllCardOfGivenExpiryDateOfGivenCardType(Date date, CardType cardType) {

        List<Card> cardList=cardRepository.findAll();
        List<CardResponseDto> cardResponseDtos=new ArrayList<>();
        for(Card card: cardList){
            if(card.getCardType().equals(cardType)&&card.getExpiryDate().compareTo(date)>0){
                CardResponseDto cardResponseDto=CardTransformer.cardToCardResponseDto(card);
                cardResponseDtos.add(cardResponseDto);
            }
        }
        return cardResponseDtos;
    }

    @Override
    public CardType getCardTypeWhichIsMostlyUsed() {
        int visa=0;
        int rupay=0;
        int master=0;
        List<Card> cardList=cardRepository.findAll();
        for (Card card:cardList){
            if(card.getCardType().equals(CardType.MASTER)){
                master++;
            }else if(card.getCardType().equals(CardType.RUPAY)){
                rupay++;
            }else visa++;
        }
        if(master>visa&&master>rupay) return CardType.MASTER;
        else if(visa>master&&visa>rupay)  return CardType.VISA;
        return CardType.RUPAY;

    }
}
