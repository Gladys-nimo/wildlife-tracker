import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationsTest {

    @BeforeEach
    void setUp() throws Exception{
    }

    @AfterEach
    void tearDown() throws Exception{
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