import org.sql2o.Sql2o;

public class DB {
    public static Sql2o sql2o=new Sql2o("jdbc:postgresql://ec2-50-16-108-41.compute-1.amazonaws.com:5432/db7ir085nl2n51", "mbyktqmeseldmb","27df694c443e2b041d10bd4fbf2990bcb99d90786ed9e69f9456ef27d5300a5");
//    public static Sql2o sql2o=new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "postgres", "gladys");
}
