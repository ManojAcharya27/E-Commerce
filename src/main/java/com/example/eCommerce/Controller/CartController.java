package com.example.eCommerce.Controller;

import com.example.eCommerce.Controller.RequestDto.CheckOutCartRequestDto;
import com.example.eCommerce.Controller.RequestDto.ItemRequestDto;
import com.example.eCommerce.Dto.ResponseDto.CartResponseDto;
import com.example.eCommerce.Dto.ResponseDto.ItemResponseDto;
import com.example.eCommerce.Dto.ResponseDto.OrderResponseDto;
import com.example.eCommerce.Entity.Item;
import com.example.eCommerce.Service.ServiceInterface.CartService;
import com.example.eCommerce.Service.ServiceInterface.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {


    @Autowired
    ItemService itemService;

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity addToCart(@RequestBody ItemRequestDto itemRequestDto){
        try {
            Item savedItem=itemService.addItem(itemRequestDto);
            CartResponseDto cartResponseDto=cartService.saveCart(itemRequestDto.getCustomerId(),savedItem);
            return new ResponseEntity(cartResponseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDto> checkOutCart(@RequestBody CheckOutCartRequestDto checkOutCartRequestDto){

        try {
            OrderResponseDto orderResponseDto=cartService.checkOutCart(checkOutCartRequestDto);
            return new ResponseEntity(orderResponseDto,HttpStatus.CREATED);
        }catch (Exception e){
            return  new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }


    }

    @GetMapping("/view_cart/{id}")
    public ResponseEntity<List> viewAllCartItems(@PathVariable("id") int customerId){

        try {
            List<ItemResponseDto> itemResponseDtoList=cartService.viewAllCartItems(customerId);
            return new ResponseEntity<>(itemResponseDtoList,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
