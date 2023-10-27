package com.example.look;

import com.example.look.bean.Student;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Java测试
 */
class JavaTest {

    public static void main(String[] args) throws Exception {
//
//        String str = "使用完整服务\n本应用需要获取个人信息才可使用完整服务名单前仅可浏览部分内容";
//        System.out.println(str.length());
        // addNum();
        //  changeFileName();
        testObject();
    }

    // 冒泡排序
    private void maoPao(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    // 字节流demo: 使用字节流复制一张图片  流的开闭原则: 先开后关,后开先关
    private static void testIO() {
        // 文件路径
        String aPath = "E:\\practice\\io_test_a\\a.jpg";
        String bPath = "E:\\practice\\io_test_b\\b.jpg";

        try (FileInputStream fis = new FileInputStream(new File(aPath));
             FileOutputStream fos = new FileOutputStream(new File(bPath))) {
            int len = 0;
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 序列化: 把对象转换为字节序列的过程称为对象的序列化。
    private static void serializableOut() {
        Student stu = new Student();
        stu.setAge(20);
        stu.setName("序列化测试");
        String serA = "E:\\practice\\serialize_a\\a.txt";
        try (
                FileOutputStream fos = new FileOutputStream(new File(serA));
                ObjectOutputStream objOut = new ObjectOutputStream(fos)) {
            objOut.writeObject(stu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testObject() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("小花", "666"));
        personList.add(new Person("小明", "888"));

        for (int i = 0; i < personList.size(); i++) {
            if (personList.get(i).price.equals("小明")) {
                personList.set(i, new Person("郭靖", "厉害"));
            }
        }

//        for (Person person : personList) {
//            if (person.price.equals("小明")) {
//                person.setPrice("郭靖");
//                person.setNum("利害");
//            }
//        }

        System.out.print(personList.toString());
    }

    // 批量修改文件名
    private static void changeFileName() {
        //  首先找到需要修改文件的文件夹，也可以像我这样将文件拷贝到一个我们容易找到的文件夹
        File file = new File("E:\\demo\\aaa");
        File[] list = file.listFiles();
        if (file.exists() && file.isDirectory()) {
            for (int i = 0; i < list.length; i++) {
                String name = list[i].getName();
                //  我这里是通过下标来找到字符串的位置，也可以有很多种方式，比如，lastIndexOf()
                //  具体请学习String的常用方法
                int index = name.indexOf(0);
                //  接下来就是将文件名修改 【XXX项目】+你截取的字符串
                //  我截取的是整条文件名
                //  也可以通过i进行排列，比如：加上i+"、"就会变成  1、【XXX项目】文件名称
                String name2 = "同事三分亲" + name.substring(index + 1);
                //  将文件保存回aaa文件夹，也可存放在其他你需要保存的地方
                File dest = new File("E:/demo/aaa" + "/" + name2);
                list[i].renameTo(dest);
                System.out.println(dest.getName());
            }
        }
    }

    private static Student serializableIn() {
        String serA = "E:\\practice\\serialize_a\\a.txt";
        Student stu = null;
        try (FileInputStream fis = new FileInputStream(new File(serA));
             ObjectInputStream objIn = new ObjectInputStream(fis)) {
            stu = (Student) objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stu;
    }

    // 使用 BigDecimal求和
    public static void addNum() {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("12.9", "3"));
        personList.add(new Person("5.9", "2"));
        personList.add(new Person("9.9", "1"));
        Iterator<Person> it = personList.iterator();
        BigDecimal abc = BigDecimal.ZERO;
        while (it.hasNext()) {
            Person person = it.next();
            BigDecimal count = new BigDecimal(person.getNum()).multiply(new BigDecimal(person.getPrice()));
            abc = abc.add(count);
        }
        System.out.println("总数:" + abc);
    }

    static class Person {

        String price;
        String num;

        public Person(String price, String num) {
            this.price = price;
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "price='" + price + '\'' +
                    ", num='" + num + '\'' +
                    '}';
        }
    }

}
