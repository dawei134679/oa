package com.hkkj.oa.common.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SystemLog {
	String module() default "";

	String remark() default "";
}