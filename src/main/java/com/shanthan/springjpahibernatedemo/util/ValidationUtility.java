package com.shanthan.springjpahibernatedemo.util;

import com.shanthan.springjpahibernatedemo.model.State;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

import static java.util.stream.Stream.of;

@Component
public class ValidationUtility {

    public boolean validateState(String state) {
        List<String> stateNames = of(State.values())
                .map(State::getName)
                .toList();
        List<String> stateAbbreviations = of(State.values())
                .map(State::getAbbreviation)
                .toList();

        if (!stateNames.contains(state) && !stateAbbreviations.contains(state)) {
            return false;
        }
        return true;
    }

    public static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$",
            Pattern.CASE_INSENSITIVE
    );
}
