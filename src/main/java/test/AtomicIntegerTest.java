package test;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {
    private static final int THREADS_CONUT = 20;
        public static AtomicInteger count = new AtomicInteger(0);
    // 使用此类型导致非预期错误结果
//    public static volatile int count;
    public static LocalDateTime time = LocalDateTime.now();


    public static void increase() {
        count.incrementAndGet();
//        count++;
        time = time.plusSeconds(1);
    }

    public static boolean isNullStr(String str) {
        if (str == null || str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(time.getSecond());
        Thread[] threads = new Thread[THREADS_CONUT];
        for (int i = 0; i < threads.length; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        increase();
                    }
                }
            });
            thread.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(time.getSecond());
        System.out.println(count);
    }

    @Test
    public void test1(){
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

//        System.out.println(s1 == s2);  // true
//        System.out.println(s1 == s3);  // true
//        System.out.println(s1 == s5);  // false TODO 地址相同,为何不相等?
//        System.out.println(s1 == s4);  // false
//        System.out.println(s1 == s9);  // false

        // String intern()方法
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);
    }

    @Test
    public void test2(){
        // 基本类型的封装类及对应常量池 TODO
        //5种整形的包装类Byte,Short,Integer,Long,Character的对象，
        //在值小于127时可以使用常量池
        Integer i1=127;
        Integer i2=127;
        System.out.println(i1==i2);//输出true

        //值大于127时，不会从常量池中取对象
        Integer i3=128;
        Integer i4=128;
        System.out.println(i3==i4);//输出false
        //Boolean类也实现了常量池技术

        Boolean bool1=true;
        Boolean bool2=true;
        System.out.println(bool1==bool2);//输出true

        //浮点类型的包装类没有实现常量池技术
        Double d1=1.0;
        Double d2=1.0;
        System.out.println(d1==d2); //输出false
    }

}
