package com.applet.common.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Liyanpeng
 * 这是个注释
 **/
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CheckAuthorization {
    String value();
}
