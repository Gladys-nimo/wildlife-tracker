import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class LocationsTest {

    @BeforeEach
    public void before() {
        DB.sql2o = new Sql2o("postgres://mvokoemhpqigzi:27f37f87d668ceb5cad5f62447dce58ba406a74a8c087f4a1002d73434b3e6af@ec2-54-160-7-200.compute-1.amazonaws.com:5432/d9ffi410craedu");
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