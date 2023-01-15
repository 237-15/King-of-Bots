package com.kob.backend;

import com.kob.backend.consumer.utils.BotTool;
import com.kob.backend.consumer.utils.MatchTool;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
		MatchTool matchTool = new MatchTool();
		matchTool.start();
		BotTool botTool = new BotTool();
		botTool.start();
	}

}


