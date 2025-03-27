package org.chobit.trino.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record SelectedRole(@JsonProperty("type") Type type,
                           @JsonProperty("role") String role) {

    public enum Type {
        ROLE, ALL, NONE
    }

    private static final Pattern PATTERN = Pattern.compile("(ROLE|ALL|NONE)(\\{(.+?)\\})?");


    public static SelectedRole valueOf(String value) {
        Matcher m = PATTERN.matcher(value);
        if (m.matches()) {
            Type type = Type.valueOf(m.group(1));
            String role = m.group(3);
            return new SelectedRole(type, role);
        }
        throw new IllegalArgumentException("Could not parse selected role: " + value);
    }
}
