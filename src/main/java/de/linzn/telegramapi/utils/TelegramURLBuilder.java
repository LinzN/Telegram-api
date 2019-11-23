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

package de.linzn.telegramapi.utils;

import java.net.URI;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TelegramURLBuilder {

    private String uri;
    private String path;
    private String token;

    private HashMap<String, Object> queryParams = new LinkedHashMap<>();

    private TelegramURLBuilder(String uri) {
        this.uri = uri;
    }

    public static TelegramURLBuilder fromURI(String uri) {
        return new TelegramURLBuilder(uri);
    }

    public TelegramURLBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public TelegramURLBuilder path(String path) {
        this.path = path;
        return this;
    }

    public TelegramURLBuilder queryParam(String key, Object value) {
        if (value instanceof String) {
            this.queryParams.put(key, ((String) value).replace(" ", "+"));
        } else {
            this.queryParams.put(key, value.toString());
        }
        return this;
    }

    public URI build() {
        int i = queryParams.size() - 1;
        StringBuilder paramList = new StringBuilder();
        for (String param : this.queryParams.keySet()) {
            paramList.append(param).append("=").append(this.queryParams.get(param));
            if (i != 0) {
                paramList.append("&");
                i--;
            }
        }
        return URI.create(uri + path.replace("{token}", "bot" + this.token) + "?" + paramList.toString());
    }
}
