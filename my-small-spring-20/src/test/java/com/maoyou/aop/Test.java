package com.maoyou.aop;

import com.maoyou.springframework.aop.Advisor;
import com.maoyou.springframework.aop.TargetSource;
import com.maoyou.springframework.aop.aspectj.AspectJExpressionPointcut;
import com.maoyou.springframework.aop.framework.AdvisorChainFactory;
import com.maoyou.springframework.aop.framework.DefaultAdvisorChainFactory;
import com.maoyou.springframework.aop.framework.ProxyFactory;
import com.maoyou.springframework.aop.framework.ReflectiveMethodInvocation;
import com.maoyou.springframework.aop.support.DefaultBeanFactoryPointcutAdvisor;
import com.maoyou.springframework.aop.target.SingletonTargetSource;
import com.maoyou.springframework.context.support.ClassPathXmlApplicationContext;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName Test
 * @Description
 * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/10/18 16:41
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void testJDKDynamicProxy() {
        Object targetSource = new UserServiceImpl();
        UserService proxyObject = (UserService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetSource.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                long start = System.currentTimeMillis();
                try {
                    return method.invoke(targetSource, args);
                } finally {
                    long end = System.currentTimeMillis();
                    System.out.println(targetSource.getClass().getName() + "." + method.getName() + "()耗时：" + (end - start) + "毫秒");
                }
            }
        });
        proxyObject.registerUser(new User("maoyou", "123"));
        System.out.println(proxyObject.getUsers());
    }

    @org.junit.Test
    public void testPointcutExpression() throws NoSuchMethodException {
        Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        String expression = "execution(* com.maoyou.aop1.*.*(..))";
        PointcutExpression pointcutExpression = pointcutParser.parsePointcutExpression(expression);
        UserService userService = new UserServiceImpl();
        System.out.println(pointcutExpression.couldMatchJoinPointsInType(userService.getClass()));
        System.out.println(pointcutExpression.matchesMethodExecution(userService.getClass().getDeclaredMethod("registerUser", User.class)).alwaysMatches());
    }

    @org.junit.Test
    public void testAspectJExpressionPointcut() throws NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.maoyou.aop.**.*(..))");
        UserService userService = new UserServiceImpl();
        System.out.println(pointcut.matches(userService.getClass()));
        System.out.println(pointcut.matches(userService.getClass().getDeclaredMethod("registerUser", User.class), userService.getClass()));
    }

    @org.junit.Test
    public void testAOP1() {
        Object targetSource = new UserServiceImpl();
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.maoyou.aop.**.*(..))");
        Object proxyObject = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetSource.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (pointcut.matches(method, targetSource.getClass())) {
                    long start = System.currentTimeMillis();
                    try {
                        return method.invoke(targetSource, args);
                    } finally {
                        long end = System.currentTimeMillis();
                        System.out.println(targetSource.getClass().getName() + "." + method.getName() + "()耗时：" + (end - start) + "毫秒");
                    }
                }
                return method.invoke(targetSource, args);
            }
        });
        ((UserService)proxyObject).registerUser(new User("maoyou", "123"));
        System.out.println(((UserService)proxyObject).getUsers());
    }

    @org.junit.Test
    public void testReflectiveMethodInvocation() {
        Object targetSource = new UserServiceImpl();
        MethodInterceptor methodInterceptor1 = mi -> {
            long start = System.currentTimeMillis();
            try {
                return mi.proceed();
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()耗时：" + (end - start) + "毫秒");
            }
        };
        MethodInterceptor methodInterceptor2 = mi -> {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()开始");
            try {
                return mi.proceed();
            } finally {
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()结束");
            }
        };
        List<MethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(methodInterceptor1);
        interceptors.add(methodInterceptor2);
        Object proxyObject = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetSource.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(targetSource, method, args, interceptors);
                Object object = reflectiveMethodInvocation.proceed();
                return object;
            }
        });
        ((UserService)proxyObject).registerUser(new User("maoyou", "123"));
        System.out.println(((UserService)proxyObject).getUsers());
    }

    @org.junit.Test
    public void testDefaultBeanFactoryPointcutAdvisor() {
        Object targetSource = new UserServiceImpl();

        MethodInterceptor methodInterceptor1 = mi -> {
            long start = System.currentTimeMillis();
            try {
                return mi.proceed();
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()耗时：" + (end - start) + "毫秒");
            }
        };
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
        pointcut1.setExpression("execution(* com.maoyou.aop.**.*(..))");
        DefaultBeanFactoryPointcutAdvisor advisor1 = new DefaultBeanFactoryPointcutAdvisor();
        advisor1.setAdvice(methodInterceptor1);
        advisor1.setPointcut(pointcut1);

        MethodInterceptor methodInterceptor2 = mi -> {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()开始");
            try {
                return mi.proceed();
            } finally {
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()结束");
            }
        };
        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("execution(* com.maoyou.ioc.**.*(..))");
        DefaultBeanFactoryPointcutAdvisor advisor2 = new DefaultBeanFactoryPointcutAdvisor();
        advisor2.setAdvice(methodInterceptor2);
        advisor2.setPointcut(pointcut2);

        List<Advisor> advisorList = new ArrayList<>();
        advisorList.add(advisor1);
        advisorList.add(advisor2);

        Object proxyObject = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetSource.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object target = targetSource;
                Class<?> targetClass = (target != null ? target.getClass() : null);
                Advisor[] advisors = advisorList.toArray(new Advisor[0]);
                AdvisorChainFactory advisorChainFactory = new DefaultAdvisorChainFactory();
                List<Object> chain = advisorChainFactory.getInterceptorsAndDynamicInterceptionAdvice(advisors, method, targetClass);
                ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(targetSource, method, args, chain);
                Object object = reflectiveMethodInvocation.proceed();
                return object;
            }
        });
        ((UserService)proxyObject).registerUser(new User("maoyou", "123"));
        System.out.println(((UserService)proxyObject).getUsers());
    }

    @org.junit.Test
    public void testProxyFactory() {
        Object targetSource = new UserServiceImpl();
        // 这部分可以配置在spring.xml中
        DefaultBeanFactoryPointcutAdvisor advisor1 = new DefaultBeanFactoryPointcutAdvisor();
        MethodInterceptor methodInterceptor1 = mi -> {
            long start = System.currentTimeMillis();
            try {
                return mi.proceed();
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()耗时：" + (end - start) + "毫秒");
            }
        };
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
        pointcut1.setExpression("execution(* com.maoyou.aop.**.*(..))");
        advisor1.setAdvice(methodInterceptor1);
        advisor1.setPointcut(pointcut1);

        DefaultBeanFactoryPointcutAdvisor advisor2 = new DefaultBeanFactoryPointcutAdvisor();
        MethodInterceptor methodInterceptor2 = mi -> {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()开始");
            try {
                return mi.proceed();
            } finally {
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()结束");
            }
        };
        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("execution(* com.maoyou.ioc.**.*(..))");
        advisor2.setAdvice(methodInterceptor2);
        advisor2.setPointcut(pointcut2);

        // 这部分是后置处理器中的代理逻辑
        List<Advisor> advisorList = new ArrayList<>();
        advisorList.add(advisor1);
        advisorList.add(advisor2);
        ProxyFactory advised = new ProxyFactory();
        advised.setTargetSource(new SingletonTargetSource(targetSource));
        advised.addAdvisors(advisorList);
        Object proxyObject = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetSource.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                ProxyFactory advised0 = advised;
                TargetSource targetSource = advised.getTargetSource();
                Object target = targetSource.getTarget();
                Class<?> targetClass = (target != null ? target.getClass() : null);

                List<Object> chain = advised0.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);
                ReflectiveMethodInvocation reflectiveMethodInvocation = new ReflectiveMethodInvocation(targetSource, method, args, chain);
                Object object = reflectiveMethodInvocation.proceed();
                return object;
            }
        });

        ((UserService)proxyObject).registerUser(new User("maoyou", "123"));
        System.out.println(((UserService)proxyObject).getUsers());
    }

    @org.junit.Test
    public void testAopProxy() {
        Object targetSource = new UserServiceImpl();
        // 这部分可以配置在spring.xml中
        DefaultBeanFactoryPointcutAdvisor advisor1 = new DefaultBeanFactoryPointcutAdvisor();
        MethodInterceptor methodInterceptor1 = mi -> {
            long start = System.currentTimeMillis();
            try {
                return mi.proceed();
            } finally {
                long end = System.currentTimeMillis();
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()耗时：" + (end - start) + "毫秒");
            }
        };
        AspectJExpressionPointcut pointcut1 = new AspectJExpressionPointcut();
        pointcut1.setExpression("execution(* com.maoyou.aop.**.*(..))");
        advisor1.setAdvice(methodInterceptor1);
        advisor1.setPointcut(pointcut1);

        DefaultBeanFactoryPointcutAdvisor advisor2 = new DefaultBeanFactoryPointcutAdvisor();
        MethodInterceptor methodInterceptor2 = mi -> {
            System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()开始");
            try {
                return mi.proceed();
            } finally {
                System.out.println(mi.getThis().getClass().getName() + "." + mi.getMethod().getName() + "()结束");
            }
        };
        AspectJExpressionPointcut pointcut2 = new AspectJExpressionPointcut();
        pointcut2.setExpression("execution(* com.maoyou.aop.**.*(..))");
        advisor2.setAdvice(methodInterceptor2);
        advisor2.setPointcut(pointcut2);

        // 这部分是后置处理器中的代理逻辑
        List<Advisor> advisorList = new ArrayList<>();
        advisorList.add(advisor1);
        advisorList.add(advisor2);
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setTargetSource(new SingletonTargetSource(targetSource));
        proxyFactory.addAdvisors(advisorList);
        Object proxyObject = proxyFactory.getProxy();

        ((UserService)proxyObject).registerUser(new User("maoyou", "123"));
        System.out.println(((UserService)proxyObject).getUsers());
    }

    @org.junit.Test
    public void testAspectJAwareAdvisorAutoProxyCreator() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-aop.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.registerUser(new User("maoyou", "123"));
        System.out.println(userService.getUsers());
    }

    @org.junit.Test
    public void testAdvisorAdapter() {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "target/cglib");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-aop2.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.registerUser(new User("maoyou", "123"));
        System.out.println(userService.getUsers());
    }

    @org.junit.Test
    public void testCglib() {
        Object target = new UserServiceImpl();
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new net.sf.cglib.proxy.MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("start");
                Object ret = methodProxy.invoke(target, objects);
//                Object ret = methodProxy.invokeSuper(o, objects);
//                Object ret = method.invoke(target, objects);
                System.out.println("end");
                return ret;
            }
        });
        Object proxy = enhancer.create();
        ((UserServiceImpl)proxy).registerUser(new User("maoyou", "123"));
    }
}
