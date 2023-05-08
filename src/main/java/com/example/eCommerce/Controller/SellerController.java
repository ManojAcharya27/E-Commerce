package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.SellerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.SellerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedSellerResponseDto;
import com.example.eCommerce.Exceptions.EmailAlreadyPresentException;
import com.example.eCommerce.Service.ServiceInterface.SellerInterface;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {


    @Autowired
    SellerInterface sellerInterface;
    @PostMapping("/add")
    public ResponseEntity addSeller(@RequestBody SellerRequestDto sellerRequestDto) {
        try {
            SellerResponseDto sellerResponseDto=sellerInterface.addSeller(sellerRequestDto);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/get_seller_by_email/{email}")
    public ResponseEntity getSellerByEmail(@PathVariable("email")String email){

        try {
            SellerResponseDto sellerResponseDto=sellerInterface.getSellerByEmail(email);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_seller_by_id/{id}")
    public ResponseEntity getSellerById(@PathVariable("id") int id){
        try {
            SellerResponseDto sellerResponseDto=sellerInterface.getSellerById(id);
            return new ResponseEntity(sellerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/update_seller_info_by_email/{email}")
    public ResponseEntity updateSellerByEmail(@PathVariable("email")String email, @PathParam("age") int age){
        try {
            UpdatedSellerResponseDto updatedSellerResponseDto=sellerInterface.updateSellerByEmail(email,age);

            return new ResponseEntity(updatedSellerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){

            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{email}")
    public ResponseEntity deleteSellerByEmail(@PathVariable("email")String email){
        try {
            String message=sellerInterface.deleteSellerByEmail(email);
            return new ResponseEntity(message,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteSellerBtId(@PathVariable("id")int id){
        try {
            String message=sellerInterface.deleteSellerById(id);
            return new ResponseEntity(message,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    public ResponseEntity<List>  getAllSellerWithSimilarAge(@PathVariable("age") int age){

        return new ResponseEntity<>(sellerInterface.getAllSellerWithSimilarAger(age),HttpStatus.CREATED);
    }
}
