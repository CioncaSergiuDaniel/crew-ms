package com.abac.crew.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder(builderClassName = "Builder")
@NoArgsConstructor
@AllArgsConstructor
public class ErrorTO {

    @Getter
    @Setter
    @JsonProperty(value = "errorCode")
    private String errorCode;

    @Getter
    @Setter
    @JsonProperty(value = "message")
    private String message;
}
