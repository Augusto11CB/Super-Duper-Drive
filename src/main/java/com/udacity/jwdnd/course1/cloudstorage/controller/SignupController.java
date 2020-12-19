package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.dto.SignupFormDTO;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
/*
 * - [X] An error message is already present in the template, but should only be visible if an error occurred during signup.
 * - [X] The application should not allow duplicate usernames or duplicate filenames attributed to a single user.
 */

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage(@ModelAttribute("signupForm") final SignupFormDTO signupForm, final Model model) {
        return "signup";
    }

    @PostMapping
    public ModelAndView signupNewUser(@ModelAttribute("signupForm") final SignupFormDTO signupForm, final ModelMap model) {

        Optional<String> invalidResult = this.  isSubmittedSignupDataValid(signupForm);

        if (invalidResult.isPresent()) {
            model.addAttribute("signupError", invalidResult.get());

        } else {

            Optional<User> user = userService.createUser(signupForm.getUserName(), signupForm.getPassword(), signupForm.getFirstName(), signupForm.getLastName());

            if (!user.isPresent()) {

                model.addAttribute("signupError", "There was an error signing you up. Please try again.");
                return new ModelAndView("signup", model);
            } else {
                model.addAttribute("signup", "true");
            }
        }

        return new ModelAndView("login", model);
    }

    private Optional<String> isSubmittedSignupDataValid(final SignupFormDTO signupForm) {

        Optional<String> result = Optional.empty();

        result = validateIfRequiredInfoIsPresent(signupForm);

        if (result.isPresent()) {
            return result;
        }

        result = validateIfThereIsDuplicatedUserName(signupForm);

        return result;
    }

    private Optional<String> validateIfThereIsDuplicatedUserName(final SignupFormDTO signupForm) {

        Optional<User> userByUserName = userService.findUserByUserName(signupForm.getUserName());

        if (userByUserName.isPresent()) {
            return Optional.of("The username already exists.");
        }

        return Optional.empty();
    }

    private Optional<String> validateIfRequiredInfoIsPresent(final SignupFormDTO signupForm) {

        Optional<String> s = Optional.of("There are fields with invalid information, please review your registration");

        if (!StringUtils.isNotBlank(signupForm.getUserName()) ||
                !StringUtils.isNotBlank(signupForm.getPassword()) ||
                !StringUtils.isNotBlank(signupForm.getFirstName()) ||
                !StringUtils.isNotBlank(signupForm.getLastName())
        ) {
            return s;
        }

        return Optional.empty();
    }
}
