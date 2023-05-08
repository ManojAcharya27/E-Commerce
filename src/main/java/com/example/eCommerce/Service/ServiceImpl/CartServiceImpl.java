package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.CheckOutCartRequestDto;
import com.example.eCommerce.Controller.RequestDto.ItemRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CartResponseDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Entity.*;
import com.example.eCommerce.Exceptions.InvalidCardException;
import com.example.eCommerce.Exceptions.InvalidCartException;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;
import com.example.eCommerce.Repository.*;
import com.example.eCommerce.Service.ServiceInterface.CartService;
import com.example.eCommerce.Service.ServiceInterface.CustomerService;
import com.example.eCommerce.Service.ServiceInterface.ProductService;
import com.example.eCommerce.Transformer.CartTransformer;
import com.example.eCommerce.Transformer.ItemTransformer;
import com.example.eCommerce.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CartRepository cartRepository;


    @Autowired
    CardRepository cardRepository;


    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    ProductService productService;

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public CartResponseDto saveCart(int customerId, Item savedItem) {

        Customer customer=customerRepository.findById(customerId).get();
        Cart cart=customer.getCart();
        int newTotal=cart.getTotalCost()+(savedItem.getRequiredQuantity()*savedItem.getProduct().getPrice());





         cart.setTotalCost(newTotal);
        cart.getItemList().add(savedItem);
        int newNumberOfItem=cart.getNumberOfItem()+savedItem.getRequiredQuantity();

        cart.setNumberOfItem(newNumberOfItem);
        Cart savedCart=cartRepository.save(cart);

        return CartTransformer.cartToCartResponseDto(savedCart);
    }

    @Override
    public OrderResponseDto checkOutCart(CheckOutCartRequestDto checkOutCartRequestDto) throws InvalidCustomerException, InvalidCardException, InvalidCartException, InvalidProductException {
     Customer customer;


     try{
         customer=customerRepository.findById(checkOutCartRequestDto.getCustomerId()).get();
     }catch (Exception e){
         throw  new InvalidCustomerException("Customer not registered");
     }

     Card card=cardRepository.findByCardNo(checkOutCartRequestDto.getCardNo());

     if(card==null||card.getCustomer()!=customer||card.getCvv()!=checkOutCartRequestDto.getCvv()){
         throw  new InvalidCardException("Unable to use card");
     }
     Cart cart=customer.getCart();
        if(cart.getNumberOfItem()==0){
            throw  new InvalidCartException("Cart is Empty .! ");
        }
        Ordered savedOrdered;
        try {
            Ordered ordered=orderService.orderPlaced(customer,card);
            customer.getOrderedList().add(ordered);
            savedOrdered=orderedRepository.save(ordered);
            resetCart(cart);
        }catch (Exception e){
            throw  new InvalidProductException(e.getMessage());
        }


       OrderResponseDto orderResponseDto=OrderTransformer.orderedToOrderResponseDto(savedOrdered);
        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item: savedOrdered.getItems()){
            ItemResponseDto itemResponseDto=new ItemResponseDto();
            itemResponseDto.setPriceOfOneProduct(item.getProduct().getPrice());
            itemResponseDto.setQuantity(item.getRequiredQuantity());
            itemResponseDto.setProductName(item.getProduct().getName());
            itemResponseDto.setTotalPrice(item.getRequiredQuantity()*item.getProduct().getPrice());
            itemResponseDtoList.add(itemResponseDto);
        }


        orderResponseDto.setItems(itemResponseDtoList);


        String text="Congrats"+customer.getName()+"your order has peen placed thank you for using ecommerce"+
                " "+"The order description"+
                orderResponseDto.getCardUsed()+
                orderResponseDto.getOrderNo()+
                orderResponseDto.getOrderDate()+
                orderResponseDto.getTotalCost();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hmanojacharya27@gmail.com");
        message.setTo(customer.getEmailId());
        message.setSubject("Order is Placed ");
        message.setText(text);
        emailSender.send(message);

        return orderResponseDto;
    }

    @Override
    public List<ItemResponseDto> viewAllCartItems(int customerId) throws InvalidCustomerException {

        Customer customer=customerRepository.findById(customerId).get();
        if(customer==null){
            throw  new InvalidCustomerException("customer does not exists");
        }
        List<Item> itemList=customer.getCart().getItemList();
        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item:itemList){
            ItemResponseDto itemResponseDto=new ItemResponseDto();
            itemResponseDto=ItemTransformer.itemToItemResponseDto(item);
            itemResponseDtoList.add(itemResponseDto);
        }
        return itemResponseDtoList;
    }

    public void resetCart(Cart cart){
        cart.setTotalCost(0);
        cart.setNumberOfItem(0);
        cart.setItemList(new ArrayList<>());
    }
}
