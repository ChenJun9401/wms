package cn.wolfcode.wms.web.interceptor;

import cn.wolfcode.wms.util.UserContext;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//检查登录拦截器
public class CheckLoginInterceptor extends HandlerInterceptorAdapter {
    //快捷键ctrl+o覆盖类中的方法

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当用户有没有登录,有登录就放行
        Object emp = UserContext.getCurrentUser();
        if (emp == null) {    //没有登录
            response.sendRedirect("/login.html");
            return false;
        }
        //已经登录
        return true;
    }
}
