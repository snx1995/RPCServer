package top.banyaoqiang.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by 班耀强 on 2018/8/17
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SqlConstraint {
    boolean AUTO_INCREMENT() default false;
    boolean NOT_NULL() default false;
    boolean UNIQUE() default false;
    boolean PRIMARY_KEY() default false;
    boolean FOREIGN_KEY() default false;
    boolean CHECK() default false;
    boolean DEFAULT() default false;
}
