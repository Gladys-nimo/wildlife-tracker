import org.sql2o.Connection;
import org.sql2o.Sql2o;



public class DatabaseRule   {


        public void before() {
            DB.sql2o = new Sql2o("postgres://mvokoemhpqigzi:27f37f87d668ceb5cad5f62447dce58ba406a74a8c087f4a1002d73434b3e6af@ec2-54-160-7-200.compute-1.amazonaws.com:5432/d9ffi410craedu");
        }


          public void after() {
            try (Connection con = DB.sql2o.open()) {

                String deleteAnimalQuery = "DELETE FROM animals ";
                String deleteSightingsQuery = "DELETE FROM sightings *";
                String deleteRangerQuery = "DELETE FROM rangers";
                con.createQuery(deleteAnimalQuery).executeUpdate();
                con.createQuery(deleteSightingsQuery).executeUpdate();
                con.createQuery(deleteRangerQuery).executeUpdate();
                String deleteLocationQuery = "DELETE FROM locations *";
                con.createQuery(deleteLocationQuery).executeUpdate();
            }
        }
    }



