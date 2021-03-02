import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalTest {

    @BeforeEach
    void setUp() throws Exception {
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    public DatabaseRule databaseRule = new DatabaseRule();

    @Test
    void testInstanceOfAnimalClass_true() {
        Animal testAnimal = setUpNewAnimal();
        assertEquals(true, testAnimal instanceof Animal);
    }

    @Test
    void allInstancesAreSaved() {
        Animal testAnimal = setUpNewAnimal();
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
        } catch (IllegalArgumentException e) {
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