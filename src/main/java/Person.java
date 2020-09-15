//import java.sql.Connection;
import org.sql2o.*;
import org.sql2o.Connection;

import java.util.ArrayList;
import java.util.List;

import static spark.route.HttpMethod.get;

public class Person {
    private String name;
    private String email;
    private int id;

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO person (name, email) VALUES (:name, :email)";
            con.createQuery(sql)
                    .addParameter("name", this.name)
                    .addParameter("email", this.email)
                    .executeUpdate()
                    .getKey();

        }
    }

    public int getId() {
        return id;
    }

    public static List<Person> all() {
        String sql = "SELECT * FROM persons";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Person.class);
        }
    }

    public static Person find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM persons where id=:id";
            Person person = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Person.class);
            return person;

        }
    }

    @Override
    public boolean equals(Object otherPerson) {
        if (!(otherPerson instanceof Person)){
            return false;
        } else {
            Person newPerson = (Person) otherPerson;
            return this.getName().equals(newPerson.getName()) &&
                    this.getEmail().equals(newPerson.getEmail());
        }
    }

    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
