package com.lancer.eyelast.network.scheduler;

/**
 * @author lancer
 * @des
 * @Date 2020/6/28 11:35
 */
public interface OnNextWithErrorListener<T> {
    void onNext(T t);

    void onError(Throwable e);
}
