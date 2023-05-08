package com.example.eCommerce.Transformer;

import com.example.eCommerce.Controller.RequestDto.SellerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.SellerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedSellerResponseDto;
import com.example.eCommerce.Entity.Seller;

public class SellerTransformer {
    public  static Seller sellerTransformer(SellerRequestDto sellerRequestDto){
        Seller seller=Seller.builder()
                .age(sellerRequestDto.getAge())
                .name(sellerRequestDto.getName())
                .emailId(sellerRequestDto.getEmailId())
                .mobNo(sellerRequestDto.getMobNo())
                .build();
        return  seller;
    }

    public  static SellerResponseDto sellerToSellerResponseDto(Seller seller){
        return SellerResponseDto.builder().name(seller.getName()).age(seller.getAge()).build();
    }
    public static UpdatedSellerResponseDto sellerToUpdatedSellerResponseDto(Seller seller){
        return UpdatedSellerResponseDto.builder()
                .updated_age(seller.getAge()).
        name(seller.getName()).build();
    }
}
