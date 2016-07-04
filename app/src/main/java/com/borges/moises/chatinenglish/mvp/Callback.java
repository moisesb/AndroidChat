package com.borges.moises.chatinenglish.mvp;

/**
 * Created by Moisés on 02/07/2016.
 */

public interface Callback<T> {
    void onSuccess(T data);
    void onError();
}
