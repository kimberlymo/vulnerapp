package ch.bbw.m183.vulnerapp;

import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public abstract class WebTestClientHelper {
    private static final String ADMIN_CREDENTIALS = "admin@admin.ch:super5ecret";
    private static final String USER_CREDENTIALS = "fuu@fuu.ch:barbarbar";

    public static void addAdminCredentialsToHeader(HttpHeaders httpHeaders) {
        httpHeaders.add("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString(ADMIN_CREDENTIALS.getBytes(StandardCharsets.UTF_8)));
    }

    public static void addUserCredentialsToHeader(HttpHeaders httpHeaders) {
        httpHeaders.add("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString(USER_CREDENTIALS.getBytes(StandardCharsets.UTF_8)));
    }

    public static void addInvalidCredentialsToHeader(HttpHeaders httpHeaders) {
        httpHeaders.add("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString("test@tets.com:812937jf".getBytes(StandardCharsets.UTF_8)));
    }

    public static void addInvalidPasswordCredentialsToHeader(HttpHeaders httpHeaders) {
        httpHeaders.add("Authorization", "Basic " + Base64.getEncoder()
                .encodeToString("fuu@fuu.ch:812937jf".getBytes(StandardCharsets.UTF_8)));
    }
}
