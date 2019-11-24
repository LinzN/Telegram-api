/*
 * Copyright (C) 2019. Niklas Linz - All Rights Reserved
 * You may use, distribute and modify this code under the
 * terms of the LGPLv3 license, which unfortunately won't be
 * written for another century.
 *
 * You should have received a copy of the LGPLv3 license with
 * this file. If not, please write to: niklas.linz@enigmar.de
 *
 */

package de.linzn.telegramapi.staticApp;

import de.linzn.telegramapi.TelegramAPI;

public class TelegramStaticApp {

    public static void main(String[] args) {
        if (args.length >= 3) {
            String token = args[0];
            String chatID = args[1];
            StringBuilder text = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                text.append(args[i]).append(" ");
            }
            TelegramAPI telegramAPI = new TelegramAPI(token);
            System.out.println(telegramAPI.sendMessage(chatID, text.toString()).getResponse());
        } else {
            System.out.println("Invalid Arguments! Usage: <token> <chatID> <Text...>");
        }

    }
}
