package com.cloud.cameralibrary.result;

import com.cloud.cameralibrary.PhotoSelector;
import com.cloud.cameralibrary.tools.Tools;

import java.io.File;

class Base64Result extends Result<String> {

    Base64Result(PhotoSelector.ResultCallback<String> callback) {
        super(callback);
    }

    @Override
    String onImageResult(File file) {
        return Tools.readBase64FromFile(file);
    }

}
