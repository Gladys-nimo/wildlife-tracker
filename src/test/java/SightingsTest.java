import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

class SightingsTest {

    @BeforeEach
    public void before() {
        DB.sql2o = new Sql2o("postgres://mvokoemhpqigzi:27f37f87d668ceb5cad5f62447dce58ba406a74a8c087f4a1002d73434b3e6af@ec2-54-160-7-200.compute-1.amazonaws.com:5432/d9ffi410craedu");
    }

    @AfterEach
    public void after() {
        try (Connection con = DB.sql2o.open()) {


            String deleteSightingsQuery = "DELETE FROM sightings *";
               con.createQuery(deleteSightingsQuery).executeUpdate();
                 }
    }
//    @Rule
//    public DatabaseRule databaseRule=new DatabaseRule();

    @Test
    public void createInstanceOfSightingsClass_true() {

        Sightings sighting= setUpNewSighting();
        assertEquals(true,sighting instanceof Sightings);
    }

    @Test
    public void allInstancesAreSaved() {
        Sightings sightings=setUpNewSighting();
        Sightings otherSighting=new Sightings(-1,1,1);
        try {
            sightings.save();
            otherSighting.save();
            assertTrue(Sightings.find(sightings.getId()).equals(sightings));
        }catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }

    @Test
    public void findSightingByID() {
        Sightings sighting=setUpNewSighting();
        sighting.save();
        Sightings foundSighting= Sightings.find(sighting.getId());
        System.out.println(sighting.getId());
        System.out.println(foundSighting.getId());
        assertEquals(true,foundSighting.equals(sighting));
//
//

    }
    @Test
    public void deleteSightingByID() {
        Sightings sighting=setUpNewSighting();
        sighting.save();
        sighting.delete();
        assertEquals(null,Sightings.find(sighting.getId()));

    }
    @Test
    public void deleteAll() {
        Sightings sighting=setUpNewSighting();
        Sightings otherSightings=setUpNewSighting();
        sighting.save();
        otherSightings.save();
        Sightings.deleteAll();

        assertEquals(0,Sightings.all().size());

    }
    private Sightings setUpNewSighting() {
        return new Sightings(1,1,1);
    }
}