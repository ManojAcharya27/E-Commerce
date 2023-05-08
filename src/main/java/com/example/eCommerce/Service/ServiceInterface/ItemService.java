package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.ItemRequestDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;

public interface ItemService {

    public Item addItem(ItemRequestDto itemRequestDto) throws Exception;
}
