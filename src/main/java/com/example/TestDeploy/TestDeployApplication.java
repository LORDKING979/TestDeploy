package com.example.TestDeploy;

import com.example.TestDeploy.controller.TestDeploy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class TestDeployApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestDeployApplication.class, args);
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TestDeploy());
			System.out.println("Bot ishga tushdi...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
