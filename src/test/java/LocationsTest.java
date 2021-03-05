import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class LocationsTest {

    @BeforeEach
    public void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://ec2-50-16-108-41.compute-1.amazonaws.com:5432/db7ir085nl2n51", "mbyktqmeseldmb","27df694c443e2b041d10bd4fbf2990bcb99d90786ed9e69f9456ef27d5300a5");

//        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "postgres", "gladys");
    }
    @AfterEach
    public void after() {
        try (Connection con = DB.sql2o.open()) {


            String deleteLocationQuery = "DELETE FROM locations *";
            con.createQuery(deleteLocationQuery).executeUpdate();
            String deleteSightingsQuery = "DELETE FROM sightings *";
            con.createQuery(deleteSightingsQuery).executeUpdate();
        }
    }
//    @Rule
//    public DatabaseRule databaseRule = new DatabaseRule();

    @Test
    void createInstanceOfLocationClass() {
        Locations location =setUpNewLocation();
        assertEquals(true,location instanceof Locations);
    }

    @Test
    void allEntriesAreSaved() {
        Locations location = setUpNewLocation();
        Locations newLocation = new Locations("");
        try {
            location.save();
            assertTrue(Locations.all().get(0).equals(location));
            newLocation.save();
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    void entryIsDeletedSuccessfully() {
        Locations location=setUpNewLocation();
        Locations newLocation=new Locations("Zone B");
        location.save();
        newLocation.save();
        location.delete();
        assertEquals(null,Locations.find(location.getId()));
    }

    @Test
    void allSightingsAreReturnedForRanger() {
        Locations location = setUpNewLocation();
        try {

            location.save();
            Sightings sighting=new Sightings(location.getId(),1,1);
            Sightings otherSighting=new Sightings(location.getId(),1,1);
            sighting.save();
            otherSighting.save();
            assertEquals(location.getLocationSightings().get(0),sighting);
            assertEquals(location.getLocationSightings().get(1),otherSighting);
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }

    }
    private Locations setUpNewLocation() {
        return new Locations("Zone A");
    }
}