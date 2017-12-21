/*******************************************************************************
 * Copyright (c) 2016 Julien Louette & Gaël Wittorski
 * 
 * This file is part of Raspoid.
 * 
 * Raspoid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Raspoid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Raspoid.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package com.muzammilpeer.quadcopter.diozero;

/**
 * Exception thrown by raspoid when a thread is interrupted unexpectedly
 * 
 * @author Julien Louette &amp; Ga&euml;l Wittorski
 * @version 1.0
 */
public class RaspoidInterruptedException extends RaspoidException {

    /**
     * Constructs a new interrupted exception with the specified detail message and cause.
     * @param message the detail message
     * @param cause the cause to be retrieved later
     */
    public RaspoidInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new interrupted exception with the specified detail message.
     * @param message the detail message
     */
    public RaspoidInterruptedException(String message) {
        super(message);
    }

    /**
     * Constructs a new interrupted exception with the specified cause.
     * @param cause the cause to be retrieved later
     */
    public RaspoidInterruptedException(Throwable cause) {
        super(cause);
    }

    
    
}
