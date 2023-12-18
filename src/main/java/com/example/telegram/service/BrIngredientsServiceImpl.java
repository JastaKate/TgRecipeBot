//package com.example.telegram.service;
//
//import com.example.telegram.entity.BrIngredients;
//import com.example.telegram.entity.Breakfasts;
//import com.example.telegram.repository.BrIngredientsRepo;
//import com.example.telegram.request.BrIngredientsRequest;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class BrIngredientsServiceImpl implements BrIngredientsService{
//    @PersistenceContext
//    private EntityManager entityManager;
//    private final BrIngredientsRepo ingredientsRepo;
//
//    @Override
//    public BrIngredients createIngredient(Long breakfasts_id, BrIngredientsRequest request) {
//        Breakfasts breakfasts = entityManager.getReference(Breakfasts.class, breakfasts_id);
//        return ingredientsRepo.save(BrIngredients.builder()
//                        .breakfasts(breakfasts)
//                        .product(request.getProduct())
//                        .amount(request.getAmount())
//                .build());
//    }
//
//    @Override
//    public BrIngredients putIngredient(Long ingredients_id, BrIngredientsRequest request) {
//        BrIngredients brIngredients = ingredientsRepo.findById(ingredients_id).get();
//        brIngredients.setProduct(request.getProduct());
//        brIngredients.setAmount(request.getAmount());
//        return ingredientsRepo.save(brIngredients);
//    }
//
//    @Override
//    public void deleteIngredient(Long id) {
//        ingredientsRepo.deleteById(id);
//    }
//}
