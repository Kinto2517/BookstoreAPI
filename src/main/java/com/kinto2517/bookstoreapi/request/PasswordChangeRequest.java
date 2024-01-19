package com.kinto2517.bookstoreapi.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirmation;

}
