package metty1337.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import metty1337.dto.SignUpFormDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpFormDto> {
    @Override
    public boolean isValid(SignUpFormDto signUpFormDto, ConstraintValidatorContext constraintValidatorContext) {
        if (signUpFormDto == null) {
            return true;
        }

        String password = signUpFormDto.getPassword();
        String repeatPassword = signUpFormDto.getRepeatPassword();

        if (password == null || repeatPassword == null) {
            return true;
        }

        boolean matches = password.equals(repeatPassword);
        if (!matches) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("Passwords do not match")
                                      .addConstraintViolation();
        }

        return matches;
    }
}