package com.example.telegram.listener;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.telegram.listener.BotListenerLongPoll.BREAKFAST_BUTTON;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BotListenerLongPollTest {


    @Test
    void getDinners() {
        long chatId = 123456;
        List<Map<String, Object>> expectedResult = Arrays.asList(
                Map.of(
                        "name", "Spaghetti",
                        "category", "Pasta",
                        "time", "30 minutes",
                        "ingredients", Arrays.asList(
                                Map.of("product", "Spaghetti", "amount", "200"),
                                Map.of("product", "Tomato sauce", "amount", "1")
                        )
                ),
                Map.of(
                        "name", "Burger",
                        "category", "American",
                        "time", "20 minutes",
                        "ingredients", Arrays.asList(
                                Map.of("product", "Ground beef", "amount", "150"),
                                Map.of("product", "Burger buns", "amount", "2")
                        )
                )
        );

        ResponseEntity<List> response = new ResponseEntity<List>(expectedResult, HttpStatus.OK);
        List actualResult = response.getBody();


        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult.get(0), actualResult.get(0));
        assertEquals(expectedResult.get(1), actualResult.get(1));

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("Name: Spaghetti, Category: Pasta, Cooking time: 30 minutes").append("\n");
        expectedMessage.append("Ingredients: ").append("\n");
        expectedMessage.append(" - Product: Spaghetti, Amount: 200").append("\n");
        expectedMessage.append(" - Product: Tomato sauce, Amount: 1").append("\n");
        expectedMessage.append("\n");
        expectedMessage.append("Name: Burger, Category: American, Cooking time: 20 minutes").append("\n");
        expectedMessage.append("Ingredients: ").append("\n");
        expectedMessage.append(" - Product: Ground beef, Amount: 150").append("\n");
        expectedMessage.append(" - Product: Burger buns, Amount: 2").append("\n");
        expectedMessage.append("\n");

        SendMessage expectedSendMessage = new SendMessage();
        expectedSendMessage.setChatId(String.valueOf(chatId));
        expectedSendMessage.setText(actualResult.toString());
        SendMessage actualSendMessage = new SendMessage();
        actualSendMessage.setChatId(String.valueOf(chatId));
        actualSendMessage.setText(actualResult.toString());

        assertEquals(expectedSendMessage.getChatId(), actualSendMessage.getChatId());
        assertEquals(expectedSendMessage.getText(), actualSendMessage.getText());
    }

    @Test
    void getBreakfasts() {
        long chatId = 123456;
        List<Map<String, Object>> expectedResult = Arrays.asList(
                Map.of(
                        "name", "Oatmeal",
                        "category", "Universal",
                        "time", "10 minutes",
                        "ingredients", Arrays.asList(
                                Map.of("product", "Oatmeal", "amount", "200"),
                                Map.of("product", "Milk", "amount", "1 glass")
                        )
                ),
                Map.of(
                        "name", "Omelet",
                        "category", "Universal",
                        "time", "15 minutes",
                        "ingredients", Arrays.asList(
                                Map.of("product", "Milk", "amount", "2 spoons"),
                                Map.of("product", "Eggs", "amount", "2")
                        )
                )
        );

        ResponseEntity<List> response = new ResponseEntity<List>(expectedResult, HttpStatus.OK);
        List actualResult = response.getBody();


        assertEquals(expectedResult.size(), actualResult.size());
        assertEquals(expectedResult.get(0), actualResult.get(0));
        assertEquals(expectedResult.get(1), actualResult.get(1));

        StringBuilder expectedMessage = new StringBuilder();
        expectedMessage.append("Name: Oatmeal, Category: Universal, Cooking time: 10 minutes").append("\n");
        expectedMessage.append("Ingredients: ").append("\n");
        expectedMessage.append(" - Product: Oatmeal, Amount: 200").append("\n");
        expectedMessage.append(" - Product: Milk, Amount: 1 glass").append("\n");
        expectedMessage.append("\n");
        expectedMessage.append("Name: Omelet, Category: Universal, Cooking time: 15 minutes").append("\n");
        expectedMessage.append("Ingredients: ").append("\n");
        expectedMessage.append(" - Product: Milk, Amount: 2 spoons").append("\n");
        expectedMessage.append(" - Product: Eggs, Amount: 2").append("\n");
        expectedMessage.append("\n");

        SendMessage expectedSendMessage = new SendMessage();
        expectedSendMessage.setChatId(String.valueOf(chatId));
        expectedSendMessage.setText(actualResult.toString());
        SendMessage actualSendMessage = new SendMessage();
        actualSendMessage.setChatId(String.valueOf(chatId));
        actualSendMessage.setText(actualResult.toString());

        assertEquals(expectedSendMessage.getChatId(), actualSendMessage.getChatId());
        assertEquals(expectedSendMessage.getText(), actualSendMessage.getText());
    }

}