package mo.springrestful.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", posts=" + posts +
                '}';
    }

    public User() {
    }

    public User(Integer id, @Size(min = 2, message = "you should enter name at least contain 2 character") String name, @Past Date birthday) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
    }

    public User(@Size(min = 2, message = "you should enter name at least contain 2 character") String name, @Past Date birthday, List<Post> posts) {
        this.name = name;
        this.birthday = birthday;
        this.posts = posts;
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
