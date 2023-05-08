package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.CheckOutCartRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CartResponseDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Exceptions.InvalidCardException;
import com.example.eCommerce.Exceptions.InvalidCartException;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;

import java.util.List;

public interface CartService {

    public CartResponseDto saveCart(int customerId, Item savedItem);

    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws InvalidCustomerException, InvalidCardException, InvalidCartException, InvalidProductException;


    public List<ItemResponseDto> viewAllCartItems(int customerId) throws InvalidCustomerException;
}

