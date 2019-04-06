package com.techradge.fabler.base;

import android.content.Context;

public interface BaseView<S> {

    void setPresenter(S presenter);

    Context getContext();
}
