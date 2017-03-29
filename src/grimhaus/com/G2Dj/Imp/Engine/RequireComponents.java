/*
 * G2Dj Game engine
 * Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Imp.Engine;

import grimhaus.com.G2Dj.Type.Engine.Component;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Joseph Cameron
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequireComponents
{
    public Class<? extends Component>[] value();
    
}

/*
//Cannot use repeatables; Java 8 features require an android API of a much too new standard (24)
public @interface RequireComponents
@Retention(RetentionPolicy.RUNTIME)
{
    RequireComponent[] value() default Component.class;;

}*/
