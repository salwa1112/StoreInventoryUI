package com.salwa.assignment2.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@Logged //log if the methods will call
public class LoggedInterceptor implements Serializable {

    @AroundInvoke
    public Object logEntryToTheMethod(InvocationContext ctx) throws Exception {
        System.out.println("Method " + ctx.getMethod() + " was called");
        return ctx.proceed();
    }
}
