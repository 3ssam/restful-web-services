package mo.springrestful.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

//@JsonFilter("GetSpecialField")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2,message = "you should enter name at least contain 2 character")
    private String name;

    @Past
    private Date birthday;

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", birthday=" + birthday + "]";
    }

    public User() {
    }


    public User(Integer id, String name, Date birthday) {
        super();
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
