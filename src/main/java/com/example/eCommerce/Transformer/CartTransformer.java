package com.example.eCommerce.Transformer;

import com.example.eCommerce.Dto.ResponseDto.CartResponseDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Entity.Cart;
import com.example.eCommerce.Entity.Item;

import java.util.ArrayList;
import java.util.List;

public class CartTransformer{
    public static CartResponseDto cartToCartResponseDto(Cart cart){
        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item: cart.getItemList()){
            ItemResponseDto itemResponseDto=new ItemResponseDto();
            itemResponseDto.setPriceOfOneProduct(item.getProduct().getPrice());
            itemResponseDto.setQuantity(item.getRequiredQuantity());
            itemResponseDto.setProductName(item.getProduct().getName());
            itemResponseDto.setTotalPrice(item.getRequiredQuantity()*item.getProduct().getPrice());
            itemResponseDtoList.add(itemResponseDto);
        }
        return CartResponseDto.builder()
                .cartTotal(cart.getTotalCost())
                .customerName(cart.getCustomer().getName())
                .numberOfItem(cart.getNumberOfItem()).itemResponseDtoList(itemResponseDtoList).build();
    }
}
