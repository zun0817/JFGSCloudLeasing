package com.cloud.leasing.util;

public class HttpDownload {

    private static HttpDownload mInstance;

    private HttpDownload() {
    }

    public static synchronized HttpDownload getInstance() {
        if (mInstance == null) {
            mInstance = new HttpDownload();
        }
        return mInstance;
    }

    public void downLoadFile(String url, String fileName){
        new DownLoadAsynctask(url, fileName).execute();
    }

}
