package com.aliagushutapea.convertion.dependencyinjection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by ali on 26/01/18.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerDatabaseHelper {
}
