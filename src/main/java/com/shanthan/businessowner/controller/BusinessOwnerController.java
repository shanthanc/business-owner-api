package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.model.BusinessOwner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/businessowner/service")
@RestController
@Slf4j
public class BusinessOwnerController {

    @GetMapping(value = "/getBusinessOwner/{businessId}")
    public BusinessOwner getBusinessOwnerWithId(@PathVariable Long businessId) {
        return null;
    }
}
