package com.dropbox.dropboxservice.utils;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface LogExecutionTime {
}
