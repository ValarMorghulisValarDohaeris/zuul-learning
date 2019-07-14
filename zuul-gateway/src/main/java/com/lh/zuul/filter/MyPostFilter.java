package com.lh.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulConstants;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created on 2019/7/13.
 *
 * @author hao
 */
@Service
public class MyPostFilter extends ZuulFilter
{
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("执行zuul后置处理器");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        response.setHeader("X-Test", UUID.randomUUID().toString());
        try {
            response.getOutputStream().print("123");
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new ZuulException("A", 1, "B");
        //return null;
    }
}
