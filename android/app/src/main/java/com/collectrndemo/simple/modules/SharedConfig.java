package com.collectrndemo.simple.modules;

import java.util.HashMap;
import java.util.Map;

public class SharedConfig {

    public static final String VAULT_ID = "<VAULT_ID>";

    public static final String ENVIRONMENT = "<ENVIRONMENT>";

    public static final String CARD_NUMBER_FIELD_NAME = "card_number";
    public static final String EXPIRATION_DATE_FIELD_NAME = "card_expirationDate";
    public static final String CARD_NUMBER_CONTENT_PATH = "json.payment_card_number";
    public static final String EXPIRATION_DATE_CONTENT_PATH = "json.payment_card_expirationDate";
    public static final String CARD_NUMBER_PAYLOAD_KEY = "payment_card_number";
    public static final String EXPIRATION_DATE_PAYLOAD_KEY = "payment_card_expirationDate";

    public static Map<String, String> showPayload = new HashMap<>();
}
