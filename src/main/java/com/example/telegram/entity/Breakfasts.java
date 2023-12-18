//package com.example.telegram.entity;
//
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name = "breakfasts")
//public class Breakfasts {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "category")
//    private String category;
//
//    @Column(name = "time")
//    private String time;
//
//    @OneToMany(mappedBy = "breakfasts", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
//    @JsonManagedReference
//    private List<BrIngredients> ingredients = new ArrayList<>();
//}
