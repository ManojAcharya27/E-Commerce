package com.example.eCommerce.Transformer;

import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Entity.Card;
import com.example.eCommerce.Entity.Cart;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Entity.Ordered;

import java.util.UUID;

public class OrderTransformer {

    public static Ordered makingCardObject(Card card, Cart cart, Customer customer){

        Ordered ordered=new Ordered();
        ordered.setOrderNo(String.valueOf(UUID.randomUUID()));
        String cardNo=card.getCardNo();
        String cardUsed="";
        for(int i=0;i<cardNo.length()-4;i++){
            cardUsed+="X";
        }
        cardUsed+=cardNo.substring(cardNo.length()-4);
        ordered.setCardUsed(cardUsed);
        ordered.setCustomer(customer);
        return ordered;
    }

    public  static OrderResponseDto orderedToOrderResponseDto(Ordered ordered){
        return OrderResponseDto.builder()
                .orderNo(ordered.getOrderNo())
                .orderDate(ordered.getOrderDate())
                .totalCost(ordered.getTotalCost())
                .cardUsed(ordered.getCardUsed()).
                customerName(ordered.getCustomer().getName()).build();

    }
    public static  Ordered objectOFOrderForOrdering(Customer customer,Card card){
        Ordered ordered=new Ordered();
        ordered.setOrderNo(String.valueOf(UUID.randomUUID()));
        String cardNo=card.getCardNo();
        String cardUsed="";
        for(int i=0;i<cardNo.length()-4;i++){
            cardUsed+="X";
        }
        cardUsed+=cardNo.substring(cardNo.length()-4);
        ordered.setCardUsed(cardUsed);
        ordered.setCustomer(customer);
        return ordered;
    }
}
