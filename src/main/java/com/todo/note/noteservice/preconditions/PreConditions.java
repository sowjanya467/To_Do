package com.todo.note.noteservice.preconditions;

import org.springframework.lang.Nullable;

import com.todo.note.utility.exceptions.ToDoException;

public class PreConditions {

	 public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) throws ToDoException {
	        if (reference == null) {
	            throw new ToDoException(String.valueOf(errorMessage));
	        }
	        return reference;
	    }

	    public static boolean isPresentInDb(boolean reference,@Nullable Object errorMessage) throws ToDoException {
	        if (!reference) {
	            throw new ToDoException(String.valueOf(errorMessage));
	        }
	        return reference;
	    }

	    
	    public static <T> String checkNotNull(String reference, @Nullable Object errorMessage) throws ToDoException {
	        if (reference.equals("")) {
	            throw new ToDoException(String.valueOf(errorMessage));
	        }
	        return reference;
	    }
	}

