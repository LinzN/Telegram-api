/*
 * Copyright (c) 2025 MirraNET, Niklas Linz. All rights reserved.
 *
 * This file is part of the MirraNET project and is licensed under the
 * GNU Lesser General Public License v3.0 (LGPLv3).
 *
 * You may use, distribute and modify this code under the terms
 * of the LGPLv3 license. You should have received a copy of the
 * license along with this file. If not, see <https://www.gnu.org/licenses/lgpl-3.0.html>
 * or contact: niklas.linz@mirranet.de
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

    public TelegramResponse<String> getUpdate(long offset) {
        return GetUpdate.init(this.token, offset).sendAPIRequest();
    }
}
