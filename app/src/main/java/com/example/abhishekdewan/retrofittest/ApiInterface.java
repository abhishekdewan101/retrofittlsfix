package com.example.abhishekdewan.retrofittest;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by abhishekdewan on 9/12/17.
 */

public interface ApiInterface {

    @GET("/configs?expand=all&state=CA")
    Call<Void> getCommitsByName();

}
