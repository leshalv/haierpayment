package com.haiercash.pluslink.capital.processer.server.pvm;

import org.springframework.context.ApplicationEvent;

/**
 * 事件元素，此为执行事件。
 *
 * @author xiaobin
 * @create 2018-07-13 下午3:16
 **/
public abstract class EngineEvent extends ApplicationEvent {

    /**
     * 当前事件是否中止当前活动
     */
    private boolean interrupting = false;

    /**
     * 立即执行则不会放入队列中
     */
    protected boolean immediate = true;

    public EngineEvent(Object source) {
        super(source);
    }

    public EngineEvent(Object source, boolean interrupting) {
        super(source);
        this.interrupting = interrupting;
    }

    public boolean isInterrupting() {
        return interrupting;
    }

    public void setInterrupting(boolean interrupting) {
        this.interrupting = interrupting;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public void setImmediate(boolean immediate) {
        this.immediate = immediate;
    }
}
