package me.bo0tzz.cvbot.engine;

import com.google.gson.Gson;
import com.jtelegram.api.events.EventHandler;
import com.jtelegram.api.events.message.PhotoMessageEvent;
import com.jtelegram.api.requests.GetFile;
import com.jtelegram.api.requests.message.send.SendText;
import me.bo0tzz.cvbot.CVBot;
import me.bo0tzz.cvbot.bean.Prediction;
import me.bo0tzz.cvbot.bean.PredictionResult;
import me.bo0tzz.cvbot.config.Configuration;
import okhttp3.*;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CVBotHandler implements EventHandler<PhotoMessageEvent> {

    private final CVBot CVBot;
    private final Configuration configuration;
    private final OkHttpClient httpClient;
    private final Gson gson;

    public static final MediaType MULTIPART = MediaType.parse("multipart/form-data");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    public static final String FORMAT = "I am %.2f%% sure that your image is %s.";
    private static final String imagePath = System.getProperty("java.io.tmpdir") + File.separator + "image.jpg";

    public CVBotHandler(CVBot CVBot, Configuration configuration) {
        this.CVBot = CVBot;
        this.configuration = configuration;
        this.httpClient = new OkHttpClient();
        this.gson = new Gson();
    }

    private Response postImage(InputStream download) throws IOException {
        byte[] image = IOUtils.toByteArray(download);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), image);
        Request request = new Request.Builder()
                .url(configuration.getApiUrl())
                .post(body)
                .build();
        return httpClient.newCall(request).execute();
    }

    private void processImage(PhotoMessageEvent event, Response response) throws IOException {
        PredictionResult result = gson.fromJson(response.body().string(), PredictionResult.class);

        double probability = 0;
        String tag = "";

        for (Prediction p : result.getPredictions()) {
            if (p.getProbability() > probability) {
                probability = p.getProbability();
                tag = p.getTagName();
            }
        }

        if (event.getMessage().getChat().getType().equals("PRIVATE") || (probability >= 0.6 && !tag.startsWith("Not"))) {
            String message = String.format(FORMAT, probability * 100, tag);
            CVBot.getBot().perform(SendText.builder()
                    .chatId(event.getMessage().getChat().getChatId())
                    .replyToMessageID(event.getMessage().getMessageId())
                    .text(message)
                    .build());
        }
    }

    @Override
    public void onEvent(PhotoMessageEvent event) {
        try {

            this.CVBot.getBot().perform(GetFile.builder()
                    .fileId(event.getMessage().getHighestResolutionPhoto().getFileId())
                    .errorHandler((error) -> {
                        System.out.println(error.getDescription());
                        System.out.println(error.getMessage());
                    })
                    .callback((file) -> {
                        try {
                            InputStream inputStream = this.CVBot.getBot().downloadFile(file);
                            Response response = postImage(inputStream);
                            if (response != null && response.isSuccessful()) {
                                processImage(event, response);
                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    })
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
            CVBot.getBot().perform(SendText.builder()
                    .chatId(event.getMessage().getChat().getChatId())
                    .text("I had an error.")
                    .build());
        }
    }
}




