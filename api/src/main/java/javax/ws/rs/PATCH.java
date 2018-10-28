package javax.ws.rs;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// HACK: JAX-RS 2.0 does not support PATCH, 2.1 is supposed to though;
// HACK: dropwizard-jersey has a compatible annotation io.dropwizard.jersey.PATCH, but we do not want to pull all that in here

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@HttpMethod("PATCH")
@Documented
public @interface PATCH
{
  // empty
}