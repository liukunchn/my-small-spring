package com.maoyou.springframework.beans.factory.support;

import com.maoyou.springframework.beans.factory.DisposableBean;
import com.maoyou.springframework.beans.factory.ObjectFactory;
import com.maoyou.springframework.beans.factory.config.SingletonBeanRegistry;
import com.maoyou.springframework.util.Assert;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName DefaultSingletonBeanRegistry
 * @Description a和b为循环依赖的时候，执行过程分析：
 *      *      创建a实例化前调用getSingleton(a)三级缓存都没有，返回null，
 *  *      *          创建a实例化后属性填充之前调用addSingletonFactory()，将()->getEarlyBeanReference()放入三级缓存
 *  *      *      创建b实例化前调用getSingleton(b)三级缓存都没有，返回null，
 *  *      *          创建b实例化后属性填充之前调用addSingletonFactory()，将()->getEarlyBeanReference()放入三级缓存
 *  *      *      创建b填充属性a调用getSingleton(a)三级缓存有，调用工厂方法返回半成品，且放入二级缓存中，
 *  *      *      创建b对象执行初始化方法(代理后置处理)之后调用getSingleton(b),三级缓存有，调用工厂方法返回单例，且放入二级缓存
 *  *      *          创建b对象执行getSingleton(a)之后，调用registerSingleton(b)，放入一级缓存
 *  *      *      创建a填充属性b调用getSingleton(b)一级缓存有，返回单例
 *  *      *      创建a对象执行初始化方法(代理后置处理)之后调用getSingleton(a),二级缓存有，返回单例
 *  *      *          创建a对象执行getSingleton(a)之后，调用registerSingleton(a)，放入一级缓存
 *  * @Author 刘坤 kunliu@yinhai.com
 * @Date 2021/9/22 17:34
 * @Version 1.0
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 一级缓存，存储单例对象
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /**
     * 二级缓存，存储半成品对象
     */
    private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);

    /**
     * 三级缓存，存储普通单例对象和代理单例对象的对象工厂
     */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    /**
     * 注册单例对象
     * 什么时候调用？
     *      doCreateBean() 结束的时候调用【注意：会先调用getSingleton()】
     * @param beanName
     * @param singletonObject
     * @throws IllegalStateException
     */
    @Override
    public void registerSingleton(String beanName, Object singletonObject) throws IllegalStateException {
        Assert.notNull(beanName, "Bean name must not be null");
        Assert.notNull(singletonObject, "Singleton object must not be null");
        synchronized (this.singletonObjects) {
            Object oldObject = this.singletonObjects.get(beanName);
            if (oldObject != null) {
                throw new IllegalStateException("Could not register object [" + singletonObject +
                        "] under bean name '" + beanName + "': there is already object [" + oldObject + "] bound");
            }
            this.singletonObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
            this.earlySingletonObjects.remove(beanName);
        }
    }

    /**
     * 获取单例对象
     * 什么时候调用？
     *      1. doGetBean(）开头的时候会调用
     *      2. doCreateBean() 结束的时候会调用
     * @param beanName
     * @return
     */
    @Override
    public Object getSingleton(String beanName) {
        // 减小加锁的范围，依次从一级缓存，二级缓存获取
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null) {
            singletonObject = this.earlySingletonObjects.get(beanName);
            if (singletonObject == null) {
                // 加锁，依次从一级缓存，二级缓存，三级缓存获取
                synchronized (this.singletonObjects) {
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        singletonObject = this.earlySingletonObjects.get(beanName);
                        if (singletonObject == null) {
                            ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                            if (singletonFactory != null) {
                                // 如果从三级缓存获取，将它放入二级缓存
                                singletonObject = singletonFactory.getObject();
                                this.earlySingletonObjects.put(beanName, singletonObject);
                                this.singletonFactories.remove(beanName);
                            }
                        }
                    }
                }
            }
        }
        return singletonObject;
    }

    /**
     * 提前注册半成品，这里注册的是单例工厂，它的方法可能返回普通单例对象，也可能返回代理单例对象
     * 什么时候调用？
     *      createBeanInstance()之后调用。
     * @param beanName
     * @param singletonFactory
     */
    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        Assert.notNull(singletonFactory, "Singleton factory must not be null");
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
            }
        }
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        this.disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        String[] disposableBeanNames = this.disposableBeans.keySet().toArray(new String[0]);
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            this.singletonObjects.remove(disposableBeanNames[i]);
            DisposableBean disposableBean = (DisposableBean) this.disposableBeans.remove(disposableBeanNames[i]);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                System.out.println("Destruction of bean with name '" + disposableBeanNames[i] + "' threw an exception");
                e.printStackTrace();
            }
        }
    }

    public boolean containsSingleton(String beanName) {
        return this.singletonObjects.containsKey(beanName);
    }
}
