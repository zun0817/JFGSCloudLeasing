package com.cloud.cameralibrary.result;


import com.cloud.cameralibrary.PhotoSelector;
import com.cloud.cameralibrary.tools.Tools;

import java.io.File;

class BytesResult extends Result<byte[]> {

    BytesResult(PhotoSelector.ResultCallback<byte[]> callback) {
        super(callback);
    }

    @Override
    byte[] onImageResult(File file) {
        return Tools.readBytesFromFile(file);
    }

}
