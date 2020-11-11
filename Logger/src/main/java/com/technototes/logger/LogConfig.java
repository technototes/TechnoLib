package com.technototes.logger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;

@Repeatable(LogConfig.LogConfigs.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogConfig {


    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface LogConfigs {
        LogConfig[] value();
    }


    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={FIELD, LOCAL_VARIABLE, METHOD})
    @interface Run{
        boolean duringRun() default true;
        boolean duringInit() default false;

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={FIELD, LOCAL_VARIABLE, METHOD})
    @interface Whitelist{
        Class<?>[] value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={FIELD, LOCAL_VARIABLE, METHOD})
    @interface Blacklist{
        Class<?>[] value();
    }
    @Retention(RetentionPolicy.RUNTIME)
    @Target(value={FIELD, LOCAL_VARIABLE, METHOD})
    @interface Disabled{

    }

}
