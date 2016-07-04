package com.borges.moises.chatinenglish.chat;

import com.borges.moises.chatinenglish.mvp.ViewMvp;

/**
 * Created by Moisés on 03/07/2016.
 */

public interface ChatView extends ViewMvp {
    void showMessageNotSentError();

    void cleanMessageField();

    void showMessage(String message);
}
