package com.ncs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.ncs.entity.SuperUser;
import com.ncs.entity.User;
import com.ncs.service.UserService;

@RestController
@RequestMapping("/global")
@Validated
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private MessageSource msgSource;

	@GetMapping
	public String service() {
		return msgSource.getMessage("welcome.message", null, "User Service Loaded", LocaleContextHolder.getLocale());
		// return "User Service Loaded !!! ";
	}

	@GetMapping(value = "/users", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, headers = "api-version=1")
	public List<User> getUsersV1() {
		System.out.println("Users >> " + userService.getUsers());

		return userService.getUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, headers = "api-version=2")
	public List<SuperUser> getUsersV2() {
		System.out.println("Users >> " + userService.getUsers());

		return userService.getUsersWithRole();
	}

	@GetMapping(value = "/users", produces = "application/vnd.company.app-v1+json")
	public List<User> getUsersContentV1() {
		System.out.println("Users >> " + userService.getUsers());

		return userService.getUsers();
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = "application/vnd.company.app-v2+json")
	public List<SuperUser> getUsersContentV2() {
		System.out.println("Users >> " + userService.getUsers());

		return userService.getUsersWithRole();
	}

	@GetMapping(value = "user/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public User getUser(@PathVariable("id") @Positive Long id) {

		return userService.getUser(id);
	}

	@PostMapping(value = "/adduser")
	public String addUser(@Valid @RequestBody User user) {

		String userId = userService.addUser(user);
		return "User Created with Id " + userId;
	}

	@DeleteMapping(value = "/remove-user/{id}")
	public String removeUser(@PathVariable Long id) {
		return userService.remove(id);

	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
