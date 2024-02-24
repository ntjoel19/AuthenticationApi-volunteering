package com.volunteering.AuthenticationApi.error;

import com.volunteering.AuthenticationApi.auth.AuthenticationResponse;

public class ErrorResponse extends AuthenticationResponse {
    String message;

    public ErrorResponse(String s) {
    }
}
