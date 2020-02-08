package com.inspur.ihealth.codes.file;

import java.io.*;

public class FileTests {

    public static void main(String[] args) throws IOException {
        FileReader fr = new FileReader(new File("d:/test.txt"));
        BufferedReader br = new BufferedReader(fr);
        String s;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        //读文件内容
        while ((s = br.readLine()) != null) {
            sb.append(s).append("\n");
        }
        //对内容进行截取去掉最后一个\n,然后截取去掉最后一行，最后加入你想要的内容
        System.out.println(sb.substring(0, sb.toString()
                .substring(0, sb.length() - 1).lastIndexOf("\n")) + "\n4444");
        fr.close();
        br.close();

        //删除最后一行
        try {
            rewriteendline("d:/test.txt","");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void writeendline(String filepath, String string)
            throws Exception {

        RandomAccessFile file = new RandomAccessFile(filepath, "rw");
        long len = file.length();
        long start = file.getFilePointer();
        long nextend = start + len - 1;
        byte[] buf = new byte[1];
        file.seek(nextend);
        file.read(buf, 0, 1);

        if (buf[0] == '\n')

            file.writeBytes(string);
        else

            file.writeBytes("\r\n" + string);

        file.close();

    }

    public static void rewriteendline(String filepath, String string)
            throws Exception {

        RandomAccessFile file = new RandomAccessFile(filepath, "rw");
        long len = file.length();
        long start = file.getFilePointer();
        long nextend = start + len - 1;

        int i = -1;
        file.seek(nextend);
        byte[] buf = new byte[1];

        while (nextend > start) {

            i = file.read(buf, 0, 1);
            if (buf[0] == '\r') {
                file.setLength(nextend - start);
                break;
            }
            nextend--;
            file.seek(nextend);

        }
        file.close();
        writeendline(filepath, string);

    }

}
