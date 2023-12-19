package com.example.telegram.listener;


import com.example.telegram.entity.Ads;
import com.example.telegram.entity.Person;
import com.example.telegram.property.TelegramBotProperty;
import com.example.telegram.repository.AdsRepo;
import com.example.telegram.repository.PersonRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class BotListenerLongPoll extends TelegramLongPollingBot {

    private final TelegramBotProperty telegramBotProperty;
    private final PersonRepo personRepo;
    private final AdsRepo adsRepo;
    private final KafkaTemplate<String, String> kafkaTemplate;



    static final String HELP_TEXT = "Using this bot you can get recipes to choose from if you don't know what to cook\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /recipe to chose recipe you want\n\n" +
            "Type /help to see this message again";
    static final String BREAKFAST_BUTTON = "Breakfast recipe";
    static final String DINNER_BUTTON = "Dinner recipe";
    static final String ERROR_TEXT = "Error occurred: ";

    public BotListenerLongPoll(TelegramBotProperty property, PersonRepo personRepo, AdsRepo adsRepo, KafkaTemplate<String, String> kafkaTemplate) {
        super(property.getToken());
        telegramBotProperty = property;
        this.personRepo = personRepo;
        this.adsRepo = adsRepo;
        this.kafkaTemplate = kafkaTemplate;
        List<BotCommand> listofCommands = new ArrayList<>();
        listofCommands.add(new BotCommand("/start", "get a welcome message"));
        listofCommands.add(new BotCommand("/help", "info how to use this bot"));
        listofCommands.add(new BotCommand("/recipe", "chose recipe"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();

            switch (message) {
                case "/start":
                    registerUser(update.getMessage());
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    log.info(String.format("ChatId : %s", update.getMessage().getChatId().toString()));
                    break;
                case "/help":
                    sendMessage(chatId, HELP_TEXT);
                    log.info("Replied to user " + update.getMessage().getChat().getFirstName());
                    log.info(String.format("ChatId : %s", update.getMessage().getChatId().toString()));
                    break;
                case "/recipe":
                    getRecipe(chatId);
                    break;

                default: sendMessage(chatId, "Error");

            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if (callbackData.equals(BREAKFAST_BUTTON)) {

                String text = "data from database breakfast";
                getBreakfasts(chatId);
                executeMessageText(text, chatId,messageId);


            } else if (callbackData.equals(DINNER_BUTTON)) {
                String text = "data from database dinner";
                getDinners(chatId);
                executeMessageText(text, chatId,messageId);
            }
            
        }
    }

    private void registerUser(Message msg) {
        if (personRepo.findById(msg.getChatId()).isEmpty()) {
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            Person person = new Person();
            person.setId(chatId);
            person.setName(chat.getFirstName());
            person.setUsername(chat.getUserName());

            personRepo.save(person);
            kafkaTemplate.send("bot-topic", "User saved");

        }
    }

    public void getDinners(long chatId) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8091/v2/api/dinners";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List dinnersWithIngredients = response.getBody();
        StringBuilder mes = new StringBuilder();
        for  (Object dinnerWithIngredients : dinnersWithIngredients) {
            String name = (String) ((Map) dinnerWithIngredients).get("name");
            String category = (String) ((Map) dinnerWithIngredients).get("category");
            String time = (String) ((Map) dinnerWithIngredients).get("time");

            List ingredients = (List) ((Map) dinnerWithIngredients).get("ingredients");
            mes.append("Name: ").append(name).append(", Category: ").append(category)
                    .append(", Cooking time: ").append(time).append("\n");
            mes.append("Ingredients: \n");

            for (Object ingredient : ingredients) {
                String product = (String) ((Map) ingredient).get("product");
                String amount = (String) ((Map) ingredient).get("amount");
                mes.append(" - Product: ").append(product).append(", Amount: ").append(amount).append("\n");
            }
            mes.append("\n");
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(mes.toString());
        executeMessage(message);

    }

    public void getBreakfasts(long chatId) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8092/api/breakfasts";
        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        List breakfastsWithIngredients = response.getBody();
        StringBuilder mes = new StringBuilder();
        for  (Object breakfastWithIngredients : breakfastsWithIngredients) {
            String name = (String) ((Map) breakfastWithIngredients).get("name");
            String category = (String) ((Map) breakfastWithIngredients).get("category");
            String time = (String) ((Map) breakfastWithIngredients).get("time");

            List ingredients = (List) ((Map) breakfastWithIngredients).get("ingredients");
            mes.append("Name: ").append(name).append(", Category: ").append(category)
                    .append(", Cooking time: ").append(time).append("\n");
            mes.append("Ingredients: \n");

            for (Object ingredient : ingredients) {
                String product = (String) ((Map) ingredient).get("product");
                String amount = (String) ((Map) ingredient).get("amount");
                mes.append(" - Product: ").append(product).append(", Amount: ").append(amount).append("\n");
            }
            mes.append("\n");
        }
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(mes.toString());
        executeMessage(message);
    }
    private void getRecipe(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("What recipe do you want?");

        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInList = new ArrayList<>();
        List<InlineKeyboardButton> rowInLine = new ArrayList<>();

        var BreakfastButton = new InlineKeyboardButton();
        BreakfastButton.setText("Breakfast recipe");
        BreakfastButton.setCallbackData(BREAKFAST_BUTTON);

        var DinnerButton = new InlineKeyboardButton();
        DinnerButton.setText("Dinner recipe");
        DinnerButton.setCallbackData(DINNER_BUTTON);

        rowInLine.add(BreakfastButton);
        rowInLine.add(DinnerButton);

        rowsInList.add(rowInLine);
        markup.setKeyboard(rowsInList);
        message.setReplyMarkup(markup);

        executeMessage(message);

    }

    private void executeMessageText(String text, long chatId, long messageId) {
        EditMessageText messageText = new EditMessageText();
        messageText.setChatId(String.valueOf(chatId));
        messageText.setText(text);
        messageText.setMessageId((int) messageId);

        try {
            execute(messageText);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }


    private void startCommand(long chatId, String name) {

        String answer = "Hi, " + name + " nice to meet you!";
        sendMessage(chatId, answer);
        log.info("Replied to user " + name);

    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(ERROR_TEXT + e.getMessage());
        }
    }

    @Scheduled(cron = "0 * * * * *")
    private void sendAds() {

        var ads = adsRepo.findAll();
        var persons = personRepo.findAll();
        for (Ads ad: ads) {
            for (Person person: persons) {
                sendMessage(person.getId(), ad.getText());
            }
        }

    }

    @Override
    public String getBotUsername() {
        return telegramBotProperty.getName();
    }
}
