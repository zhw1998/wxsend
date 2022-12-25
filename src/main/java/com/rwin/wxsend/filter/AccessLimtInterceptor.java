package com.rwin.wxsend.filter;

import cn.hutool.core.thread.ThreadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
/** * 自定义拦截器 */
@Component
public class AccessLimtInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (null == accessLimit) {
                return true;
            }
            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();

            String ip=request.getRemoteAddr();
            String key = request.getServletPath() + ":" + ip ;
            Integer count = (Integer) redisTemplate.opsForValue().get(key);
            if (null == count || -1 == count) {

                redisTemplate.opsForValue().set(key, 1,seconds, TimeUnit.SECONDS);
                return true;
            }
            if (count < maxCount) {

                count = count+1;
                redisTemplate.opsForValue().set(key, count,0);
                return true;
            }
            // response 返回 json 请求过于频繁请稍后再试
            throw new ThreadException("操作过于频繁");
        }
        return true;
    }
}