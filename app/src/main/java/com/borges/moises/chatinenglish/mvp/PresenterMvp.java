package com.borges.moises.chatinenglish.mvp;

/**
 * Created by Moisés on 02/07/2016.
 */

public interface PresenterMvp<T extends ViewMvp> {
    void bindView(T viewMvp);
    void unbindView();
}
