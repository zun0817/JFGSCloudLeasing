package com.cloud.leasing.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.cloud.leasing.JFGSApplication;

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

    public void downLoadFile(String url, String fileName, String fileSuffix) {
        //new DownLoadAsynctask(url, fileName).execute();
        download(url, fileName, fileSuffix);
    }

    private void download(String url, String fileName, String fileSuffix) {
        DownloadUtil.get().download(url, fileName, fileSuffix, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess() {
                //成功
                Log.i("注意", "下载成功");
                toastTest("下载成功, 请前往手机-文件管理-盾构租赁文件夹中查看");
            }

            @Override
            public void onDownloading(int progress) {
                //进度
                Log.i("注意", progress + "%");
            }

            @Override
            public void onDownloadFailed() {
                //失败
                Log.i("注意", "下载失败");
                toastTest("下载失败");
            }
        });
    }

    private void toastTest(String content) {
        new Thread(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> Toast.makeText(JFGSApplication.instance, content, Toast.LENGTH_LONG).show());
        }).start();
    }

}
