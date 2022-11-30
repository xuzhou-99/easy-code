package com.qingyan.easycode.platform.db;

import java.util.Stack;

/**
 * @param <T>
 * @author xuzhou
 */
public class ExtendAnnotationThreadLocal<T> {

    private ThreadLocal<Stack<T>> extendThreadLocal = new ThreadLocal<>();


    public T get() {

        Stack<T> stack = extendThreadLocal.get();

        if (stack == null || stack.isEmpty()) {
            return null;
        }

        return stack.peek();
    }


    /**
     * @param value
     */
    public void set(T value) {

        Stack<T> stack = extendThreadLocal.get();

        if (stack == null) {
            stack = new Stack<>();
        }

        stack.push(value);

        extendThreadLocal.set(stack);
    }

    /**
     * 全部清除
     */
    public void remove() {
        extendThreadLocal.remove();
    }

    /**
     * 只清理一个
     */
    public void removeOne() {

        Stack<T> stack = extendThreadLocal.get();

        if (stack == null || stack.isEmpty()) {
            return;
        }

        stack.pop();

        extendThreadLocal.set(stack);
    }
}