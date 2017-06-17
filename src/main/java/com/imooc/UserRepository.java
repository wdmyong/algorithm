package com.imooc;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yongduan on 2017/1/8.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    // 通过年龄查询
    // 方法名要按规矩写
    public List<User> findByAge(Integer age);
}
