package com.example.eCommerce.Entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ordered")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordered {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String orderNo;

    int totalCost;

    @CreationTimestamp
    Date orderDate;

    String cardUsed;

    @OneToMany(mappedBy = "ordered",cascade = CascadeType.ALL)
    List<Item> items=new ArrayList<>();


    @ManyToOne
    @JoinColumn
    Customer customer;
}
