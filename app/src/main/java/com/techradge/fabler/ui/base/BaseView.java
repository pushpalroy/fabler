package com.techradge.fabler.ui.base;

import android.content.Context;

public interface BaseView<S> {

    void setPresenter(S presenter);

    Context getContext();
}
