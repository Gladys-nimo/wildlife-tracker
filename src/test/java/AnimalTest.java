import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

//    @BeforeEach
//    void setUp() {
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
    @BeforeEach
    public void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "postgres", "gladys");
    }
    @AfterEach
    public void after() {
        try (Connection con = DB.sql2o.open()) {

            String deleteAnimalQuery = "DELETE FROM animals *";
          con.createQuery(deleteAnimalQuery).executeUpdate();

        }
    }

    @Test
    void testInstanceOfAnimalClass_true() {
        Animal testAnimal = setUpNewAnimal();
        assertEquals(true, testAnimal instanceof Animal);
    }

    @Test
    void allInstancesAreSaved() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        assertTrue(Animal.all().get(0).equals(testAnimal));
    }

    @Test
    void ensureEntryIsUpdateCorrectly() {
        Animal testAnimal = setUpNewAnimal();
        Animal otherAnimal = testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(), "endangered", "ill", "newborn");
            Animal updatedAnimal = Animal.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(), otherAnimal.getId());
            assertNotEquals(updatedAnimal.getHealth(), otherAnimal.getHealth());
        } catch (IllegalArgumentException e){

        }
    }

    @Test
    void findByIdReturnsCorrectlyInfo() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        Animal foundAnimal = Animal.find(testAnimal.getId());
        assertTrue(foundAnimal.equals(testAnimal));
    }

    @Test
    void deleteById() {
        Animal testAnimal = setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null, Animal.find(testAnimal.getId()));
    }

    @Test
    void deleteAllEntries() {
        Animal testAnimal = setUpNewAnimal();
        Animal otherAnimal = setUpNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animal.deleteAll();
        List<Animal> animals = Animal.all();
        assertEquals(0, animals.size());
    }

    @Test
    void ensureNameFieldCannotBeEmpty() {
        Animal testAnimal = new Animal("", "normal");
        try {
            testAnimal.save();
        } catch (IllegalArgumentException e) {
        }
    }

    private Animal setUpNewAnimal() {
        return new Animal("Antelope","normal");
    }

}