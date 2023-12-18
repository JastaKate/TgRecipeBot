//package com.example.telegram.controller;
//
//import com.example.telegram.entity.Breakfasts;
//import com.example.telegram.request.BreakfastsRequest;
//import com.example.telegram.service.BreakfastsServiceImpl;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class BreakfastsController {
//
//    private final BreakfastsServiceImpl breakfastsService;
//
//    @GetMapping("/breakfasts")
//    public ResponseEntity<List<Breakfasts>> getBreakfasts() {
//        return new ResponseEntity<>(breakfastsService.getBreakfasts(), HttpStatus.OK);
//    }
//
//    @PostMapping("/breakfasts")
//    public ResponseEntity<Breakfasts> createBreakfast(@RequestBody @Valid BreakfastsRequest request) {
//        return new ResponseEntity<>(breakfastsService.createBreakfast(request), HttpStatus.OK);
//    }
//
//    @PutMapping("/breakfasts")
//    public ResponseEntity<Breakfasts> putBreakfast(@RequestBody @Valid Breakfasts breakfasts) {
//        return new ResponseEntity<>(breakfastsService.putBreakfast(breakfasts),HttpStatus.OK);
//    }
//
//    @DeleteMapping("/breakfasts/{id}")
//    public HttpStatus deleteBreakfast(@PathVariable Long id) {
//        breakfastsService.deleteBreakfast(id);
//        return HttpStatus.OK;
//    }
//
//
//}
