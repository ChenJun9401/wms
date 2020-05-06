package cn.wolfcode.wms.util;

import cn.wolfcode.wms.domain.Employee;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

//用户的上下文工具类
public abstract class UserContext {

    public static final String USER_KEY = "EMPLOYEE_IN_SESSION";
    public static final String EXPS_KEY = "EXPS_IN_SESSION";

    private static HttpSession getSession() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attrs.getRequest().getSession();
    }

    public static void setCurrentUser(Employee emp) {
        getSession().setAttribute(USER_KEY, emp);
    }

    public static Employee getCurrentUser() {
        return (Employee) getSession().getAttribute(USER_KEY);
    }

    public static void setExpressions(List<String> exps) {
        getSession().setAttribute(EXPS_KEY, exps);
    }

    public static List<String> getExpressions() {
        return (List<String>) getSession().getAttribute(EXPS_KEY);
    }
}
