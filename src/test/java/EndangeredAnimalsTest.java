import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndangeredAnimalsTest {
//@Rule
//public DatabaseRule database = new DatabaseRule();
//    @BeforeEach
//    public void before() {
////        DB.sql2o = new Sql2o("jdbc:postgresql://ec2-3-232-163-23.compute-1.amazonaws.com:5432/ds89mql6kaov4", "nlqmlfhnmxxkpu", "9efb5012f4203955d5ada3c96711648537fc4e5459e05e79ef58b4db05db0149");
//
//        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "postgres", "gladys");
////
//    }
//    @AfterEach
//    public void after() {
//        try (Connection con = DB.sql2o.open()) {
//
//            String deleteAnimalQuery = "DELETE FROM animals * ";
//            String deleteSightingsQuery = "DELETE FROM sightings *";
//            String deleteRangerQuery = "DELETE FROM rangers *";
//            con.createQuery(deleteAnimalQuery).executeUpdate();
//            con.createQuery(deleteSightingsQuery).executeUpdate();
//            con.createQuery(deleteRangerQuery).executeUpdate();
//            String deleteLocationQuery = "DELETE FROM locations *";
//            con.createQuery(deleteLocationQuery).executeUpdate();
//        }
//    }

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