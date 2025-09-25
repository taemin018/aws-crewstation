package com.example.crewstation.common.enumeration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Secret {

    Private("sub"), Public("main");

    private final String value;
    private static final Map<String, Secret> Secret_MAP =
            Arrays.stream(Secret.values()).collect(Collectors.toMap(Secret::getValue, Function.identity()));
    Secret(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return this.value;
    }
    @JsonCreator
    public static Secret getTypeFromValue(String value) {
        return Optional.ofNullable(Secret_MAP.get(value)).orElseThrow(IllegalArgumentException::new);
    }
}
