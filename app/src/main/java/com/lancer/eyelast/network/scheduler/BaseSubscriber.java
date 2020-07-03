package com.lancer.eyelast.network.scheduler;

import android.util.Log;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author lancer
 * @des
 * @Date 2020/6/28 11:36
 */
public class BaseSubscriber<E> implements Observer<E> {


    private OnNextWithErrorListener<E> mOnNextWithErrorListener;

    private Disposable d;
    private static final String TAG = "BaseSubscriber";


    public BaseSubscriber(OnNextWithErrorListener<E> mOnNextWithErrorListener) {
        this.mOnNextWithErrorListener = mOnNextWithErrorListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
    }

    @Override
    public void onNext(E e) {
        mOnNextWithErrorListener.onNext(e);
    }

    @Override
    public void onError(Throwable e) {
        if (mOnNextWithErrorListener != null) {
            mOnNextWithErrorListener.onError(e);
        } else {
            if (e instanceof SocketTimeoutException) {
                Log.e(TAG, "网络连接超时，请检查您的网络状态");
            } else if (e instanceof ConnectException) {
                Log.e(TAG, "网络连接失败，请检查您的网络状态");
            } else if (e instanceof SocketException) {
                Log.e(TAG, "网络连接失败，请检查您的网络状态");
            }
        }
        if (!d.isDisposed()) {
            d.dispose();
        }
    }

    @Override
    public void onComplete() {
        if (!d.isDisposed()) {
            d.dispose();
        }
    }
}
