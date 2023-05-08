package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.OrderRequestDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Service.ServiceInterface.OrderService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity placeDirectOrder(@RequestBody OrderRequestDto orderRequestDto){
       try {
           OrderResponseDto orderResponseDto=orderService.placeDirectOrder(orderRequestDto);
           return new ResponseEntity(orderRequestDto, HttpStatus.CREATED);
       }catch (Exception e){
           return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/get_all_order/{customerId}")
    public ResponseEntity<List> getAllOrderForCustomer(@PathVariable("customerId")int customerId){
        try {
            List<OrderResponseDto> orderResponseDtos=orderService.getAllOrderForCustomer(customerId);
            return new ResponseEntity<>(orderResponseDtos, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_latest_5orders")
    public ResponseEntity<List> getLatest5orders(){
        try {
            List<OrderResponseDto> orderResponseDtos=orderService.getLatest5Orders();
            return new ResponseEntity<>(orderResponseDtos, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.CREATED);
        }
    }

    @GetMapping("/get_highest_order_cost")
    public ResponseEntity getHighestOrderCost(){
        return new ResponseEntity(orderService.getHighestCostOrder(),HttpStatus.CREATED);
    }


    @DeleteMapping("/delete_order/{customerId}")
    public ResponseEntity deleteOrderFromOrderListOfCustomer(@PathVariable("customerId") int customerId, @PathParam("orderId")int orderId){
          try {
            return new ResponseEntity(orderService.deleteOrderFromOrderListOfCustomer(customerId,orderId),HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



}
