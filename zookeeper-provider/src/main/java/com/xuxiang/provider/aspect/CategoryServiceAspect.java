package com.xuxiang.provider.aspect;

import com.xuxiang.service.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author: Xux
 * @package: com.xuxiang.provider.aspect
 * @create: 2021/6/18
 * @FileName: CategoryServiceAspect
 * @Description:
 */
@Component
@Aspect  //声明这是一个切面类
public class CategoryServiceAspect {

    @Reference
    private CategoryService CategoryService;

/*    //设置切入点
    @Pointcut("execution(* com.xuxiang.provider.service.*.insertCategory(..))")
    public void pointcut(){
    }   */

    //设置切入点
    @Pointcut("execution(* com.xuxiang.provider.service.*.*(..))") //返回值任意,类任意,方法任意,参数任意
    public void pointcut() {
    }

    @Before("pointcut()")  //在切入点方法执行前执行
    public void before(JoinPoint joinPoint) {
        System.out.println("-------------执行before方法-------------" + joinPoint);
    }

    @After("pointcut()")  //在切入点方法执行后执行,不管是否抛出异常都执行
    public void After(JoinPoint joinPoint) {
        System.out.println("-------------执行After方法-------------" + joinPoint);
    }

    @AfterReturning("pointcut()")  //在切入点方法return后执行,不管是否抛出异常都执行
    public void AfterReturning(JoinPoint joinPoint) {
        System.out.println("-------------执行AfterReturning方法-------------" + joinPoint);
    }

    //(AfterReturning和AfterThrowing只会执行其中一个)
    @AfterThrowing("pointcut()")  //在切入点方法抛出异常后执行
    public void AfterThrowing(JoinPoint joinPoint) {
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        System.out.println("simpleName = " + simpleName);
        System.out.println("-------------执行AfterThrowing方法-------------" + joinPoint);
    }

    /*@Around("pointcut()")  //环绕通知需要手动执行目标方法,并返回值
    public Object Around(ProceedingJoinPoint ProceedingJoinPoint) throws Throwable {
        String simpleName = ProceedingJoinPoint.getTarget().getClass().getSimpleName();
        System.out.println("simpleName = " + simpleName);
        System.out.println("-------------执行Around方法前-------------" + ProceedingJoinPoint);
        Object proceed = ProceedingJoinPoint.proceed();
        System.out.println("proceed = " + proceed);
        System.out.println("-------------执行Around方法后-------------" + ProceedingJoinPoint);
        return proceed;
    }*/
}
