package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.CustomerRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Dto.ResponseDto.UpdatedCustomerResponseDto;
import com.example.eCommerce.Entity.Cart;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.MobNoAlreadyPresentException;
import com.example.eCommerce.Repository.CustomerRepository;
import com.example.eCommerce.Service.ServiceInterface.CustomerService;
import com.example.eCommerce.Transformer.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public CustomerResponseDto addCustomer(CustomerRequestDto customerRequestDto) throws MobNoAlreadyPresentException {
        if(customerRepository.findByMobNo(customerRequestDto.getMobNo())!=null){
            throw new MobNoAlreadyPresentException("Customer Already Present");
        }
        Customer customer= CustomerTransformer.customerRequestDtoToCustomer(customerRequestDto);
        Cart cart =Cart.builder().
                 totalCost(0)
                .numberOfItem(0)
                .customer(customer)
                .build();
        customer.setCart(cart);
        Customer savedCustomer =customerRepository.save(customer);
        return CustomerTransformer.customerToCustomerResponseDto(savedCustomer);

    }

    @Override
    public String deleteCustomer(int customerId) throws InvalidCustomerException {

        try{
            Customer customer=customerRepository.findById(customerId).get();
            customerRepository.delete(customer);
            return "Customer deleted successfully";
        }catch (Exception e){
            throw new InvalidCustomerException("Customer does not exist");
        }

    }

    @Override
    public UpdatedCustomerResponseDto upDateCustomersEmail(String oldEmail, String newEmail) throws InvalidCustomerException {
        Customer customer=customerRepository.findByEmailId(oldEmail);
        if(customer==null){
            throw new InvalidCustomerException("Customer does not exist");
        }
        customer.setEmailId(newEmail);
        Customer savedCustomer=customerRepository.save(customer);

        UpdatedCustomerResponseDto updatedCustomerResponseDto=CustomerTransformer.customerTOUpdatedCustomerResponseDto(customer);
        return updatedCustomerResponseDto;


    }
}
