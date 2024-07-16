package com.cyberspeed.game;

import com.cyberspeed.game.model.Config;
import com.cyberspeed.game.game.Game;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            String configFilePath = args[1];
            int bettingAmount = Integer.parseInt(args[3]);

            //ObjectMapper objectMapper = new ObjectMapper();
            BufferedReader br = new BufferedReader(new FileReader("config.json"));
            Config config = new GsonBuilder()
                    .setFieldNamingStrategy(new FieldNamingStrategy() {
                        @Override
                        public String translateName(Field field) {
                            return FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field);
                        }
                    })
                    .create().fromJson(br, Config.class);
            System.out.println();
            Game game = new Game(config);
            game.play(bettingAmount);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }
}
