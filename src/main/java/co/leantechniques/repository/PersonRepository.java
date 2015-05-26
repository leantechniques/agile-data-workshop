package co.leantechniques.repository;

import co.leantechniques.model.Person;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class PersonRepository {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Person> findAll() {
        Query query = sessionFactory.getCurrentSession().createQuery("from Person");
        return (List<Person>) query.list();
    }

    public void save(Person entity) {
        sessionFactory.getCurrentSession().save(entity);
    }

    public void delete(Person entity) {
        sessionFactory.getCurrentSession().delete(entity);
    }

    private Person firstOrNull(List<Person> people) {
        return people.isEmpty() ? null : people.get(0);
    }

    public Person findById(Long personId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Person where id = :id");
        query.setLong("id", personId);
        List<Person> list = query.list();
        return firstOrNull(list);
    }
}
