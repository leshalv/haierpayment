package com.haiercash.pluslink.capital.processer.server.utils.threading;


public abstract class InheritAction {
    private final ThreadLocalsHolder callerThreadLocalsHolder;

    public InheritAction() {
        this.callerThreadLocalsHolder = new ThreadLocalsHolder();
    }

    protected ThreadLocalsHolder backup() {
        ThreadLocalsHolder threadLocalsHolder = new ThreadLocalsHolder();
        this.callerThreadLocalsHolder.copyTo(threadLocalsHolder.getThread());
        return threadLocalsHolder;
    }

    protected void restore(ThreadLocalsHolder threadLocalsHolder) {
        threadLocalsHolder.restore();
    }
}
