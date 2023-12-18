//package com.example.telegram.controller;
//
//import com.example.telegram.entity.BrIngredients;
//import com.example.telegram.request.BrIngredientsRequest;
//import com.example.telegram.service.BrIngredientsServiceImpl;
//import jakarta.validation.Valid;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@Getter
//@Setter
//@RequiredArgsConstructor
//@RequestMapping("/api")
//public class IngredientsController {
//
//    private final BrIngredientsServiceImpl ingredientsService;
//
//    @PostMapping("/ingredients")
//    public ResponseEntity<BrIngredients> createIngredient(@RequestParam Long breakfasts_id, @RequestBody @Valid BrIngredientsRequest ingredientsRequest) {
//        return new ResponseEntity<>(ingredientsService.createIngredient(breakfasts_id, ingredientsRequest), HttpStatus.OK);
//    }
//
//    @PutMapping("/ingredients/{id}")
//    public ResponseEntity<BrIngredients> putIngredient(@PathVariable Long id, @RequestBody @Valid BrIngredientsRequest ingredientsRequest) {
//        return new ResponseEntity<>(ingredientsService.putIngredient(id, ingredientsRequest), HttpStatus.OK);
//    }
//
//    @DeleteMapping("/ingredients/{id}")
//    public HttpStatus deleteIngredient(@PathVariable Long id) {
//        ingredientsService.deleteIngredient(id);
//        return HttpStatus.OK;
//    }
//
//
//}
