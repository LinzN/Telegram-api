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

    private GetUpdate(String token, long offset) {
        TelegramURLBuilder builder = TelegramURLBuilder
                .fromURI("https://api.telegram.org")
                .path("/{token}/getUpdates")
                .setToken(token);
        if (offset != 0) {
            builder.queryParam("offset", offset);
        }
        request = HttpRequest.newBuilder()
                .GET()
                .uri(builder.build())
                .timeout(Duration.ofSeconds(5))
                .build();
    }

    static GetUpdate init(String token, long offset) {
        return new GetUpdate(token, offset);
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
