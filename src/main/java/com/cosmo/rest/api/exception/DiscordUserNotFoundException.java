package com.cosmo.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User doesn't exist.")
public class DiscordUserNotFoundException extends RuntimeException {
}
