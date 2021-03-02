import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.HashMap;
import java.util.Map;


import static spark.Spark.*;

public class App {
    public static void main(String[] arg){
        get("/", (requsest, response) -> {
            Map<String,Object> model=new HashMap<String,Object>();
            return new ModelAndView(model,"index.hbs");
        },new HandlebarsTemplateEngine());

        get("/create/ranger",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"ranger-form.hbs");
        },new HandlebarsTemplateEngine());

    }
}
