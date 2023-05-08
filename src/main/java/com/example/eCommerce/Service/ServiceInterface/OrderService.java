package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.OrderRequestDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Exceptions.InvalidCardException;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;

import java.util.List;

public interface OrderService {
    public OrderResponseDto placeDirectOrder(OrderRequestDto orderRequestDto) throws InvalidProductException, InvalidCustomerException, InvalidCardException;

    public List<OrderResponseDto> getAllOrderForCustomer(int customerId) throws InvalidCustomerException;

    public List<OrderResponseDto> getLatest5Orders() throws Exception;

    public OrderResponseDto getHighestCostOrder();

    public String deleteOrderFromOrderListOfCustomer(int customerId,int orderId) throws Exception;
}
