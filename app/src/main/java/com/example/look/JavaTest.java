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


}
