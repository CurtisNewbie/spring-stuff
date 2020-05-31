package com.example.uploadingfiles;

public class StorageFileNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -895116639872825267L;

    StorageFileNotFoundException(String fname) {
        super(String.format("File: '%s' not found.", fname));
    }

}