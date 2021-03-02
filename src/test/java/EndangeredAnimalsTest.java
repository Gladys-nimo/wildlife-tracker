import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndangeredAnimalsTest {
//@Rule
//public DatabaseRule database = new DatabaseRule();
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testInstanceOfEndangeredAnimalsClass_true() {
        EndangeredAnimals testAnimal= setUpNewAnimal();
        assertEquals(true,testAnimal instanceof EndangeredAnimals);

    }

    @Test
    void findByIdReturnsCorrectInfo() {
        EndangeredAnimals testAnimal=setUpNewAnimal();
        testAnimal.save();
        Animal foundAnimal= Animal.find(testAnimal.getId());
        assertTrue(foundAnimal.getHealth().equals(testAnimal.getHealth()));
    }

    @Test
    void deleteById() {
        EndangeredAnimals testAnimal=setUpNewAnimal();
        testAnimal.save();
        testAnimal.delete();
        assertEquals(null,Animal.find(testAnimal.getId()));
    }

    @Test
    void ensureFieldCannotBeEmpty() {
        EndangeredAnimals testAnimal=new EndangeredAnimals("","endangered","","");
        try {
            testAnimal.save();
        }catch (IllegalArgumentException e){

        }
    }

    @Test
    void deleteAllEntries() {
        EndangeredAnimals testAnimal=setUpNewAnimal();
        EndangeredAnimals otherAnimal=setUpNewAnimal();
        testAnimal.save();
        otherAnimal.save();
        Animal.deleteAll();
        List<Animal> animals=Animal.all();
        assertEquals(0,animals.size());
    }

    @Test
    void ensureEntryIsUpdatedCorrectly() {
        EndangeredAnimals testAnimal=setUpNewAnimal();
        EndangeredAnimals otherAnimal=testAnimal;
        testAnimal.save();
        try {
            testAnimal.update(testAnimal.getId(),"endangered","okay","newborn");
            Animal updatedAnimal=  Animal.find(testAnimal.getId());
            assertEquals(updatedAnimal.getId(),otherAnimal.getId());
            assertNotEquals(updatedAnimal.getHealth(),otherAnimal.getHealth());
        }catch (IllegalArgumentException e){

        }
    }
    private EndangeredAnimals setUpNewAnimal() {
        return new EndangeredAnimals("whiteGiraffe","endangered","healthy","young");
    }
}