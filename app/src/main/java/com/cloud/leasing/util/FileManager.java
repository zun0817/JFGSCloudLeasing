package com.cloud.leasing.util;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Keep;

import com.cloud.leasing.constant.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;

public class FileManager {

    /**
     * 获取根目录
     */
    @Keep
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals("mounted");
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        return sdDir != null ? sdDir.toString() : null;
    }

    @Keep
    public static String getPicturePath(Context context) {
        boolean hasSDCard = Environment.getExternalStorageState().equals("mounted");
        return hasSDCard ? context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() : null;
    }

    /**
     * 创建文件
     */
    public static boolean createFile(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        boolean isSuccess = true;
        // 创建要上传图片的文件夹
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                isSuccess = file.mkdirs();
            } catch (Exception e) {
                // continue
                isSuccess = false;
            }
        }
        return isSuccess;
    }

    /**
     * 读取文件
     */
    public static String readfile(String filepath) {
        File file = new File(filepath);
        File readfile = null;
        if (file.isDirectory()) {
            String[] filelist = file.list();
            if (filelist != null && filelist.length > 0) {
                readfile = new File(filepath + filelist[filelist.length - 1]);
            }
        }
        return readfile != null ? readfile.getAbsolutePath() : null;
    }

    /**
     * 获取指定目录内所有文件路径
     *
     * @param dirPath 需要查询的文件目录
     * @param _type   查询类型，比如mp3什么的
     */
    public static JSONArray getAllFiles(String dirPath, String _type) {
        File f = new File(dirPath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();

        if (files == null) {//判断权限
            return null;
        }

        JSONArray fileList = new JSONArray();
        for (File _file : files) {//遍历目录
            if (_file.isFile() && _file.getName().endsWith(_type)) {
                String _name = _file.getName();
                String filePath = _file.getAbsolutePath();//获取文件路径
                String fileName = _file.getName().substring(0, _name.length() - 4);//获取文件名
                try {
                    JSONObject _fInfo = new JSONObject();
                    _fInfo.put("name", fileName);
                    _fInfo.put("path", filePath);
                    fileList.put(_fInfo);
                } catch (Exception e) {
                }
            } else if (_file.isDirectory()) {//查询子目录
                getAllFiles(_file.getAbsolutePath(), _type);
            } else {
            }
        }
        return fileList;
    }

    /**
     * 遍历指定文件夹下的指定类型文件
     */
    public static List<File> getAllFileInDir(String fileAbsolutePath, String _type) {
        File f = new File(fileAbsolutePath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();
        if (files == null) {//判断权限
            return null;
        }
        // 遍历此目录，并且添加到集合中
        List<File> fileList = new ArrayList<>();
        for (File _file : files) {//遍历目录
            if (_file.isFile() && _file.getName().endsWith(_type)) {
                fileList.add(_file);
            } else if (_file.isDirectory()) {//查询子目录
                getAllFileInDir(_file.getAbsolutePath(), _type);
            }
        }
        return fileList;
    }

    /**
     * 遍历指定文件夹下的指定类型文件 注：日志专用
     */
    public static File getLogAllFile(String fileAbsolutePath, String _type, String fileName) {
        File f = new File(fileAbsolutePath);
        if (!f.exists()) {//判断路径是否存在
            return null;
        }

        File[] files = f.listFiles();
        if (files == null) {//判断权限
            return null;
        }
        // 遍历此目录，并且添加到集合中
        List<File> fileList = new ArrayList<>();
        for (File _file : files) {//遍历目录
            Log.d("******", fileName);
            Log.d("***", _file.getName());
            if (_file.isFile() && _file.getName().endsWith(_type) && _file.getName().equals(fileName)) {
                fileList.add(_file);
            }
        }
        return fileList.size() > 0 ? fileList.get(0) : null;
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String delFile) {
        File file = new File(delFile);
        if (!file.exists()) {
//            Toast.makeText(App.Companion.getApplication(),
//                    "删除文件失败:" + delFile + "不存在！", Toast.LENGTH_SHORT).show();
            Log.e("--Method--%s", "Copy_Delete.delete: 删除单个文件" + delFile + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteSingleFile(delFile);
            else
                return deleteDirectory(delFile);
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                Log.e("--Method--%s", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
//                Toast.makeText(App.Companion.getApplication(),
//                        "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                Log.e("--Method--%s", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "失败！");
                return false;
            }
        } else {
//            Toast.makeText(App.Companion.getApplication(),
//                    "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            Log.e("--Method--%s", "Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
//            Toast.makeText(App.Companion.getApplication(),
//                    "删除目录失败：" + filePath + "不存在！", Toast.LENGTH_SHORT).show();
            Log.e("--Method--%s", "Copy_Delete.deleteDirectory: 删除目录失败" + filePath + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
//            Toast.makeText(App.Companion.getApplication(),
//                    "删除目录失败！", Toast.LENGTH_SHORT).show();
            Log.e("--Method--%s", "Copy_Delete.deleteDirectory: 删除目录失败" + filePath + "失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            Log.e("--Method--%s", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
//            Toast.makeText(App.Companion.getApplication(),
//                    "删除目录：" + filePath + "失败！", Toast.LENGTH_SHORT).show();
            Log.e("--Method--%s", "Copy_Delete.deleteDirectory: 删除目录" + filePath + "失败！");
            return false;
        }
    }

    /**
     * 清空指定文件夹
     *
     * @param pPath
     * @param deleteDir 是否删除文件夹本身
     */
    public static boolean clearDir(final String pPath, boolean deleteDir) {
        File dir = new File(pPath);
        return clearDir(dir, deleteDir);
    }


    /**
     * 清空指定文件夹
     *
     * @param dir
     * @param deleteDir 是否删除文件夹本身
     */
    public static boolean clearDir(File dir, boolean deleteDir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return true;
        }
        if (deleteDir) {
            String command = "rm -r " + dir.getAbsolutePath();
            try {
                Runtime.getRuntime().exec(command);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            for (File file : dir.listFiles()) {
                String command = "rm -r " + file.getAbsolutePath();
                try {
                    Runtime.getRuntime().exec(command);
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }

    public static void saveFile(ResponseBody responseBody, String filename) throws IOException {
        boolean isDownLoadSuccess = true;
        long totalByte = responseBody.contentLength();
        long downloadByte = 0;
        File file = new File(Constant.INSTANCE.getFILE_PATH(), filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        byte[] buffer = new byte[1024 * 4];
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        long tempFileLen = file.length();
        randomAccessFile.seek(tempFileLen);
        while (true) {
            int len = responseBody.byteStream().read(buffer);
            if (len == -1) {
                break;
            }
            randomAccessFile.write(buffer, 0, len);
            downloadByte += len;
            isDownLoadSuccess = false;
        }
        isDownLoadSuccess = true;
        randomAccessFile.close();
    }

    /**
     * 判断文件是否存在
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName){
        File file = new File(getSDPath() + fileName);
        return file.exists();
    }

    /**
     * 在SD卡上创建文件
     * @param fileName
     * @return
     * @throws java.io.IOException
     */
    public static File createSDFile(String fileName) throws IOException {
        File file = new File(getSDPath() + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     * @param dirName 目录名字
     * @return 文件目录
     */
    public static File createDir(String dirName) {
        File dir = new File(getSDPath() + dirName);
        dir.mkdir();
        return dir;
    }

    public static File write2SDFromInput(String path, String fileName, InputStream input) {
        File file = null;
        OutputStream output = null;
        try {
            createDir(path);
            file = createSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            while (input.read(buffer) != -1) {
                output.write(buffer);
                output.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
