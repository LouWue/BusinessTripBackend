package org.example.businesstripps.security.constants;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class SecurityConstants {
    public static final String SECRET = "2a3b1a12c712ca62b31d8e0dbac216909b84987e3c55f6871106c22453ec66198326d11bcd80297aaa900d9f11aa58948907b022f40000e65a38c79e13dd6079ffcf5b0c4bd2d53eaf27b99a6f71d06a33a22a212d199d6230d01dae89cfa0d44a8c66b29197c6c6bfda3fb9844babf14149be5ead774a0dadfc423b29949920e360fbc92692d79c92f2624ce05de27cf5f203caff03bac0aa543d5ccb3024bad5cee342ea0dc8c9829301d65e2a8ca6d6d41a5bad0ebf968e4390ecb31f4c294300f03ee9a039510f3414ebb4f4ff9a5e8d88c2ff0d8a7209b7be6c7ef5634af356758698961d7b1af4797ac9c7770580d189a9ecba907edbdfc7831c914396cb4e3d026991da6ed1dc8723b8331f4e4295109a0f9b1ec9b4acb2e6d5c6657b4aa8347e4059fb8c99faa057dd8c74fff291663a5cbe5eb4c1da2e655f5020b4954bbee73522d112566f9cd0e8d3d96a5e13e5bcb173df0e9a15395233cedc74a9c8e22bcd20a5a2fbaca6ff2f8d763876a6e561071d9932e0d7912ff861f91bccfa0be1261a96a25c66b33a75b106a1d6f20f354a9fd3f1ab57057c8afa9042b89eafea0dfcc3fa0c890306a212eec7070a546949fb10cecef6d940cd2ed6f1e0f047179b5a82c92df3d3427515a0ff4f65881a58a48087d2b7c1c650a3986fab97e14d9f2f68cfa311eb4e2c7810c775e6c0492b8d402e8e9380d2e94ca681";
    public static final String ALGORITHM = "HmacSHA256";
    public static final SecretKeySpec SECRET_KEY_SPEC = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), ALGORITHM);
    public static final long EXPIRATION_TIME = 864_000_000L; // 10 days

    private static final String API = "/api";
    private static final String API_VERSION = "/v1";
    public static final String AUTH_URLS = API + API_VERSION + "/auth/**";
    public static final String AUTHCONTROLLER_URL = API + API_VERSION + "/auth";
    public static final String SIGNUP_PATH = "/signup";
    public static final String SIGNIN_PATH = "/signin";
    public static final String SIGNOUT_PATH = "/signout";
    public static final String[] API_DOCUMENTATION_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**"
    };

    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    public static final String ROOT_PATH = "/";
}
