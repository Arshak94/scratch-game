package com.cyberspeed.game;

import com.cyberspeed.game.game.Game;
import com.cyberspeed.game.model.Config;
import com.cyberspeed.game.result.GameResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        try {
            String configFilePath = args[1];
            int bettingAmount = Integer.parseInt(args[3]);
            ObjectMapper objectMapper = new ObjectMapper();
            Config config = objectMapper.readValue(new File(configFilePath), Config.class);

            Game game = new Game(config);
            GameResult result = game.play(bettingAmount);
            objectMapper.writeValue(new File(System.getProperty("user.home")+"/result.json"), result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
