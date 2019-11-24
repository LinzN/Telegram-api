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

import de.linzn.telegramapi.interfaces.ITelegramAPI;
import de.linzn.telegramapi.response.TelegramResponse;
import de.linzn.telegramapi.utils.TelegramURLBuilder;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GetUpdate implements ITelegramAPI {
    private HttpRequest request;

    private GetUpdate(String token) {
        TelegramURLBuilder builder = TelegramURLBuilder
                .fromURI("https://api.telegram.org")
                .path("/{token}/getUpdates")
                .setToken(token);
        request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build())
                .timeout(Duration.ofSeconds(5))
                .build();
    }


    static GetUpdate init(String token) {
        return new GetUpdate(token);
    }

    @Override
    public TelegramResponse<String> sendAPIRequest() {
        TelegramResponse<String> telegramResponse;
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(5))
                .version(HttpClient.Version.HTTP_2)
                .build();
        try {
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            telegramResponse = new TelegramResponse<>(response.statusCode(), response.body());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            telegramResponse = new TelegramResponse<>(-1, "");
        }
        return telegramResponse;
    }
}
