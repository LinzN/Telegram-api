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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TelegramStaticApp {

    public static void main(String[] args) {
        if (args.length >= 2) {
            String command = args[0];
            String token = args[1];
            if (command.equalsIgnoreCase("send")) {
                send(token, Arrays.copyOfRange(args, 2, args.length));
            } else if (command.equalsIgnoreCase("getUpdateRaw")) {
                getUpdateRaw(token);
            } else if (command.equalsIgnoreCase("getUpdate")) {
                getUpdate(token);
            } else {
                System.out.println("Command not found!");
                help();
            }
        } else {
            help();
        }
    }

    private static void help() {
        System.out.println();
        System.out.println("Usage: <command> <token> [arguments...]");
        System.out.println();
        System.out.println("Commands:");
        System.out.println("    send <token> <chatID> [text...]     Send message to chatID");
        System.out.println("    getUpdateRaw <token>                Get raw json updates of new chats");
        System.out.println("    getUpdate <token>                   Get updates of new chats");
        System.out.println();
    }

    private static void send(String token, String[] args) {
        if (args.length >= 2) {
            String chatID = args[0];
            StringBuilder text = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                text.append(args[i]).append(" ");
            }
            TelegramAPI telegramAPI = new TelegramAPI(token);
            System.out.println(telegramAPI.sendMessage(chatID, text.toString()).getResponse());
        } else {
            System.out.println("Invalid Arguments! Usage: send <token> <chatID> [text...]");
        }
    }

    private static void getUpdateRaw(String token) {
        TelegramAPI telegramAPI = new TelegramAPI(token);
        System.out.println(telegramAPI.getUpdate(0).getResponse());
    }

    private static void getUpdate(String token) {
        TelegramAPI telegramAPI = new TelegramAPI(token);
        JSONObject sampleObject = new JSONObject(telegramAPI.getUpdate(0).getResponse());
        JSONArray jsonArray = sampleObject.getJSONArray("result");
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                System.out.println();
                System.out.println(format(jsonArray.getJSONObject(i)));
            } catch (JSONException e) {
                System.out.println("Error!");
            }
        }
    }

    private static String format(JSONObject jsonObject) {
        StringBuilder output = new StringBuilder();
        JSONObject chat = jsonObject.getJSONObject("message").getJSONObject("chat");

        boolean isGroup = chat.getString("type").equalsIgnoreCase("group");
        int chatID = chat.getInt("id");
        String text = jsonObject.getJSONObject("message").getString("text");
        String sender;
        if (jsonObject.getJSONObject("message").getJSONObject("from").has("username")) {
            sender = jsonObject.getJSONObject("message").getJSONObject("from").getString("username");
        } else {
            sender = jsonObject.getJSONObject("message").getJSONObject("from").getString("first_name");
        }
        int senderChatID = jsonObject.getJSONObject("message").getJSONObject("from").getInt("id");
        Date date = new Date(jsonObject.getJSONObject("message").getLong("date") * 1000);

        output.append("ChatEntity: ").append("\n");
        output.append("date: ").append(new SimpleDateFormat("yyyy.MM.dd - HH:mm:ss").format(date)).append("\n");
        output.append("isGroup: ").append(isGroup).append("\n");
        if (isGroup) {
            output.append("groupName: ").append(chat.getString("title")).append("\n");
        }
        output.append("chatID: ").append(chatID).append("\n");

        output.append("sender: ").append(sender).append("\n");

        if (isGroup) {
            output.append("senderChatID: ").append(senderChatID).append("\n");
        }

        output.append("text: ").append(text);
        return output.toString();
    }
}
