package com.qingyan.easycode.platform.db.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.qingyan.easycode.platform.db.thread.ThreadLocalManager;

/**
 * @author xuzhou
 * @since 2022/12/2
 */
public class DynamicThreadLocalInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // ThreadLocal 清理，防止异常中断导致的脏数据
        ThreadLocalManager.clearAllThreadLocalIfNeeded();

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        try {
            HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
        } finally {
            ThreadLocalManager.clearAllThreadLocalIfNeeded();
        }
    }
}
