package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.SellerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.SellerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedSellerResponseDto;
import com.example.eCommerce.Entity.Seller;
import com.example.eCommerce.Exceptions.EmailAlreadyPresentException;
import com.example.eCommerce.Exceptions.InvalidSellerException;
import com.example.eCommerce.Repository.SellerRepository;
import com.example.eCommerce.Service.ServiceInterface.SellerInterface;
import com.example.eCommerce.Transformer.SellerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerInterface {

    @Autowired
    SellerRepository sellerRepository;
    @Override
    public SellerResponseDto addSeller(SellerRequestDto sellerRequestDto) throws EmailAlreadyPresentException {
/*        Seller seller=new Seller();
        seller.setAge(sellerRequestDto.getAge());
        seller.setName(sellerRequestDto.getName());
        seller.setEmailId(sellerRequestDto.getEmailId());
        seller.setMobNo(sellerRequestDto.getMobNo());*/

        if(sellerRepository.findByEmailId(sellerRequestDto.getEmailId())!=null){
             throw new EmailAlreadyPresentException("Seller already present");
        }
       Seller seller= SellerTransformer.sellerTransformer(sellerRequestDto);
       Seller savedSeller=sellerRepository.save(seller);

        return SellerTransformer.sellerToSellerResponseDto(savedSeller);
    }

    @Override
    public SellerResponseDto getSellerByEmail(String email) throws InvalidSellerException {
        Seller seller;
        try {
            seller=sellerRepository.findByEmailId(email);
        } catch (Exception e){
            throw new InvalidSellerException("Seller not registered to e commerce !");
        }

        return  SellerTransformer.sellerToSellerResponseDto(seller);

    }

    @Override
    public SellerResponseDto getSellerById(int id) throws InvalidSellerException {

        Seller seller;
        try {
            seller=sellerRepository.findById(id).get();
        } catch (Exception e){
            throw new InvalidSellerException("Seller not registered to e commerce !");
        }

        return  SellerTransformer.sellerToSellerResponseDto(seller);
    }

    @Override
    public UpdatedSellerResponseDto updateSellerByEmail(String email, int age) throws InvalidSellerException {

        Seller seller;
        try {
            seller=sellerRepository.findByEmailId(email);
        } catch (Exception e){
            throw new InvalidSellerException("Seller not registered to e commerce !");
        }
        seller.setAge(age);
        Seller savedSeller=sellerRepository.save(seller);
        return SellerTransformer.sellerToUpdatedSellerResponseDto(savedSeller);
    }

    @Override
    public String deleteSellerByEmail(String email) throws InvalidSellerException {
        Seller seller;
        try {
            seller=sellerRepository.findByEmailId(email);
        } catch (Exception e){
            throw new InvalidSellerException("Seller not registered to e commerce !");
        }

        int id=seller.getId();
        sellerRepository.deleteById(id);
        return "Seller has been removed from db";


    }

    @Override
    public String deleteSellerById(int id) throws InvalidSellerException {
        Seller seller;
        try {
            seller=sellerRepository.findById(id).get();
        } catch (Exception e){
            throw new InvalidSellerException("Seller not registered to e commerce !");
        }
        sellerRepository.deleteById(id);
        return "Seller has been removed";
    }

    @Override
    public List<SellerResponseDto> getAllSellerWithSimilarAger(int age) {
        List<Seller> sellers=sellerRepository.findAll();
        List<SellerResponseDto> sellerResponseDtoS=new ArrayList<>();
        for(Seller seller: sellers){
            if(seller.getAge()==age){
                SellerResponseDto sellerResponseDto=new SellerResponseDto();
                sellerResponseDto=SellerTransformer.sellerToSellerResponseDto(seller);
                sellerResponseDtoS.add(sellerResponseDto);
            }
        }
        return sellerResponseDtoS;
    }


}
