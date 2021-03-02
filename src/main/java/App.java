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

       post("/create/ranger/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            String badge_number=request.queryParams("badge");
            String phone_number=request.queryParams("phone");
            Rangers ranger=new Rangers(name,badge_number,phone_number);
            ranger.save();
            return new ModelAndView(model,"ranger-form.hbs");
        }, new HandlebarsTemplateEngine());

       get("/view/rangers",(request, response) -> {
           Map<String,Object> model=new HashMap<String, Object>();
           model.put("rangers",Rangers.all());
           return new ModelAndView(model,"ranger-view.hbs");
       }, new HandlebarsTemplateEngine());

    }
}
