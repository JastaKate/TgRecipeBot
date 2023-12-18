//package com.example.telegram.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Entity
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "ingredients")
//public class BrIngredients {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//    @Column(name = "product")
//    private String product;
//    @Column(name = "amount")
//    private String amount;
//    @Column(name = "breakfasts_id", insertable = false, updatable = false)
//    private Long breakfasts_id;
//
//    @ManyToOne
//    @JoinColumn(name = "breakfasts_id", referencedColumnName = "id")
//    @JsonBackReference
//    private Breakfasts breakfasts;
//
//}
