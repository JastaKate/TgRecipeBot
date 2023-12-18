//package com.example.telegram.service;
//
//import com.example.telegram.entity.Breakfasts;
//import com.example.telegram.repository.BreakfastsRepo;
//import com.example.telegram.request.BreakfastsRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BreakfastsServiceImpl implements BreakfastsService {
//
//    private final BreakfastsRepo breakfastsRepo;
//
//    @Override
//    public List<Breakfasts> getBreakfasts() {
//        return breakfastsRepo.findAll();
//    }
//
//    @Override
//    public Breakfasts createBreakfast(BreakfastsRequest request) {
//        return breakfastsRepo.save(Breakfasts.builder()
//                .name(request.getName())
//                .category(request.getCategory())
//                .time(request.getTime())
//                .build());
//    }
//
//    @Override
//    public Breakfasts putBreakfast(Breakfasts breakfasts) {
//        return breakfastsRepo.save(breakfasts);
//    }
//
//    @Override
//    public void deleteBreakfast(Long id) {
//        breakfastsRepo.deleteById(id);
//    }
//}
