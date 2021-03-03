import org.sql2o.Sql2oException;

import org.sql2o.Connection;
import java.util.List;
import java.util.Objects;

public class Animal implements DatabaseManagement{


    public int id;
    public String name;
    public String type;
    private String health;
    private String age;
    public static final String ANIMAL_TYPE = "normal";

    public Animal(String name, String type) {
        this.name = name;
        this.type = ANIMAL_TYPE;
        this.health = health;
        this.age = age;

    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }


    public void save() {
        if (this.name.equals("") || this.type.equals("") || this.name.equals(null) || this.type.equals(null)) {
            throw new IllegalArgumentException("Fields cannot be Empty");
        }
        try (Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name,type) VALUES (:name,:type)";


            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("type", this.type)
                    .executeUpdate()
                    .getKey();
        }

    }


    public void update(int id, String type, String health, String age) {
        try (Connection con = (Connection) DB.sql2o.open()) {
            if (type.equals("")) {
                throw new IllegalArgumentException("All fields must be filled");
            }
            if (type == "endangered") {
                if (health.equals("") || age.equals("")) {
                    throw new IllegalArgumentException("All fields must be filled");
                }
                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", health)
                        .addParameter("age", age)
                        .addParameter("id", this.id)
                        .executeUpdate();
            } else {

                String sql = "UPDATE animals SET type=:type,health=:health,age=:age WHERE id=:id";
                con.createQuery(sql)
                        .addParameter("type", type)
                        .addParameter("health", "")
                        .addParameter("age", "")
                        .addParameter("id", this.id)
                        .executeUpdate();
            }

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }


    public static Animal find(int id) {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE id=:id";
            Animal animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .throwOnMappingFailure(false)
                    .executeAndFetchFirst(Animal.class);
            return animal;

        }

    }

   @Override
    public void delete() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("id", this.id)
                    .executeUpdate();

        }
    }


    public static void deleteAll() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals";
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    public static List<Animal> all() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);

        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id == animal.id && Objects.equals(name, animal.name) && Objects.equals(type, animal.type) && Objects.equals(health, animal.health) && Objects.equals(age, animal.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, health, age);
    }


    }
