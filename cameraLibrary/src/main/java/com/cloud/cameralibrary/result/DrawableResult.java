package com.cloud.cameralibrary.result;

import android.graphics.drawable.Drawable;

import androidx.appcompat.graphics.drawable.DrawableWrapper;

import com.cloud.cameralibrary.PhotoSelector;

import java.io.File;

class DrawableResult extends Result<Drawable> {

    DrawableResult(PhotoSelector.ResultCallback<Drawable> callback) {
        super(callback);
    }

    @Override
    Drawable onImageResult(File file) {
        return DrawableWrapper.createFromPath(file.getPath());
    }

}
