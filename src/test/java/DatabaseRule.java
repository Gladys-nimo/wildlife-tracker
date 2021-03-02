
import org.sql2o.Sql2o;
import org.junit.rules.ExternalResource;


public class DatabaseRule extends ExternalResource {




        @Override
        protected void before() {
            DB.sql2o = new Sql2o("jdbcpostgresql://localhost:5432/wildlife_tracker_test", "postgres", "gladys");
        }

        @Override
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



