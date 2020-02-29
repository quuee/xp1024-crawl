package com.xp1024.tools;

import com.xp1024.util.UrlUtil;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ccx
 * @version V1.0
 * @Package com.xp1024.tools
 * @date 2020/2/28 19:26
 */
public class M3U8DownloadUtil {


    //下载m3u8索引文件
//    public String downloadIndexFile(String originUrl,String parentPath)  {
//
//
//    }

    //读取索引文件
    public static String readM3u8IndexFile(String filePath) {
        File file = new File(filePath);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }

    //解析索引文件
    public static List<String> analysisIndex(String content) {
        Pattern pattern = Pattern.compile(".*ts");
        Matcher ma = pattern.matcher(content);

        List<String> list = new ArrayList<>();

        while(ma.find()){
            list.add(ma.group());
        }
        return list;
    }

    /**
     * 通用下载
     * @param originUrl 文件源路径
     * @param parentPath 本地存储路径
     */
    public static String commonDownload(String originUrl,String parentPath) {
        File parent = new File(parentPath);
        if(!parent.exists()){
            parent.mkdirs();
        }

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File file = null;
        try {
            URL url = new URL(originUrl);
            URLConnection urlConnection =  url.openConnection();
            urlConnection.setConnectTimeout(60000);//链接超时数60秒
//            urlConnection.setUseCaches(false);
            urlConnection.setReadTimeout(120000);//读取超时数120秒
            urlConnection.setDoInput(true);//默认为true

            inputStream = urlConnection.getInputStream();

            file = new File(parentPath +File.separator+ UrlUtil.getLastName(originUrl));
            if(!file.exists()){
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            int byteread = 0;
            byte[] buffer = new byte[1024];
            while ((byteread = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file.getPath();
    }

    /**
     * 合并源目录下的
     * 文件合并顺序 不能乱
     * @param directory 源目录
     * @param destination 文件
     */
    public static void mergeM3u8ToVideo(File directory,File destination){
        FileOutputStream outputStream=null;
        FileInputStream in = null;
        try {
            outputStream = new FileOutputStream(destination);
            //根据文件结尾过滤
            File[] files = directory.listFiles((FilenameFilter) new SuffixFileFilter(".ts"));

            // TODO 文件排序

            for (File file : files) {
                in = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while((len = in.read(bytes)) > 0){
                    outputStream.write(bytes,0,len);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
