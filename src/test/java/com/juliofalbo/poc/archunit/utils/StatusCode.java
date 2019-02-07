package com.juliofalbo.poc.archunit.utils;

import lombok.Getter;

@Getter
public enum StatusCode {
    CREATED("created"), OK("ok"), NO_CONTENT("noContent");

    private String value;

    StatusCode(final String value) {
        this.value = value;
    }
}
