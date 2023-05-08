package com.example.eCommerce.Transformer;

import com.example.eCommerce.Controller.RequestDto.ItemRequestDto;
import com.example.eCommerce.Controller.RequestDto.OrderRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class ItemTransformer {


    public static Item itemRequestDtoToItem(ItemRequestDto itemRequestDto){

        return Item.builder()
                .requiredQuantity(itemRequestDto.getProductId()).build();
    }

    public static Item OrderRequestToItem(OrderRequestDto orderRequestDto){
        Item item=Item.builder().
        requiredQuantity(orderRequestDto.getRequiredQuantity()).build();
        return item;
    }

    public static ItemResponseDto itemToItemResponseDto(Item item){
        return ItemResponseDto.builder()
                .priceOfOneProduct(item.getProduct().getPrice())
                .productName(item.getProduct().getName())
                .quantity(item.getRequiredQuantity()).
                totalPrice(item.getRequiredQuantity()*item.getProduct().getPrice()).build();
    }
}
