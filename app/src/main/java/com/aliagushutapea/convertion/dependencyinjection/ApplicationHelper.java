package com.aliagushutapea.convertion.dependencyinjection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by ali on 26/01/18.
 */

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicationHelper {
}
