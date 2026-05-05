package com.drl.utils;

import java.util.List;
import java.util.Map;

import com.drl.entities.FramesRights;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthenticationResponse {

    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("frames_rights")
    Map<String, Object> claims;
    
}
