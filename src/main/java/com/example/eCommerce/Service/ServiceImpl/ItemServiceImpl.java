package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.ItemRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CustomerResponseDto;
import com.example.eCommerce.Entity.Customer;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Entity.Product;
import com.example.eCommerce.Enums.ProductStatus;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;
import com.example.eCommerce.Repository.CustomerRepository;
import com.example.eCommerce.Repository.ItemRepository;
import com.example.eCommerce.Repository.ProductRepository;
import com.example.eCommerce.Service.ServiceInterface.ItemService;
import com.example.eCommerce.Transformer.ItemTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ItemRepository itemRepository;


    @Override
    public Item addItem(ItemRequestDto itemRequestDto) throws Exception {


        Customer customer;
        try {
            customer =customerRepository.findById(itemRequestDto.getCustomerId()).get();
        }catch (Exception e){
            throw  new InvalidCustomerException("Customer not registered");
        }

        Product product;
        try {
            product=productRepository.findById(itemRequestDto.getProductId()).get();
        }catch (Exception e){
            throw new InvalidProductException("Product is not available");
        }

        if(itemRequestDto.getRequiredQuantity()>product.getQuantity()||product.getProductStatus()!= ProductStatus.AVAILABLE){
            throw  new Exception("Product is out of Stock");
        }
        Item item= ItemTransformer.itemRequestDtoToItem(itemRequestDto);

        item.setName(product.getName());

        item.setCart(customer.getCart());
        item.setProduct(product);

        product.getItems().add(item);

        /*Product savedProduct=productRepository.save(product);

        int size=product.getItems().size();

        return product.getItems().get(size-1);*/

        return  itemRepository.save(item);


    }
}
