package Utils;


import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

/**
 * BeanUtils.populate用来将一些key-value的值映射到bean中的属性。
 * BeanUtils.populate( Object bean, Map properties )，
 * 这个方法会遍历map<key, value>中的key，如果bean中有这个属性，就把这个key对应的value值赋给bean的属性。
 * 这个方法的作用是将前端的获取的Parameter和Value封装成bean。
 */
public class WebUtils {
    public static <T> T fillBean(HttpServletRequest request, Class<T> clazz){
        try {
            T bean = clazz.newInstance();

            BeanUtils.populate(bean, request.getParameterMap());

            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
