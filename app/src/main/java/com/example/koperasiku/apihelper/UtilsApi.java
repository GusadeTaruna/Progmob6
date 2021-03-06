package com.example.koperasiku.apihelper;

public class UtilsApi {

    // 10.0.2.2 ini adalah localhost.
    public static final String BASE_URL_API = "https://progmob6api.herokuapp.com/api/";
//    public static final String BASE_URL_API = "https://05d8b612.ngrok.io/api/";


    // Mendeklarasikan Interface BaseApiService
    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

    public static BaseApiService getAPIServiceWithToken(String token){
        return RetrofitClient.getClientWithToken(BASE_URL_API,token).create(BaseApiService.class);
    }
}
