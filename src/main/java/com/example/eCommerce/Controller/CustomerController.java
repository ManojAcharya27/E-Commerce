package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.CustomerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedCustomerResponseDto;
import com.example.eCommerce.Service.ServiceInterface.CustomerService;
import jakarta.websocket.server.PathParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

@Autowired
CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity addCustomer(@RequestBody CustomerRequestDto customerRequestDto){
        try {
            CustomerResponseDto customerResponseDto=customerService.addCustomer(customerRequestDto);
            return  new ResponseEntity(customerResponseDto, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_GATEWAY);
        }
    }
    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity deleteCustomer(@PathParam("customerId")int customerId){
        try {
            return new ResponseEntity(customerService.deleteCustomer(customerId),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update_customer")
    public ResponseEntity updateCustomersEmail(@PathParam("email")String oldEmail,@PathParam("email")String newEmail){
        try {
            UpdatedCustomerResponseDto updatedCustomerResponseDto=customerService.upDateCustomersEmail(oldEmail,newEmail);
            return new ResponseEntity(updatedCustomerResponseDto,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }


      
}
