import java.util.Objects;

public class Person {

    @Min(50)
    private int age;

    @NotEmpty
    private String name;


    @Max(100)
    private int id;

    @Length(min = 3, max = 6)
    private int PhoneNumber;

    public Person(int age, String name, int id, int phoneNumber) {
        this.age = age;
        this.name = name;
        this.id = id;
        PhoneNumber = phoneNumber;
    }


    //////////////////////////////////////////////////////////////////////////////


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Person{" +
            "age=" + age +
            ", name='" + name + '\'' +
            ", id=" + id +
            ", PhoneNumber=" + PhoneNumber +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && id == person.id && PhoneNumber == person.PhoneNumber && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, name, id, PhoneNumber);
    }
}
