package me.bo0tzz.cvbot.config;

public class Configuration {

    private final String telegramKey;
    private final String apiUrl;

    public Configuration(String[] args) {
        this.telegramKey = getTelegramKey(args);
        this.apiUrl = getApiUrl(args);
    }

    private String getTelegramKey(String[] args) {
        String apiKey = System.getenv("BOT_KEY");
        if (apiKey == null || apiKey.equals("")) {
            if (args.length < 1) {
                System.exit(0);
            }
            apiKey = args[0];
        }
        return apiKey;
    }

    private String getApiUrl(String[] args) {
        String url = System.getenv("API_URL");
        if (url == null || url.equals("")) {
            if (args.length < 2) {
                System.exit(0);
            }
            url = args[1];
        }
        return url;
    }

    public String getTelegramKey() {
        return telegramKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }
}
