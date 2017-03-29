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
public @interface RequireComponent
{
    public Class<? extends Component> value();
    
}

/*
//Cannot support Java 8 feature. Android API requirement is much too high (24)
import java.lang.annotation.Repeatable; 
@Repeatable(RequireComponents.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireComponent
{
    public Class<? extends Component> value() default Component.class;
    
}
*/