package com.kt.demo.controller;

import com.kt.demo.model.Person;
import com.kt.demo.service.PersonService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping ("/")
public class PersonController {

    @Resource
   PersonService personService;

    //显示所有列表信息
    @GetMapping ("/show")
    public List<Person> show() {
        return personService.getAll();
    }

    //增加
    @PostMapping("/add")
    public void add(Person person){
        System.out.println(person);
        personService.add(person);
    }

    //删除
    @PostMapping ("/del")
    public void del(String number) {
        personService.delete(number);
    }

    //修改
    @PostMapping("/edit")
    public void edit(Person person){
        personService.update(person);
    }

    //number查询
    @PostMapping ("/select")
    public Person queryNumber(int number) {
        return personService.getPersonByNumber(number);
    }

    //姓名查询
    @PostMapping ("/query")
    public List<Person> queryName(String name) {
        return personService.getPersonByName(name);
    }

    /**
     * 分页
     * @return
     */
    @PostMapping ("/page")
    public List<Person> page(Integer page){
        int pageNow = page == null ? 1 : page;
        int pageSize = 5;
        int startRows = pageSize*(pageNow-1);
        List<Person> list = personService.queryPage(startRows);
        return list;
    }

    /**
     * rows
     * @return
     */
    @PostMapping ("/rows")
    @ResponseBody
    public int rows(){
        return personService.getRowCount();
    }

}
