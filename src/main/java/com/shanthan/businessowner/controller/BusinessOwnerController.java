package com.shanthan.businessowner.controller;

import com.shanthan.businessowner.exception.BusinessOwnerException;
import com.shanthan.businessowner.model.BusinessOwner;
import com.shanthan.businessowner.model.DefaultResponse;
import com.shanthan.businessowner.service.BusinessOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.util.ObjectUtils.isEmpty;

@RequestMapping("/api")
@RestController
@Slf4j
public class BusinessOwnerController {

    private final BusinessOwnerService businessOwnerService;

    public BusinessOwnerController(BusinessOwnerService businessOwnerService) {
        this.businessOwnerService = businessOwnerService;
    }

    @Operation(summary = "Add Business Owner to DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added BusinessOwner to the database successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for adding BusinessOwner. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @PostMapping(value = "v1/addBusinessOwner/businessOwner")
    public ResponseEntity addBusinessOwner(@Valid @RequestBody BusinessOwner businessOwner) throws BusinessOwnerException {
        log.info("Calling BusinessOwner Service to addBusinessOwner -> [{}] to Db ", businessOwner);
        BusinessOwner responseBody = businessOwnerService.addBusinessOwner(businessOwner);
        String idField = responseBody.getBusinessId().toString();
        return status(CREATED).body(DefaultResponse.builder()
                .field(idField)
                .message("Business Owner with businessId created -> " + idField + " added successfully. ")
                .build());
    }

    @Operation(summary = "Update Existing Business Owner in DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BusinessOwner updated in database successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "204", description = "BusinessOwner to be updated does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for updating BusinessOwner. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @PutMapping(value = "v1/updateBusinessOwner/businessOwner")
    public ResponseEntity updateBusinessOwner(@Valid @RequestBody BusinessOwner businessOwner) throws BusinessOwnerException {
        log.info("Calling BusinessOwner Service to updateBusinessOwner with id -> {} ", businessOwner.getBusinessId());
        BusinessOwner result = businessOwnerService.updateBusinessOwner(businessOwner);
        if (isEmpty(result.getBusinessId())) {
            return status(NO_CONTENT)
                    .body(DefaultResponse.builder()
                            .field(businessOwner.getBusinessId().toString())
                            .message("Business owner with id -> " + businessOwner.getBusinessId() + " does not exist ")
                            .build());
        }
        return ok(DefaultResponse.builder()
                .field(businessOwner.getBusinessId().toString())
                .message("Business Owner with above id updated successfully")
                .build());
    }

    @Operation(summary = "Get Business Owner with given businessId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BusinessOwner details retrieved",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "204", description = "BusinessOwner does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for retrieving BusinessOwner. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @GetMapping(value = "v1/getBusinessOwner/businessId/{businessId}")
    public ResponseEntity getBusinessOwnerWithId(@PathVariable Long businessId) throws BusinessOwnerException {
        if (!businessOwnerService.doesBusinessOwnerWithIdExist(businessId)) {
            return status(NO_CONTENT).body(DefaultResponse.builder()
                    .field(businessId.toString())
                    .message("Given Business Owner does not exist. ")
                    .build());
        }
        return ok(businessOwnerService.getBusinessOwnerByBusinessId(businessId));
    }

    @Operation(summary = "Get Business Owner list with given firstName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BusinessOwners details retrieved",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "204", description = "BusinessOwners with given firstName does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for retrieving list of BusinessOwners. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @GetMapping(value = "v1/getBusinessOwners/firstName/{firstName}")
    public ResponseEntity getBusinessOwnersWithFirstName(@PathVariable String firstName) throws BusinessOwnerException {
        List<BusinessOwner> businessOwners = businessOwnerService.getBusinessOwnerListByFirstName(firstName);
        if (businessOwners.isEmpty()) {
            return status(NO_CONTENT).body(DefaultResponse.builder()
                    .field(firstName)
                    .message("No business owners exist with first name " + firstName)
                    .build());
        }
        return ok(businessOwners);
    }

    @Operation(summary = "Get Business Owner list with given lastName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BusinessOwners details retrieved",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "204", description = "BusinessOwners with given lastName does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for retrieving list of BusinessOwners. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @GetMapping(value = "v1/getBusinessOwners/lastName/{lastName}")
    public ResponseEntity getBusinessOwnersWithLastName(@PathVariable String lastName) throws BusinessOwnerException {
        List<BusinessOwner> businessOwners = businessOwnerService.getBusinessOwnerListByLastName(lastName);
        if (businessOwners.isEmpty()) {
            return status(NO_CONTENT).body(DefaultResponse.builder()
                    .field(lastName)
                    .message("No business owners exist with last name " + lastName)
                    .build());
        }
        return ok(businessOwners);
    }

    @Operation(summary = "Delete Existing Business Owner with given businessId DB")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "BusinessOwner deleted in database successfully",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "203", description = "BusinessOwner to be deleted does not exist",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE)}),
            @ApiResponse(responseCode = "400", description = "Bad Request for deleting BusinessOwner. ",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized request", content = @Content),
            @ApiResponse(responseCode = "403", description = "Request Forbidden", content = @Content),
            @ApiResponse(responseCode = "404", description = "Resource Not Found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Unexpected Internal Error", content = @Content)
    })
    @DeleteMapping(value = "v1/deleteBusinessOwner/businessId/{businessId}")
    public ResponseEntity deleteBusinessOwner(@PathVariable Long businessId) throws BusinessOwnerException {
        boolean isDeleted = businessOwnerService.deleteBusinessOwnerById(businessId);
        if (isDeleted) {
            return ok(DefaultResponse.builder()
                    .field(businessId.toString())
                    .message("Business Owner with above id successfully deleted from Db ")
                    .build());
        } else {
            return accepted().body(DefaultResponse.builder()
                    .field(businessId.toString())
                    .message("Business Owner with above Id does not exist ")
                    .build());
        }
    }
}
