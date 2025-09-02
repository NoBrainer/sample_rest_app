package com.example.sample_rest_app.util;

import com.github.dockerjava.zerodep.shaded.org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class TestAuthUtil {

    private static String USERNAME;
    private static String PASSWORD;

    // You cannot use @Value on a static variable, but you can get around this by putting it on its setter.
    // Important: This only works when this property is set in application.properties (not application-test.properties)
    @Value("${spring.security.user.name}")
    public void setUsername(String username) {
        USERNAME = username;
    }

    // You cannot use @Value on a static variable, but you can get around this by putting it on its setter.
    // Important: This only works when this property is set in application.properties (not application-test.properties)
    @Value("${spring.security.user.password}")
    public void setPassword(String password) {
        PASSWORD = password;
    }

    private static HttpHeaders headers;

    /**
     * HttpHeaders with an authorized user (configured in application.properties).
     */
    public static HttpHeaders headers() {
        if (headers == null) {
            String credentials = String.format("%s:%s", USERNAME, PASSWORD);
            byte[] bytes = credentials.getBytes();
            byte[] base64Bytes = Base64.encodeBase64(bytes);
            String encodedCredentials = new String(base64Bytes);
            headers = new HttpHeaders();
            headers.setBasicAuth(encodedCredentials);
        }
        return headers;
    }

    /**
     * HttpEntity with headers for an authorized user (configured in application.properties)
     */
    public static HttpEntity<String> request() {
        return new HttpEntity<>(headers());
    }
}
