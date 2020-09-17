import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Person {
    private String name;
    private String email;
    private int id;

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

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO persons (name, email) VALUES (:name, :email)";
            con.createQuery(sql)
                    .addParameter("name", this.name)
                    .addParameter("email", this.email)
                    .executeUpdate();
        }
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

    public List<Monster> getMonsters() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM monsters where personId=:id";
            return con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeAndFetch(Monster.class);
        }
    }


}
