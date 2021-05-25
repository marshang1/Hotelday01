package cn.kgc.interceptor;

import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SysInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("拦截器的核心方法.......");
        //从session容器中取出登陆的用户对象
        Object loginUser=request.getSession().getAttribute("loginUser");
        //如果该用户再session中存在
        if (loginUser!=null){
            return true;//直接放行
        }else {
            //装入拦截提示
            request.setAttribute("loginUIMsg","loginUIMsg");
            //直接转发到登陆页面
            request.getRequestDispatcher("/model/loginUI").forward(request,response);
            return false;
        }
    }
}
