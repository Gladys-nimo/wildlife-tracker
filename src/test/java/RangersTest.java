import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class RangersTest {

    @BeforeEach
    public void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://ec2-50-16-108-41.compute-1.amazonaws.com");
//        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "postgres", "gladys");
    }

    @AfterEach
    public void after() {
        try (Connection con = DB.sql2o.open()) {


            String deleteRangerQuery = "DELETE FROM rangers *";
            con.createQuery(deleteRangerQuery).executeUpdate();
                  }
    }
//    @Rule
//    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    void name() {
    }

    @Test
    public void createInstanceOfRangersClass_true(){
        Rangers ranger= setUpNewRanger();
        assertEquals(true,ranger instanceof Rangers);
    }

    @Test
    public void allEntriesAreSaved() {
        Rangers ranger= setUpNewRanger();
        ranger.save();
        assertTrue(Rangers.all().get(0).equals(ranger));

    }

    @Test
    public void emptyFieldsAreNotSaved() {
        Rangers ranger=new Rangers("","","");
        try{
            ranger.save();
            assertTrue(Rangers.all().get(0).equals(ranger));
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void findById() {
        Rangers ranger= setUpNewRanger();
        Rangers otherRanger=new Rangers("Sylvia","2","0726108898");
        ranger.save();
        otherRanger.save();
        Rangers foundRanger=Rangers.find(ranger.getId());
        assertTrue(foundRanger.equals(ranger));

    }

    @Test
    public void entryIsUpdatedCorrectly() {
        Rangers ranger= setUpNewRanger();
        Rangers otherRanger=ranger;
        ranger.save();
        try {
            ranger.update(ranger.getId(),"","");
            Rangers foundRanger=Rangers.find(ranger.getId());
            assertNotEquals(foundRanger,otherRanger);
            assertEquals(foundRanger.getId(),otherRanger.getId());

        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void entriesAreDeleted() {
        Rangers ranger= setUpNewRanger();
        Rangers otherRanger=new Rangers("Sylvia","2","0726108898");
        ranger.save();
        otherRanger.save();
        ranger.delete();
        assertEquals(null,Rangers.find(ranger.getId()));

    }

    @Test
    public void allSightingsAreReturnedForRanger() {
        Rangers ranger=setUpNewRanger();
        try {
            Locations location=new Locations("Zone A");
            ranger.save();
            location.save();
            Sightings sighting=new Sightings(location.getId(),ranger.getId(),1);
            Sightings otherSighting=new Sightings(1,ranger.getId(),1);
            sighting.save();
            otherSighting.save();
            assertEquals(ranger.getRangerSightings().get(0),sighting);
            assertEquals(ranger.getRangerSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }

    }
    private Rangers setUpNewRanger() {
        return new Rangers("Gladys","1","0734256789");
    }
}