package pl.edu.agh.soa.model;

import java.util.ArrayList;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "filmlist")
    private String filmlist;

    public User() {
    }

    public User(int id, String name, int age, String filmlist) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.filmlist = filmlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFilmlist() {
        return filmlist;
    }

    public void setFilmlist(String filmlist) {
        this.filmlist = filmlist;
    }
}
