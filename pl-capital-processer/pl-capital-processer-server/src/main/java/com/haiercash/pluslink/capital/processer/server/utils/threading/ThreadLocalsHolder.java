package com.haiercash.pluslink.capital.processer.server.utils.threading;


public final class ThreadLocalsHolder {
    private static final String THREAD_LOCALS = "threadLocals";
    private static final String INHERITABLE_THREAD_LOCALS = "inheritableThreadLocals";
    private final Thread thread;
    private final Object threadLocals;
    private final Object inheritableThreadLocals;

    public ThreadLocalsHolder() {
        this(Thread.currentThread());
    }

    public ThreadLocalsHolder(Thread thread) {
        this.thread = thread;
        this.threadLocals = ReflectionUtils.getFieldValue(this.thread, THREAD_LOCALS);
        this.inheritableThreadLocals = ReflectionUtils.getFieldValue(this.thread, INHERITABLE_THREAD_LOCALS);
    }

    public Thread getThread() {
        return this.thread;
    }

    public Object getThreadLocals() {
        return this.threadLocals;
    }

    public Object getInheritableThreadLocals() {
        return this.inheritableThreadLocals;
    }

    public void copyTo(Thread thread) {
//        ReflectionUtils.setFieldValue(thread, THREAD_LOCALS, this.threadLocals);
//        ReflectionUtils.setFieldValue(thread, INHERITABLE_THREAD_LOCALS, this.inheritableThreadLocals);
    }

    public void restore() {
//        this.copyTo(this.thread);
    }
}
