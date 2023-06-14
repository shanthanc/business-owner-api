package com.shanthan.businessowner.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultResponse {

    private String field;
    private String message;
}
