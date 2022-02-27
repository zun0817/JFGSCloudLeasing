package com.cloud.leasing.util;

import android.os.AsyncTask;
import android.widget.Toast;

import com.cloud.leasing.JFGSApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownLoadAsynctask extends AsyncTask<String, Integer, Boolean> {

    String path, fileName, downloadurl;

    public DownLoadAsynctask(String url, String fileName) {
        this.downloadurl = url;
        this.fileName = fileName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (aBoolean) {
            Toast.makeText(JFGSApplication.instance, "下载完成，请到文件管理查看", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            URL url = new URL(downloadurl);
            URLConnection urlConnection = url.openConnection();

            path = JFGSApplication.instance.getExternalFilesDir(null).getPath() + "/" + "xxxxx.pdf";
            File file = new File(path);

            if (file.exists()) {
                file.delete();
            }
            //获取文件的总长度
            int contentLength = urlConnection.getContentLength();
            //获取输入流
            InputStream in = urlConnection.getInputStream();
            byte[] b = new byte[1024];
            int DownloadLength = 0; //用于保存实时下载长度
            int len = 0;
            OutputStream outputStream = new FileOutputStream(path);
            while ((len = in.read(b)) > -1) {
                outputStream.write(b, 0, len);
                DownloadLength += len;
                publishProgress(DownloadLength * 100 / contentLength);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
