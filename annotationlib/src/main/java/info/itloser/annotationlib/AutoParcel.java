package info.itloser.annotationlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author：zhaoliangwang on 2019/9/9 15:23
 * email：tc7326@126.com
 */
@Target(ElementType.TYPE)//代表类级别以上才能使用此注解。
@Retention(RetentionPolicy.SOURCE)//代表该注解只存在源代码中，编译后的字节码中不存在。
public @interface AutoParcel {

}
