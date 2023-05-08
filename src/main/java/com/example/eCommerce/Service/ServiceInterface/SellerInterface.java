package com.example.eCommerce.Service.ServiceInterface;

import com.example.eCommerce.Controller.RequestDto.SellerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.SellerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedSellerResponseDto;
import com.example.eCommerce.Exceptions.EmailAlreadyPresentException;
import com.example.eCommerce.Exceptions.InvalidSellerException;

import java.util.List;

public interface SellerInterface {
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException;

    public  SellerResponseDto getSellerByEmail(String email) throws InvalidSellerException;

    public SellerResponseDto getSellerById(int id) throws InvalidSellerException;

    public UpdatedSellerResponseDto updateSellerByEmail(String email, int age) throws InvalidSellerException;

    public String deleteSellerByEmail(String email) throws InvalidSellerException;

    public String deleteSellerById(int id) throws InvalidSellerException;

    public List<SellerResponseDto> getAllSellerWithSimilarAger(int age);


}
