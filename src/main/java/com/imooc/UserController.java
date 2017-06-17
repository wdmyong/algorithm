package com.imooc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.math.BigDecimal;

/**
 * Created by yongduan on 2017/1/8.
 */
@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public List<User> userList() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/users")
    public User userAdd(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "age") Integer age,
            @RequestParam(value = "avg_grade") Integer avgGrade) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setAvgGrade(avgGrade);
        return userRepository.save(user);
    }

    @GetMapping(value = "/users/{id}")
    public User userFindOne(@PathVariable("id") Integer id) {
        return userRepository.findOne(id);
    }

    @GetMapping(value = "/users/age/{age}")
    public List<User> usersFindByAge(@PathVariable("age") Integer age) {
        return userRepository.findByAge(age);
    }

    @PutMapping(value = "/users/{id}")
    public User updateUser(@PathVariable("id") Integer id,
                           @RequestParam(value = "name") String name,
                           @RequestParam(value = "age") Integer age,
                           @RequestParam(value = "avg_grade") Integer avgGrade) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        user.setAvgGrade(avgGrade);
        return userRepository.save(user);
    }

    @DeleteMapping(value = "users/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userRepository.delete(id);
    }

    @PostMapping(value = "users/two")
    public String insertTwo(@RequestParam("n1") String n1,
                            @RequestParam("n2") String n2) {
        String res = userService.insertTwo(n1, n2);
        System.out.println(res);
        return res;
    }

    public static void main(String[] args) {
        double a = 100.021001;
        double b = 0.991;
        double e = 1.012001;
        BigDecimal a1 = new BigDecimal(Double.toString(a));
        BigDecimal b1 = new BigDecimal(Double.toString(b));
        BigDecimal a2 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        long c = a1.add(b1).longValue();
        double d = a2.subtract(b2).doubleValue();
        System.out.println("c="+c);
        System.out.println("d="+d);
        System.out.println(c==e);
    }
}
