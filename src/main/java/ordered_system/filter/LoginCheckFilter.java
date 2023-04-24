package ordered_system.filter;

import com.alibaba.fastjson.JSON;
import ordered_system.common.BaseContext;
import ordered_system.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/*
检查用户是否完成登录
 */

//重定向在前端做了 这就不用再做了
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器  支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //获取uri
        String requestURI=request.getRequestURI();

        log.info("拦截到请求：{}",requestURI);
        String[] urls=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"//静态资源想看就看，拦住看数据的就欧克
        };//放不需要处理的请求

        boolean check=check(urls,requestURI);
        if(check){
            log.info("不需要处理的请求{}：",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        if(request.getSession().getAttribute("employee")!=null){
            log.info("用户已登陆，用户id：{}",request.getSession().getAttribute("employee"));

            Long empId=(Long)request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            /*long id = Thread.currentThread().getId();
            log.info("线程id为"+id);*/
            filterChain.doFilter(request,response);
            return;
        }

        //移动端判断
        if(request.getSession().getAttribute("user")!=null){
            log.info("用户已登陆，用户id：{}",request.getSession().getAttribute("user"));

            Long userId=(Long)request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);

            /*long id = Thread.currentThread().getId();
            log.info("线程id为"+id);*/
            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //通过输出流方式向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

        //log.info("拦截到请求：{}",request.getRequestURI());
        //filterChain.doFilter(request,response);
    }
    //检查本次请求是否需要放行
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
