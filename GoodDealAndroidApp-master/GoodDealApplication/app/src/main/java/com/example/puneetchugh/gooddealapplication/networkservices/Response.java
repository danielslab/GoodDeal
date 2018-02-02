package com.example.puneetchugh.gooddealapplication.networkservices;

import com.google.gson.annotations.SerializedName;

/**
 * Created by puneetchugh on 1/30/18.
 */

public class Response {
    @SerializedName("status")
    public String status;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }
}
