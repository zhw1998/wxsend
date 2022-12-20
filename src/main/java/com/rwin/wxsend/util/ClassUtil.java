package com.rwin.wxsend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 对工具包{@link cn.hutool.core.util}方法的补充
 *
 * @author chenli
 * @version 1.0
 * @date 2016-2-25
 */
public class ClassUtil extends cn.hutool.core.util.ClassUtil {
    private static final ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
    private static final String RESOURCE_PATTERN = "/**/*.class";
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    private static final String BASE_PACKAGE = "com.rwin.wxsend";
    //使用软引用缓存数据提高性能
    private static SoftReference<Map<String, Object>> sr;
    private static Lock lock = new ReentrantLock();

    /**
     * 获取类的所有枚举子类。 不能获取jar中的，只能获取classes下面的
     *
     * @param clazz 查询类（接口）名
     * @return
     * @author chenli
     * @data 2016-2-25
     */
    public static <T> List<T> getChilderEnum(Class<T> clazz) {
        String key = "enum_" + clazz.getName();
        List<T> result = new ArrayList<T>();
        Object o = getCache(key);
        if (o != null) {
            result = (List<T>) o;
        } else {
            List<Class<T>> list = ClassUtil.getChilderClass(clazz, false);
            for (Class<T> c : list) {
                if (c.isEnum()) {
                    try {
                        Method method = c.getMethod("values");
                        T[] constats = (T[]) method.invoke(null);
                        for (T iConstant : constats) {
                            result.add(iConstant);
                        }

                    } catch (Exception e) {
                        logger.error("获取常量枚举异常", e);
                    }
                }
            }
            putCache(key, result);
        }
        return result;
    }

    /**
     * 获取类的所有子类(参数 containInterface 决定是否包括接口)。
     * 不能获取jar中的，只能获取classes下面的
     * <br>等于{@link ClassUtil#getChilderClass(Class, boolean, String...)}中String wei
     *
     * @param c                查询类（接口）名
     * @param containInterface 是否包括接口(和抽象类)
     * @return
     * @author chenli
     * @data 2016-2-25
     */
    public static <T> List<Class<T>> getChilderClass(Class<T> c, boolean containInterface) {
        return getChilderClass(c, containInterface, BASE_PACKAGE);
    }

    /**
     * 获取类的所有子类(包括接口)。 不能获取jar中的，只能获取classes下面的
     *  * @param c 				查询类（接口）名
     *
     * @param containInterface 是否包括接口
     *                          * @param packageName		查询范围（在哪个包下查询）
     * @return
     * @author chenli
     * @data 2016-2-25
     */
    public static <T> List<Class<T>> getChilderClass(Class<T> c, boolean containInterface, String... packageName) {
        List<Class<T>> returnClassList = new ArrayList<Class<T>>(); //返回结果
        try {
            Set<Class<?>> allClass = getClassSet(packageName); //获得某个包以及子包下的所有类
            for (Class<?> classes : allClass) {
                if (c.isAssignableFrom(classes)) {//判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同。
                    if (!c.equals(classes)) { //本身不加进去
                        if (containInterface || (containInterface == false && classes.isInterface() == false && false == Modifier.isAbstract(classes.getModifiers()))) {
                            returnClassList.add((Class<T>) classes);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取【{}】的子类发生异常", c.getName(), e);
        }
        return returnClassList;
    }


    /**
     * 将符合条件的Bean以Class集合的形式返回
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class<?>> getClassSet(String... packagesList) throws IOException {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (String pkg : packagesList) {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    org.springframework.util.ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    try {
                        classSet.add(Class.forName(className));
                    } catch (NoClassDefFoundError e) {
                        //IGNORE 忽略jar中
                    } catch (ClassNotFoundException ex) {
                        //IGNORE 忽略jar中
                    } catch (Error ex) {
                        //IGNORE 忽略jar中
                    }
                }
            }
        }
        return classSet;
    }

    /**
     * 将符合条件的Bean以Class集合的形式返回
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Set<Class<?>> getClassSet(String pkg) throws IOException {
        String key = "pkg_" + pkg;
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        Object o = getCache(key);
        if (o != null) {
            classSet = (Set<Class<?>>) o;
        } else {
            String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    org.springframework.util.ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
            Resource[] resources = resourcePatternResolver.getResources(pattern);
            MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader reader = readerFactory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    try {
                        classSet.add(Class.forName(className));
                    } catch (NoClassDefFoundError e) {
                        //IGNORE 忽略jar中
                    } catch (ClassNotFoundException ex) {
                        //IGNORE 忽略jar中
                    }
                }
            }
            putCache(key, classSet);
        }
        return classSet;
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     *
     * @param clazz 类
     * @return 返回该类或者父类的第一个参数类型
     * @author chenli
     * @data 2016-2-28
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     *
     * @param clazz 类
     * @param index 返回的泛型位置
     * @return
     * @author chenli
     * @data 2016-2-28
     */
    public static Class getSuperClassGenricType(Class clazz, int index) {
        clazz.getGenericInterfaces();
        Type genType = clazz.getGenericSuperclass();

        //改造一下 最多向上获取三级父类的泛型 如果三级还没有则返回错误
        int i = 0;
        while (!(genType instanceof ParameterizedType) && i < 3) {
            clazz = clazz.getSuperclass();
            genType = clazz.getGenericSuperclass();
        }
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 通过反射,获得定义Class时声明的接口的范型参数的类型. 如public BookManager implements IGenricManager<Book>
     *
     * @param clazz          类
     * @param interfaceClass 集成的接口类型。
     * @return
     * @author chenli
     * @data 2016-2-28
     */
    public static Class getSuperInterfaceGenricType(Class clazz, Class interfaceClass) {
        Type[] genTypes = clazz.getGenericInterfaces();

        for (Type type : genTypes) {
            if (!(type instanceof ParameterizedType)) {
                continue;
            }
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType.equals(interfaceClass)) {
                Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                if (params.length == 1) {
                    return (Class) params[0];
                }

            }
        }
        return Object.class;
    }

    /**
     * 通过反射,获得定义Class时声明的接口的指定位置的范型参数的类型. 如public BookManager implements IGenricManager<Book, User>
     *
     * @param clazz          类
     * @param interfaceClass 集成的接口类型。
     * @param index          返回的泛型位置
     * @return
     * @author chenli
     * @data 2016-2-28
     */
    public static Class getSuperInterfaceGenricType(Class clazz, Class interfaceClass, int index) {
        Type[] genTypes = clazz.getGenericInterfaces();

        for (Type type : genTypes) {
            if (!(type instanceof ParameterizedType)) {
                continue;
            }
            Type rawType = ((ParameterizedType) type).getRawType();
            if (rawType.equals(interfaceClass)) {
                Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                if (index >= params.length || index < 0) {
                    logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                            + params.length);
                    return Object.class;
                }
                if (!(params[index] instanceof Class)) {
                    logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
                    return Object.class;
                }
                return (Class) params[index];
            }
        }
        return Object.class;
    }

    /**
     * 放入缓存
     *
     * @param key 存储key
     * @param o   存储对象
     * @author chenli
     * @data 2016-3-29
     */
    private static void putCache(String key, Object o) {
        if (sr == null) {
            try {
                lock.lock();
                if (sr == null) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    sr = new SoftReference<Map<String, Object>>(map);
                }
            } catch (Exception e) {
                //IGNORE 忽略锁异常
            } finally {
                lock.unlock();
            }
        }
        Map<String, Object> map = sr.get();
        if (map == null) {
            try {
                lock.lock();
                if (map == null) {
                    map = new HashMap<String, Object>();
                    sr = new SoftReference<Map<String, Object>>(map);
                }
            } catch (Exception e) {
                //IGNORE 忽略锁异常
            } finally {
                lock.unlock();
            }
        }
        map.put(key, o);
    }

    /**
     * 从缓存中换取
     *
     * @param key 存储key
     * @author chenli
     * @data 2016-3-29
     */
    private static Object getCache(String key) {
        if (sr == null) {
            return null;
        }
        Map<String, Object> map = sr.get();
        if (map == null) {
            return null;
        }
        return map.get(key);
    }

    /**
     * 实例化一个class
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstant(Class<T> clazz) {
        if (clazz == null) {
            return null;
        }

        T result = null;
        try {
            result = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error("class={}使用无参构造实例化对象失败", e);
        } catch (IllegalAccessException e) {
            logger.error("class={}使用无参构造实例化对象失败", e);
        }
        return result;
    }

}
