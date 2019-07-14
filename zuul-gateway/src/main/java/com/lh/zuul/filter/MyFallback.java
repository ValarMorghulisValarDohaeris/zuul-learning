package com.lh.zuul.filter;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 服务降级处理类，当某个服务无法访问的时候，进行回退处理
 * 注：自定义该服务的异常和自定义异常回退处理最好不要在同一个服务同时使用，如果同时使用，会优先进行自定义异常回退处理的处理。
 * Created on 2019/7/13.
 *
 * @author hao
 */
@Service
public class MyFallback implements FallbackProvider
{
    private static  final  String SERVER_NAME="springcloud-zuul-server2";

    @Override
    public String getRoute() {
        return SERVER_NAME;
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        //标记不同的异常为不同的http状态值
        if (cause instanceof HystrixTimeoutException) {
            return response(HttpStatus.GATEWAY_TIMEOUT);
        } else {
            //可继续添加自定义异常类
            return response(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //处理
    private ClientHttpResponse response(final HttpStatus status) {
        String msg="该"+SERVER_NAME+"服务暂时不可用!";
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream(msg.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
