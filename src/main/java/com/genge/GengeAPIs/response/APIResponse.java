package com.genge.GengeAPIs.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class APIResponse<T>{

    private boolean status;
    private String message;
    private T data;

    public static <T> APIResponse<T> success (String message, T data) {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(true);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    public static <T> APIResponse<T> fail(String message) {
        APIResponse<T> response = new APIResponse<>();
        response.setStatus(false);
        response.setMessage(message);
        response.setData(null);
        return response;
    }
}
