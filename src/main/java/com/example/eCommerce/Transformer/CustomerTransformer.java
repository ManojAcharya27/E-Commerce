package com.example.eCommerce.Transformer;

import com.example.eCommerce.Controller.RequestDto.CustomerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedCustomerResponseDto;
import com.example.eCommerce.Entity.Customer;

public class CustomerTransformer {

    public static Customer customerRequestDtoToCustomer(CustomerRequestDto customerRequestDto){
        return Customer.builder().name(customerRequestDto.getName()).
                age(customerRequestDto.getAge())
                .emailId(customerRequestDto.getEmailId())
                .mobNo(customerRequestDto.getMobNo()).address(customerRequestDto.getAddress())
                .build();
    }

    public static CustomerResponseDto customerToCustomerResponseDto(Customer customer){
        return CustomerResponseDto.builder().name(customer.getName())
                .message("Welcome to ecommerce")
                .build();
    }

    public static UpdatedCustomerResponseDto customerTOUpdatedCustomerResponseDto(Customer customer){
        return UpdatedCustomerResponseDto.builder()
                .customerEmail(customer.getEmailId())
                .customerName(customer.getName())
                .Address(customer.getAddress())
                .age(customer.getAge()).mobNo(customer.getMobNo()).build();
    }
}
