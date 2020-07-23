package com.home.quizws.user.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @ExtendedEmailValidator
    private String email;

    @Length(min = 5, message = "Password should contain at least 5 characters")
    private String password;

}
