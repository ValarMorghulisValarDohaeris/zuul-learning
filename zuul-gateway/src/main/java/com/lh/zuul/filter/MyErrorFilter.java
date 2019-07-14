package com.lh.zuul.filter;

import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2019/7/13.
 * 自定义异常处理类，在调用zuul使用的时候发生了异常(捕获的都是过滤器执行的异常，比如route()时服务不可用，可以在ZuulServlet类中看到，
 * 调用每个过滤器时都用try-catch包围，在catch中最终会调用error类型的过滤器进行处理，那么在目标服务中抛出的Exception不会被抓到)，会调用异常过滤器对异常进行处理
 * 若要该异常过滤器生效，需要在配置文件中将SendErrorFilter禁用掉
 * @author hao
 */
@Service
public class MyErrorFilter extends SendErrorFilter
{
    @Override
    public Object run() {
        String msg="请求失败！";
        try{
            RequestContext ctx = RequestContext.getCurrentContext();
            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            System.out.println("错误信息:"+exception.getErrorCause());
            msg+="error:"+exception.getErrorCause();
            HttpServletResponse response = ctx.getResponse();
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(msg.getBytes());
        }catch (Exception ex) {
            ex.printStackTrace();
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return msg;
    }
}
