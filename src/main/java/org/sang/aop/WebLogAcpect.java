package org.sang.aop;

/**
 * @ClassName WebLogAcpect
 * @Description TODO
 * @Author haocanwen
 * @Date 2020/4/8 11:32
 * @Version 1.0
 */

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;


@Aspect
@Component
@Order(1)//这个注解的作用是:标记切面类的处理优先级,i值越小,优先级别越高.PS:可以注解类,也能注解到方法上
@Slf4j
public class WebLogAcpect {

    private Gson gson = new Gson();

    private Logger logger = LoggerFactory.getLogger(WebLogAcpect.class);

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    @Pointcut("execution(public * org.sang.controller..*.*(..))")
    public void webLog(){}

    /**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")//这个注解的作用是:在切点前执行方法,内容为指定的切点
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //打印请求内容
        logger.info("========================请求内容======================");
        logger.info("请求地址:" + request.getRequestURL().toString());
        logger.info("请求方式" + request.getMethod());
        logger.info("客户端IP地址 : " + request.getRemoteAddr());
        logger.info("请求类方法" + joinPoint.getSignature());
        logger.info("请求类方法参数" + Arrays.toString(joinPoint.getArgs()));
        logger.info("========================请求内容======================");

    }

    //在方法执行完结后打印返回内容
    @AfterReturning(returning = "ret",pointcut = "webLog()")
    //这个注解的作用是:在切入点,return后执行,如果想对某些方法的返回参数进行处理,可以在这操作
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        logger.info("--------------返回内容----------------");
        logger.info("Response内容:" + gson.toJson(ret));
        logger.info("--------------返回内容----------------");

    }
}
