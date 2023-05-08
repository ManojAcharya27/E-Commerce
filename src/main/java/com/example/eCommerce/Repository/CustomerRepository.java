package com.example.eCommerce.Repository;

import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findByMobNo(String mobNo);

    Customer findByEmailId(String email);
}
