package com.example.TestDeploy.controller;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.*;

public class TestDeploy extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "YOUR_BOT_TOKEN";
    private final String BOT_USERNAME = "YOUR_BOT_USERNAME";

    // user state saqlash
    private final Map<Long, String> userState = new HashMap<>();
    private final List<String> registeredUsers = new ArrayList<>();

    @Override
    public String getBotUsername() {
        return "TestDeploy_1_bot";
    }

    @Override
    public String getBotToken() {
        return "8208361342:AAF6zOdKaFls_Wo962bHHSPOLPJEuTYZzZw";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();

            // agar foydalanuvchi state holatida bo'lsa
            if (userState.containsKey(chatId)) {
                String state = userState.get(chatId);

                if (state.equals("REGISTER_NAME")) {
                    registeredUsers.add(text); // ismini listga qo'shamiz
                    sendMessage(chatId, "Ro'yxatdan o'tdingiz! ✅");
                    userState.remove(chatId); // holatni tozalash
                    return;
                }
            }

            // asosiy menyu
            switch (text) {
                case "/start" -> sendMenu(chatId);
                case "1. Ro'yxatdan o'tish" -> {
                    sendMessage(chatId, "Ismingizni kiriting:");
                    userState.put(chatId, "REGISTER_NAME"); // foydalanuvchi holatini saqlaymiz
                }
                case "2. Ro'yxatdan o'tganlarni ko'rish" -> {
                    if (registeredUsers.isEmpty()) {
                        sendMessage(chatId, "Hali hech kim ro'yxatdan o'tmagan.");
                    } else {
                        StringBuilder sb = new StringBuilder("Ro'yxatdan o'tganlar:\n");
                        for (String u : registeredUsers) {
                            sb.append("- ").append(u).append("\n");
                        }
                        sendMessage(chatId, sb.toString());
                    }
                }
                default -> sendMessage(chatId, "Iltimos, menyudan tugma orqali tanlang.");
            }
        }
    }

    private void sendMenu(Long chatId) {
        SendMessage message = new SendMessage(chatId.toString(), "Tanlang:");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("1. Ro'yxatdan o'tish");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("2. Ro'yxatdan o'tganlarni ko'rish");

        rows.add(row1);
        rows.add(row2);

        keyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Botni ishga tushirish
    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TestDeploy());
            System.out.println("Bot ishga tushdi...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
