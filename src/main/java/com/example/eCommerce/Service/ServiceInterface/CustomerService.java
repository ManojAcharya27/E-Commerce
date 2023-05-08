package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.CustomerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedCustomerResponseDto;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.MobNoAlreadyPresentException;

public interface CustomerService {

    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws MobNoAlreadyPresentException;


    public String deleteCustomer(int customerId) throws InvalidCustomerException;


    public UpdatedCustomerResponseDto upDateCustomersEmail(String oldEmail, String newEmail) throws InvalidCustomerException;
}
