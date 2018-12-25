package com.ztw33.javafinal.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * 编程中曾经犯过的错误、和值得注意的地方
 * 
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Notice {
	String message(); // 消息
	int lineBegin(); // 相关代码起始行数
	int lineEnd(); // 结束行数
}
