package com.shanthan.businessowner.service;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.State;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDate.*;
import static java.util.stream.Stream.of;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.util.ObjectUtils.*;

@Component
@Slf4j
public class ValidationService {

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

    public LocalDate validDateOfBirth(String dob) throws BusinessOwnerException {
        LocalDate dateOfBirth;
        log.info("Validating date of birth -> {} ", dob);
        if (!isEmpty(dob) && !dob.isBlank()) {
            dateOfBirth = parse(dob, DateTimeFormatter.ISO_LOCAL_DATE);
            if (dateOfBirth.isAfter(now())) {
                log.error("Invalid Date of Birth! ");
                throw new BusinessOwnerException(BAD_REQUEST, "Invalid Date of birth. ");
            }

            log.info("Verifying if candidate is atleast 18 years or older... ");
            LocalDate today = now();
            Period p = Period.between(dateOfBirth, today);
            boolean is18OrOlder = p.getYears() >= 18;

            if (!is18OrOlder) {
                log.error("Candidate to be added as business owner is less than 18 years of age ");
                throw new BusinessOwnerException(BAD_REQUEST, "Business owner must be 18 years or older");
            }
        }
        else {
            log.error("Received date of birth as null or empty value ");
            throw new BusinessOwnerException(BAD_REQUEST, "Date of birth is null ");
        }
        log.info("Date of birth validity verification complete. ");
        return dateOfBirth;
    }
}
