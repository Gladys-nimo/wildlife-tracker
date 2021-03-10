import org.sql2o.Sql2o;

public class DB {

         static String connectionString = "jdbc:postgresql://ec2-52-7-115-250.compute-1.amazonaws.com:5432/d8bjgsiqb7glqn"; //!
    public static Sql2o sql2o = new Sql2o(connectionString, "dmwqctqjdpbhjd", "5e3fbbbaa453a441b72a18a121cf9921af57371d9a466309fd88c592d754487d"); //!

//
//    public static Sql2o sql2o=new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "postgres", "gladys");




}
