import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;


import static spark.Spark.*;

public class App {
    public static void main(String[] arg){
        get("/", (requsest, response) -> {
            Map<String,Object> model=new HashMap<String,Object>();
            return new ModelAndView(model,"index.hbs");
        })
    }
}
