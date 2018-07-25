package com.todo.note.utility;

import com.todo.note.utility.exceptions.UserExceptionHandling;

public class Nullcheker
{



    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckNotNull(T resource) throws UserExceptionHandling {
        if (resource != null) {
            throw new UserExceptionHandling(("email.registered"));
        }
        return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckNull(T resource) throws UserExceptionHandling {
        if (resource == null) {
            throw new UserExceptionHandling(("invalid email id"));
        }
        return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckPass(T resource) throws UserExceptionHandling {
        if (resource == null) {
            throw new UserExceptionHandling(("invalid password"));
        }
        return resource;
    }




}
