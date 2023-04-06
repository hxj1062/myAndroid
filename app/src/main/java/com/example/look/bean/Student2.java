package com.example.look.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Student2 implements Parcelable {
    private String content;
    private int age;
    public Student2(){

    }

    protected Student2(Parcel in) {
        content = in.readString();
        age = in.readInt();
    }

    // 为了能够实现模板参数的传入，这里定义Creator嵌入接口,内含两个接口函数分别返回单个和多个继承类实例
    public static final Creator<Student2> CREATOR = new Creator<Student2>() {
        @Override
        public Student2 createFromParcel(Parcel in) {
            return new Student2(in);
        }

        @Override
        public Student2[] newArray(int size) {
            return new Student2[size];
        }
    };

    // 内容接口描述，默认返回0就可以
    @Override
    public int describeContents() {
        return 0;
    }

    // 重写writeToParcel方法，将你的对象序列化为一个Parcel对象，
    // 即：将类的数据写入外部提供的Parcel中，打包需要传递的数据到Parcel容器保存，以便从 Parcel容器获取数据
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(content);
        dest.writeInt(age);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
