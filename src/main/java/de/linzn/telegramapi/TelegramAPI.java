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

package de.linzn.telegramapi;

import de.linzn.telegramapi.response.TelegramResponse;

public class TelegramAPI {
    private String token;

    public TelegramAPI(String token) {
        this.token = token;
    }

    public TelegramResponse<String> sendMessage(String chatID, String text) {
        return SendMessage.init(this.token, chatID, text).sendAPIRequest();
    }
}
