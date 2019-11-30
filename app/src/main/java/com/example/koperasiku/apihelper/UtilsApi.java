package com.example.koperasiku.apihelper;

public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
//    public static final String BASE_URL_API = "https://progmob6api.herokuapp.com/api/";
    public static final String BASE_URL_API = "https://92e342e1.ngrok.io/api/";


    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }
}
