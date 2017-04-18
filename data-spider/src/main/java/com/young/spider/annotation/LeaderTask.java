package com.young.spider.annotation;

import java.lang.annotation.*;

/**
 * Created by yangyong3 on 2017/4/18.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface LeaderTask {
}
