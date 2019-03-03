package mo.springrestful.models;

public class PersonVersion2 {
    private Name name;

    public PersonVersion2() {
    }

    public PersonVersion2(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
