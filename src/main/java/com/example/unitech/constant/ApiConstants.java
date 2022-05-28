package com.example.unitech.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstants {

    // api general
    public static final String API_VERSION_V1 = "1.0";
    public static final String API_BASE_PATH = "/api/" + API_VERSION_V1;

    // resource paths
    public static final String API_AUTHENTICATION = API_BASE_PATH + "/authentication";

    // paths
    public static final String PATH_ID = "/{id}";
    public static final String PATH_TOKEN = "/token";
    public static final String PATH_ANT_MATCH_ALL = "/**";


    public static final String CURRENCY_URL="https://api.apilayer.com/fixer/convert";

}
