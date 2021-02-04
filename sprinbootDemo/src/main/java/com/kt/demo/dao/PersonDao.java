package com.kt.demo.dao;

import com.kt.demo.model.Person;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PersonDao {

        /*
        查所有
        return List<Person>
         */
        List<Person> getAll();

        /*
        增加
        {p} 要新增的Person实例
         */
        void add(Person p);

        /*
        删除
        {number} 要删除人员的 number
         */
        void delete(String number);

        /*
        更新
        {p} 要更新的Person实例
         */
        void update(Person p);

        /*
        根据number查询
        {number} 要查询人员的 number
        */
        Person getPersonByNumber(int number);

        /*
        根据姓名查询
        {name} 要查询人员的 name
        */
        List<Person> getPersonByName(String name);

        List<Person> queryPage(Integer startRows);

        int getRowCount();
}

