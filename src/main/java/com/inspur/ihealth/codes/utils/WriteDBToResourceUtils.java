package com.inspur.ihealth.codes.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ResourceUtils;

/**
 * 将内存中的数据写入到SpringBoot根目录下面
 *
 * @author 谦谦公子爱编程
 */
public class WriteDBToResourceUtils {

    public static void main(String[] args) throws IOException {
        List<Student> students = new ArrayList<Student>();
        students.add(new Student("张三", 23, "男"));
        students.add(new Student("夏红", 18, "女"));
        students.add(new Student("李四", 25, "男"));
        //假如 Student学生数据来自数据库，并且已经存放于内存中。
        writeToResource(students);
    }

    /**
     * 写到资源文件
     *
     * @param students
     * @throws IOException
     */
    private static void writeToResource(List<Student> students) throws IOException {
        String basePath = getResourceBasePath();
        String studentResourcePath = new File(basePath, "student/student.txt").getAbsolutePath();
        // 保证目录一定存在
        ensureDirectory(studentResourcePath);
        System.out.println("studentResourcePath = " + studentResourcePath);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(studentResourcePath)));
        for (Student student : students) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(student.getName());
            buffer.append("\t");
            buffer.append(student.getAge());
            buffer.append("\t");
            buffer.append(student.getSex());
            buffer.append("\r\n");

            writer.write(buffer.toString());
        }

        writer.flush();
        writer.close();
    }

    /**
     * 保证拷贝的文件的目录一定要存在
     *
     * @param filePath 文件路径
     */
    public static void ensureDirectory(String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return;
        }
        filePath = replaceSeparator(filePath);
        if (filePath.indexOf("/") != -1) {
            filePath = filePath.substring(0, filePath.lastIndexOf("/"));
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
    }

    /**
     * 将符号“\\”和“\”替换成“/”,有时候便于统一的处理路径的分隔符,避免同一个路径出现两个或三种不同的分隔符
     *
     * @param str
     * @return
     */
    public static String replaceSeparator(String str) {
        return str.replace("\\", "/").replace("\\\\", "/");
    }

    /**
     * 获取项目根路径
     *
     * @return
     */
    private static String getResourceBasePath() {
        // 获取跟目录
        File path = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
        } catch (FileNotFoundException e) {
            // nothing to do
        }
        if (path == null || !path.exists()) {
            path = new File("");
        }

        String pathStr = path.getAbsolutePath();
        // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
        pathStr = pathStr.replace("\\target\\classes", "");

        return pathStr;
    }
}




class Student {
    private String name; // 姓名
    private int age; // 年龄
    private String sex;// 性别

    public Student() {
    }

    public Student(String name, int age, String sex) {
        super();
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

}
