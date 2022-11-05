package me.n2g7mutf8.soup.utils.chat;

import java.util.HashMap;
import java.util.Map;

public class MessageUtil {

    private final Map<String, String> variableMap;

    public MessageUtil() {
        variableMap = new HashMap<>();
    }

    public MessageUtil setVariable(String variable, String value) {
        if (variable != null && !variable.isEmpty()) {
            variableMap.put(variable, value);
        }
        return this;
    }

    public String format(String message) {
        if (message == null || message.isEmpty()) {
            return "";
        }

        for (Map.Entry<String, String> entry : variableMap.entrySet()) {
            String s = entry.getKey();
            String s2 = entry.getValue();
            message = message.replace(s, s2);
        }

        return ColorText.translate(message);
    }
}