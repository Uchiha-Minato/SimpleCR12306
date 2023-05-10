package com.example.cr12306.utils;

import androidx.annotation.NonNull;

import com.example.cr12306.domain.BuyTicket;
import com.example.cr12306.domain.LeftTicket;

import java.lang.reflect.Field;

public class ClassUtils {

    /**
     * @param leftTicket 余票类，父类
     * @param buyTicket 要买票类，子类
     * 由于确认订单界面需要用到余票类中的数据，用Intent一个个传太麻烦
     * 于是直接写一个方法复制数据
     * 其中BuyTicket类继承了LeftTicket类
     * */
    public static void copyFatherToChild(@NonNull LeftTicket leftTicket, @NonNull BuyTicket buyTicket) throws Exception{
        //获得子类class对象
        Class<? extends LeftTicket> sonClass = buyTicket.getClass();
        //获得父类对象的所有公共字段
        Field[] fields = leftTicket.getClass().getFields();

        //循环父类的所有字段
        for(Field field : fields) {
            String name = field.getName();//父类属性名
            Object value = field.get(leftTicket);//父类属性值

            //获得子类与父类对应的属性
            Field sonClassField = sonClass.getField(name);
            //给子类赋值
            sonClassField.set(buyTicket, value);
        }
    }
}
