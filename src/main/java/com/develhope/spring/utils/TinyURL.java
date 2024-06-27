package com.develhope.spring.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class TinyURL {

    public static String shortUrl(String longUrl) throws IOException {
        String requestUrl = "http://tinyurl.com/api-create.php?url=" + longUrl;
        HttpURLConnection connection = (HttpURLConnection) new URL(requestUrl).openConnection();
        connection.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(connection.getInputStream())) {
            return scanner.nextLine();
        }
    }
}
