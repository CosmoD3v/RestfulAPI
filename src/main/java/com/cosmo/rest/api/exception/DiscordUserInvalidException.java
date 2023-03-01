package com.cosmo.rest.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Invalid discord user body.")
public class DiscordUserInvalidException extends RuntimeException {
}