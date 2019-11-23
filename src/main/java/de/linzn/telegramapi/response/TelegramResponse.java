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

package de.linzn.telegramapi.response;

public class TelegramResponse<T> {
    private T response;
    private int statusCode;

    public TelegramResponse(int statusCode, T response) {
        this.response = response;
        this.statusCode = statusCode;
    }

    public T getResponse() {
        return response;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
