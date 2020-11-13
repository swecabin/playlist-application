package com.cloud.thealphaproject.thealpha.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PingResponse {

    private String message;
}
