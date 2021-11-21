/*
 * Copyright (c) 2020-2021 Polyhedral Development
 *
 * The Terra API is licensed under the terms of the MIT License. For more details,
 * reference the LICENSE file in the common/api directory.
 */

package com.dfsek.terra.api.command.exception;

import java.io.Serial;


public class SwitchFormatException extends CommandException {
    @Serial
    private static final long serialVersionUID = -965858989317844628L;
    
    public SwitchFormatException(String message) {
        super(message);
    }
    
    public SwitchFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
