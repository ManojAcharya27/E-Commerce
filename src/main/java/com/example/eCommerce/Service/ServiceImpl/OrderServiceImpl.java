package com.example.eCommerce.Service.ServiceImpl;

import com.example.eCommerce.Controller.RequestDto.OrderRequestDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Entity.*;
import com.example.eCommerce.Exceptions.InvalidCardException;
import com.example.eCommerce.Exceptions.InvalidCartException;
import com.example.eCommerce.Exceptions.InvalidCustomerException;
import com.example.eCommerce.Exceptions.InvalidProductException;
import com.example.eCommerce.Repository.CardRepository;
import com.example.eCommerce.Repository.CustomerRepository;
import com.example.eCommerce.Repository.OrderedRepository;
import com.example.eCommerce.Repository.ProductRepository;
import com.example.eCommerce.Service.ServiceInterface.OrderService;
import com.example.eCommerce.Service.ServiceInterface.ProductService;
import com.example.eCommerce.Transformer.ItemTransformer;
import com.example.eCommerce.Transformer.OrderTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductService productService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    OrderedRepository orderedRepository;

    @Autowired
    private JavaMailSender emailSender;

    public Ordered orderPlaced(Customer customer, Card card) throws InvalidProductException {
        Cart cart=customer.getCart();
        Ordered ordered= OrderTransformer.makingCardObject(card,cart,customer);
        List<Item> orderItem=new ArrayList<>();
        for(Item item: cart.getItemList()){
            try {
                productService.decreseSavedProductQunatity(item);
                orderItem.add(item);
            } catch (Exception e) {
                throw new InvalidProductException(e.getMessage());
            }
        }
        ordered.setItems(orderItem);
        for(Item item: orderItem){
            item.setOrdered(ordered);
        }
        ordered.setTotalCost(cart.getTotalCost());


        return ordered;
    }

    @Override
    public OrderResponseDto placeDirectOrder(OrderRequestDto orderRequestDto) throws InvalidProductException, InvalidCustomerException, InvalidCardException {

        Customer customer;
        try {
            customer =customerRepository.findById(orderRequestDto.getCustomerId()).get();
        }catch (Exception e){
            throw  new InvalidCustomerException("Customer not registered");
        }

        Product product;
        try {
            product=productRepository.findById(orderRequestDto.getProductId()).get();
        }catch (Exception e){
            throw new InvalidProductException("Product is not available");
        }

        Card card=cardRepository.findByCardNo(orderRequestDto.getCardNo());

        if(card==null||card.getCustomer()!=customer||card.getCvv()!=orderRequestDto.getCvv()){
            throw  new InvalidCardException("Unable to use card");
        }


        Item item= ItemTransformer.OrderRequestToItem(orderRequestDto);
        item.setProduct(product);
        item.setName(product.getName());
        try {
            productService.decreseSavedProductQunatity(item);
        }catch (Exception e){
            throw new InvalidProductException("Product is not available");
        }

        Ordered ordered=OrderTransformer.objectOFOrderForOrdering(customer,card);

        ordered.setTotalCost(item.getRequiredQuantity()*product.getPrice());
        ordered.getItems().add(item);

        item.setOrdered(ordered);

        customer.getOrderedList().add(ordered);

        product.getItems().add(item);

       Ordered savedOrdered=orderedRepository.save(ordered);

       OrderResponseDto orderResponseDto=OrderTransformer.orderedToOrderResponseDto(savedOrdered);

        List<ItemResponseDto> itemResponseDtoList=new ArrayList<>();
        for(Item item1: savedOrdered.getItems()){
            ItemResponseDto itemResponseDto=new ItemResponseDto();
            itemResponseDto.setPriceOfOneProduct(item1.getProduct().getPrice());
            itemResponseDto.setQuantity(item1.getRequiredQuantity());
            itemResponseDto.setProductName(item1.getProduct().getName());
            itemResponseDto.setTotalPrice(item1.getRequiredQuantity()*item1.getProduct().getPrice());
            itemResponseDtoList.add(itemResponseDto);
        }
        orderResponseDto.setItems(itemResponseDtoList);


        String text="Congrats"+ordered.getCustomer().getName()+" "+"Order has been placed"+
                "Order description"+" "+
               " "+ordered.getOrderDate()+
                " " +ordered.getOrderNo()+
                " "+item.getProduct().getName()+
                " " +ordered.getTotalCost();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hmanojacharya27@gmail.com");
        message.setTo(customer.getEmailId());
        message.setSubject("Order is Placed ");
        message.setText(text);
        emailSender.send(message);


        return orderResponseDto;
    }

    @Override
    public List<OrderResponseDto> getAllOrderForCustomer(int customerId) throws InvalidCustomerException {
        try {
            Customer customer = customerRepository.findById(customerId).get();
            List<Ordered> orderedList = customer.getOrderedList();
            List<OrderResponseDto> orderResponseDtos = new ArrayList<>();
            for (Ordered ordered : orderedList) {
                OrderResponseDto orderResponseDto = new OrderResponseDto();
                orderResponseDto = OrderTransformer.orderedToOrderResponseDto(ordered);
                orderResponseDtos.add(orderResponseDto);
            }
            return orderResponseDtos;
        }catch (Exception e){
            throw new InvalidCustomerException("Customer does not exist");
        }

    }

    @Override
    public List<OrderResponseDto> getLatest5Orders() throws Exception {
        List<Ordered> orderedList=orderedRepository.findAll();
        List<OrderResponseDto> orderResponseDtos=new ArrayList<>();
        if(orderedList.size()<=5){
            throw new Exception("There are no 5 or more order in db");
        }
        int count=1;
        for(int i=orderedList.size()-1;i>=0;i--){
            if(count>5) break;
            Ordered ordered=orderedList.get(i);
            OrderResponseDto orderResponseDto=OrderTransformer.orderedToOrderResponseDto(ordered);
            orderResponseDtos.add(orderResponseDto);
            count++;
        }
        return orderResponseDtos;

    }

    @Override
    public OrderResponseDto getHighestCostOrder() {
        List<Ordered> orderedList=orderedRepository.findAll();
        OrderResponseDto orderResponseDto=new OrderResponseDto();
        Ordered ordered1=null;
        int maxCost=0;
        for(Ordered ordered: orderedList){
            if(ordered.getTotalCost()>maxCost){
                maxCost=ordered.getTotalCost();
                ordered1=ordered;
            }
        }
        orderResponseDto=OrderTransformer.orderedToOrderResponseDto(ordered1);
        return orderResponseDto;
    }

    @Override
    public String deleteOrderFromOrderListOfCustomer(int customerId,int orderId) throws Exception {

        try {
            Customer customer=customerRepository.findById(customerId).get();
            List<Ordered> orderedList=customer.getOrderedList();
            boolean flag=false;
            for(Ordered ordered: orderedList){
                if(ordered.getId()==orderId){
                    orderedList.remove(ordered);
                    flag=true;

                }
            }
            if(!flag){
                throw new Exception("Oder is not present in the order");
            }
            customer.setOrderedList(orderedList);
            customerRepository.save(customer);
            return "Order removed from orderList successfully.!";
        }catch (Exception e){
            throw new InvalidCustomerException("Customer doest not present");
        }
    }
}

