import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

        get("/view/ranger/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            int idOfRanger= Integer.parseInt(request.params(":id"));
            Rangers foundRanger=Rangers.find(idOfRanger);
            List<Sightings> sightings=foundRanger.getRangerSightings();
            ArrayList<String> animal=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sightings sighting : sightings){
                String animal_name=Animal.find(sighting.getAnimal_id()).getName();
                String animal_type=Animal.find(sighting.getAnimal_id()).getType();
                animal.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animal",animal);
            model.put("types",types);
            model.put("rangers",Rangers.all());
            return new ModelAndView(model,"ranger-view.hbs");
        },new HandlebarsTemplateEngine());

    }
}
