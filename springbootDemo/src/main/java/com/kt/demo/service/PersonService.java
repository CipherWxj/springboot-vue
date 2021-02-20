package com.kt.demo.service;


import com.kt.demo.dao.PersonDao;
import com.kt.demo.model.Person;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonService {

    @Resource
    PersonDao personDao;

    public List<Person> getAll(){ return personDao.getAll(); }

    public void add(Person p) { personDao.add(p); }

    public void  delete(String number){ personDao.delete(number); }

    public void update(Person p){ personDao.update(p); }

    public Person getPersonByNumber(int number){ return personDao.getPersonByNumber(number); }

    public List<Person> getPersonByName(String name){ return personDao.getPersonByName(name); }

    public List<Person> queryPage(Integer startRows) { return personDao.queryPage(startRows); }

    public int getRowCount() { return personDao.getRowCount(); }

}
