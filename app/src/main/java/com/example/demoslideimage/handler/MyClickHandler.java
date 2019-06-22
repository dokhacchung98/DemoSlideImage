package com.example.demoslideimage.handler;

import android.view.View;

public interface MyClickHandler {
    void onClick(View view);

    void onClickWithData(View view, Object value);
}
