package cn.wolfcode.wms.util;

import java.lang.reflect.Method;

public abstract class PermissionUtil {
    //当前方法的所在类的全限定名:当前方法的名称
    public static String buildExpression(Method m) {
        Class<?> clz = m.getDeclaringClass();
        return clz.getName() + ":" + m.getName();
    }
}
