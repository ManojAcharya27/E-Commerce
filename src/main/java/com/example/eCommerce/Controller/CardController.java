package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.CardRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CardResponseDto;
import com.example.eCommerce.Enums.CardType;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Service.ServiceInterface.CardService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    CardService cardService;
    @PostMapping("/add")
    public ResponseEntity addCard(@RequestBody CardRequestDto cardRequestDto) {
         try {
             CardResponseDto cardResponseDto=cardService.addCard(cardRequestDto);
             return new ResponseEntity(cardResponseDto, HttpStatus.CREATED);
         }catch (Exception e){
             return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
         }
    }

    @GetMapping("/get_card/{cardtype}")
    public ResponseEntity<List> getAllCardOfSpecificCardType(@PathVariable("cardtype")CardType  cardType){
        return new ResponseEntity<>(cardService.getAllCardOfSpecificCardType(cardType),HttpStatus.CREATED);
    }

    @GetMapping("/get_card_of_given_expiry_date_of_given_card_type")
     public ResponseEntity<List> getAllCardOfGivenExpiryDateOfGivenCardType(@PathParam("date") Date date,@PathParam("cardType")CardType cardType){
        return new ResponseEntity<>(cardService.getAllCardOfGivenExpiryDateOfGivenCardType(date,cardType),HttpStatus.CREATED);
    }

    @GetMapping("/get_card/max_used")
    public ResponseEntity getCardTypeWhichIsMostlyUsed(){
        return new  ResponseEntity(cardService.getCardTypeWhichIsMostlyUsed(),HttpStatus.CREATED);

    }
}
