package com.imooc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by yongduan on 2017/1/8.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public String insertTwo(String nameA, String nameB) {
        return fun(nameA, nameB);
    }

    private String fun(String nameA, String nameB) {

        String result = "test";
        try {
            User userA = new User();
            userA.setAge(30);
            userA.setName(nameA);
            userA.setAvgGrade(98);
            result = "test1";
            userRepository.save(userA);
            User userB = new User();
            userB.setAge(40);
            userB.setName(nameB);
            userB.setAvgGrade(99);
            result = "test2";
            userRepository.save(userB);
            result = "test3";
            if (nameA.compareTo("AA") == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return "SUCCESS";
        } catch (Exception e) {
            //TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return result;
        }
    }
    public static void main(String[] args){
        String s = "abc";
        testPoints("","I love my job.");//一个参数传入
        testPoints("you","and","me");//3个String参数传入
        testPoints("kk", new String[]{"you","and","me"});//可以看到传入三个String参数和传入一个长度为3的数组结果一样，再看例子
        System.out.println("---------------------------------------------------------");

        testPoints(7);
        testPoints(7,9,11);
        testPoints(new Integer[]{7,9,11});
    }

    public static void testPoints(String sing, String... s){
        if(s.length==0){
            System.out.println("没有参数传入！");
        }else if(s.length==1){
            System.out.println("1个参数传入！");
            for(int i=0;i<s.length;i++){
                System.out.println("第"+(i+1)+"个参数是"+s[i]+";");
            }
        }else{
            System.out.println("the input string is-->");
            for(int i=0;i<s.length;i++){
                System.out.println("第"+(i+1)+"个参数是"+s[i]+";");
            }
            System.out.println();
        }
    }

    public static void testPoints(Integer... itgr){
        if(itgr.length==0){
            System.out.println("没有Integer参数传入！");
        }else if(itgr.length==1){
            System.out.println("1个Integer参数传入！");
        }else{
            System.out.println("the input string is-->");
            for(int i=0;i<itgr.length;i++){
                System.out.println("第"+(i+1)+"个Integer参数是"+itgr[i]+";");
            }
            System.out.println();
        }
    }
}
