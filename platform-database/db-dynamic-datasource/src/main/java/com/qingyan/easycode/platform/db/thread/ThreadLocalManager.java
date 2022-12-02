package com.qingyan.easycode.platform.db.thread;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qingyan.easycode.platform.db.DatabaseRouter;
import com.qingyan.easycode.platform.db.DynamicDataSource;

/**
 * 线程本地资源管理器
 *
 * @author xuzhou
 * @since 2022/12/2
 */
public class ThreadLocalManager {

    /**
     * The Constant requestLocal.
     */
    private static final ThreadLocal<HttpServletRequest> REQUEST_LOCAL = new ThreadLocal<>();
    /**
     * The Constant responseLocal.
     */
    private static final ThreadLocal<HttpServletResponse> RESPONSE_LOCAL = new ThreadLocal<>();


    /**
     * set HttpServletRequest
     *
     * @param request HttpServletRequest
     */
    public static void setHttpServletRequest(HttpServletRequest request) {
        REQUEST_LOCAL.set(request);
    }

    /**
     * get HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return REQUEST_LOCAL.get();
    }


    /**
     * set httpServletResponse
     *
     * @param response HttpServletResponse
     */
    public static void setHttpServletResponse(HttpServletResponse response) {
        RESPONSE_LOCAL.set(response);
    }


    /**
     * get httpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getHttpServletResponse() {
        return RESPONSE_LOCAL.get();
    }

    /**
     * 清理需要清理的 threadlocal
     */
    public static void clearAllThreadLocalIfNeeded() {

        DatabaseRouter.removeAll();
        DynamicDataSource.clearCurTenant();

        RESPONSE_LOCAL.remove();
        REQUEST_LOCAL.remove();

    }


}
