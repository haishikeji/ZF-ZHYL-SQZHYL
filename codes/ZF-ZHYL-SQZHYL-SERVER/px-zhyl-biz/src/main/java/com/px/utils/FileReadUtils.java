package com.px.utils;

import cn.hutool.core.text.CharSequenceUtil;

import java.io.*;

public class FileReadUtils {

   public static String getFileContent(String filePath) throws IOException {
       String result = null;
       File file = new File(filePath);
       RandomAccessFile randomAccessFile=new RandomAccessFile(file, "r");
       byte bytes[]=new byte[999];
       randomAccessFile.read(bytes);
       result = new String(bytes,"gbk");
       randomAccessFile.close();
       return  result;
   }


     /**
      *   获取文件最后一行数据，文件中没有数据就返回null
      */
    public static String getFileEndLine(String filePath) throws IOException {
        File file = new File(filePath);
        RandomAccessFile randomAccessFile=new RandomAccessFile(file, "r");
        //获得文件中数据的长度
        long pos=randomAccessFile.length();
        //判断文件是否为空
        if(pos==0){
            return  null;
        }
        String endLine=getEndLine(randomAccessFile,pos);
        randomAccessFile.close();
        return endLine;
    }

    /**
     * 循环获取文件最后一行数据
     */
    private static String getEndLine(RandomAccessFile randomAccessFile,long pos) throws IOException {
        String endLine="";
        while (pos>0){
            //使光标向前移动一位
            pos--;
            //是读取文件数据的光标移动到pos位置
            randomAccessFile.seek(pos);

            //下面两个if的顺序不能颠倒

            //预防文件只有第一行有不为空的数据 和 文件中只有空格、换行的情况出现
            if (pos==0){
                //读取一行数据，从光标位置到改行尾部的数据
                endLine = randomAccessFile.readLine();
                //CharSequenceUtil.isAllBlank()检查字符串是否为空
                //endLine=" \t \n"也判定为空
                if (CharSequenceUtil.isAllBlank(endLine)) {
                    endLine="";
                }
            }

            if(randomAccessFile.read()=='\n') {
                //读取一行数据，从光标位置到改行尾部的数据
                endLine = randomAccessFile.readLine();
                //CharSequenceUtil.isAllBlank()检查字符串是否为空
                //endLine=" \t \n"也判定为空
                if (CharSequenceUtil.isAllBlank(endLine)) {
                    endLine =  "";
                } else {
                    break;
                }
            }
        }
        return endLine;
    }





    public static void main(String[] args) throws IOException {

        System.out.println("最后一行数据========"+getFileEndLine("/Users/asus/Downloads/2022-08-27"));

        System.out.println("全部========"+getFileContent("/Users/asus/Downloads/2022-08-27"));
    }

}
