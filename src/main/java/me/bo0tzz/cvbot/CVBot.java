package me.bo0tzz.cvbot;

import com.jtelegram.api.TelegramBot;
import com.jtelegram.api.TelegramBotRegistry;
import com.jtelegram.api.events.message.PhotoMessageEvent;
import com.jtelegram.api.ex.TelegramException;
import com.jtelegram.api.update.PollingUpdateProvider;
import me.bo0tzz.cvbot.config.Configuration;
import me.bo0tzz.cvbot.engine.CVBotHandler;

public class CVBot {

    private TelegramBot bot;

    private final Configuration configuration;

    public static void main(String[] args) {
        new CVBot(args);
    }

    public CVBot(String[] args) {

        this.configuration = new Configuration(args);

        TelegramBotRegistry registry = TelegramBotRegistry.builder()
                .updateProvider(new PollingUpdateProvider())
                .eventThreadCount(10)
                .build();

        registry.registerBot(configuration.getTelegramKey(), this::setupTelegramBot);

    }

    public TelegramBot getBot() {
        return bot;
    }

    private void setupTelegramBot(TelegramBot telegramBot, TelegramException error) {
        if (error != null) CVBot.handleFatalError(error);

        this.bot = telegramBot;

        this.bot.getEventRegistry().registerEvent(
                PhotoMessageEvent.class,
                new CVBotHandler(this, configuration));
    }

    public static void handleError(Exception ex) {
        System.out.println("Error occurred!");
        ex.printStackTrace();
    }

    public static void handleFatalError(Exception ex) {
        System.out.println("Error occurred!");
        ex.printStackTrace();
        System.exit(1);
    }

}

