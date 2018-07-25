package com.todo.note.userservice.preconditions;

import com.todo.note.utility.exceptions.ToDoException;

public class PreConditions {

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckNotNull(T resource) throws ToDoException {
        if (resource != null) {
            throw new ToDoException(("emailis already registered"));
        }
        return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckNull(T resource) throws ToDoException {
        if (resource == null) {
            throw new ToDoException(("invalid email id"));
        }
        return resource;
    }

    /**
     * @param resource
     * @return
     * @throws UserExceptionHandler
     */
    public static <T> T CheckPassword(T resource) throws ToDoException {
        if (resource == null) {
            throw new ToDoException(("invalid password"));
        }
        return resource;
    }
}
