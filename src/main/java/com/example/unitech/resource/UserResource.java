package com.example.unitech.resource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldNameConstants
public class UserResource  {

    @NotBlank
    private String email;


    @Length(min = 6, max = 255)
    @ToString.Exclude // ignore from toString as it is sensitive information
    private String password;
}
