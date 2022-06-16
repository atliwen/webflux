package com.example.webflux.groovy;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;

import java.io.File;
import java.util.List;

/**
 * @author chy
 * @Title: GroovyClassLoaderApp
 * @Description: 演示 GroovyClassLoader 方式
 * @date 2018/9/12 22:54
 */
public class GroovyClassLoaderApp
{

    public static Object evaluate1() {
        String type = "List<String>";
        String jsonString = "[\"wei.hu\",\"mengna.shi\",\"fastJson\"]";

        Binding binding = new Binding();
        binding.setProperty("jsonString", jsonString);
        binding.setProperty("type", type);
        GroovyShell groovyShell = new GroovyShell(binding);


        stringGroove = "import com.alibaba.fastjson.JSON;\n" +
                "import com.alibaba.fastjson.TypeReference;\n" +
                "TypeReference<" + type + "> typeReference = new TypeReference<" + type + ">(){};\n" +
                "JSON.parseObject(jsonString, typeReference);";

        return groovyShell.evaluate(stringGroove);


    }

    private static GroovyClassLoader groovyClassLoader = null;

    public static void initGroovyClassLoader() {
        CompilerConfiguration config = new CompilerConfiguration();
        config.setSourceEncoding("UTF-8");
        // 设置该GroovyClassLoader的父ClassLoader为当前线程的加载器(默认)
        groovyClassLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader(), config);
    }

    static String stringGroove = "package com.example.webflux.groovy\n" +

            "class TestGroove {\n" +
            "\n" +
            "    void print()\n" +
            "    {\n" +
            "        println \"aaa\";\n" +
            "        System.out.println(\"hello word!!!!\");\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "}\n";

    public static void main(String[] args) throws Exception {


        System.out.println(evaluate1());
        //GroovyObject groovyObject = (GroovyObject) groovyClassLoader.parseClass(stringGroove).getDeclaredConstructor().newInstance();

        //groovyObject.invokeMethod("print", null);


        //loadClass();
        //System.out.println("======================");
        //loadFile();
    }

    /**
     * 通过类加载groovy
     */
    private static void loadClass() {
        GroovyObject groovyObject = null;
        try {
            groovyObject = (GroovyObject) GroovyClassLoaderApp.class.getClassLoader().loadClass("com.chy.TestGroovy").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 执行无参函数
        groovyObject.invokeMethod("print", null);

        System.out.println("============================");

        // 执行有参函数
        Object[] objects = new Object[]{"abc", "def", "ghi"};
        List<String> ls = (List<String>) groovyObject.invokeMethod("printArgs", objects);
        ls.stream().forEach(System.out::println);
    }

    /**
     * 通过文件路径加载groovy
     *
     * @return
     */
    private static boolean loadFile() {
        File groovyFile = new File("src/main/java/com/chy/TestGroovy.groovy");
        if (!groovyFile.exists()) {
            System.out.println("文件不存在");
            return false;
        }

        initGroovyClassLoader();

        try {
            List<String> result;
            // 获得TestGroovy加载后的class
            Class<?> groovyClass = groovyClassLoader.parseClass(groovyFile);
            // 获得TestGroovy的实例
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();
            // 反射调用printArgs方法得到返回值
            Object methodResult = groovyObject.invokeMethod("printArgs", new Object[]{"chy", "zjj", "xxx"});
            if (methodResult != null) {
                result = (List<String>) methodResult;
                result.stream().forEach(System.out::println);
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
