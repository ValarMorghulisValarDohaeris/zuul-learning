package com.lh.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created on 2019/7/13.
 *
 * @author hao
 */
@Service
public class ExampleFilter extends ZuulFilter
{
    /**
     * 过滤器类型：pre、post、route、error
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * filter的执行顺序，数字越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的核心业务代码：判断请求参数中是否携带参数token，如果没有，则直接返回请求失败，有则放行该请求
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        ctx.addZuulResponseHeader("Content-type", "text/json;charset=UTF-8");
        ctx.getResponse().setCharacterEncoding("UTF-8");
        try {
            ctx.getResponse().getOutputStream().print("456");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("请求地址:"+request.getRequestURI());
        String token = request.getParameter("token");
        String msg="请求成功!";
        if(token==null) {
            ctx.setSendZuulResponse(false);
            msg="请求失败！";
            ctx.setResponseBody(msg);
            ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return msg;
    }
}
