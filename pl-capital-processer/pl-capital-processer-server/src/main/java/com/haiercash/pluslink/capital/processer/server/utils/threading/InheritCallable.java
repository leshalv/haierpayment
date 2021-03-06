package com.haiercash.pluslink.capital.processer.server.utils.threading;

import org.springframework.util.Assert;

import java.util.concurrent.Callable;


public final class InheritCallable<V> extends InheritAction implements Callable<V> {
    private final Callable<V> callable;

    public InheritCallable(Callable<V> callable) {
        Assert.notNull(callable, "callable can not be null");
        this.callable = callable;
    }

    @Override
    public V call() throws Exception {
        final ThreadLocalsHolder threadLocalsHolder = this.backup();
        try {
            return this.callable.call();
        } finally {
            this.restore(threadLocalsHolder);
        }
    }
}
