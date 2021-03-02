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
            ArrayList<String> animals=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sightings sighting : sightings){
                String animal_name=Animal.find(sighting.getAnimal_id()).getName();
                String animal_type=Animal.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("rangers",Rangers.all());
            return new ModelAndView(model,"ranger-view.hbs");
        },new HandlebarsTemplateEngine());

        get("/create/location",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"location-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/location/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String name=request.queryParams("name");
            Locations location=new Locations(name);
            try {
                location.save();
            }catch (IllegalArgumentException e){
                System.out.println(e);
            }

            return new ModelAndView(model,"location-form.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/locations",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            model.put("locations",Locations.all());
            return new ModelAndView(model,"location-view.hbs");
        },new HandlebarsTemplateEngine());

        get("/view/location/sightings/:id",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            int idOfLocation= Integer.parseInt(request.params(":id"));
            Locations foundLocation=Locations.find(idOfLocation);
            List<Sightings> sightings=foundLocation.getLocationSightings();
            ArrayList<String> animals=new ArrayList<String>();
            ArrayList<String> types=new ArrayList<String>();
            for (Sightings sighting : sightings){
                String animal_name=Animal.find(sighting.getAnimal_id()).getName();
                String animal_type=Animal.find(sighting.getAnimal_id()).getType();
                animals.add(animal_name);
                types.add(animal_type);
            }
            model.put("sightings",sightings);
            model.put("animals",animals);
            model.put("types",types);
            model.put("locations",Locations.all());
            return new ModelAndView(model,"location-view.hbs");
        },new HandlebarsTemplateEngine());

        get("/create/animal",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            return new ModelAndView(model,"animal-form.hbs");
        },new HandlebarsTemplateEngine());

        post("/create/animal/new",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            String type=request.queryParams("type");
            System.out.println(type);
            String health=request.queryParams("health");
            System.out.println(health);
            String age=request.queryParams("age");
            System.out.println(age);
            String name=request.queryParams("name");
            System.out.println(name);
            if(type.equals(EndangeredAnimals.ANIMAL_TYPE)){
                EndangeredAnimals endangered=new EndangeredAnimals(name,EndangeredAnimals.ANIMAL_TYPE,health,age);
                endangered.save();
            }
            else {
                Animal animal=new Animal(name,Animal.ANIMAL_TYPE);
                animal.save();
            }
            return new ModelAndView(model,"animal-form.hbs");
        },new HandlebarsTemplateEngine());

        get("/create/animal/endangered",(request, response) -> {
            Map<String,Object> model=new HashMap<String, Object>();
            List<String> health= new ArrayList<String>();
            health.add(EndangeredAnimals.HEALTH_HEALTHY);
            health.add(EndangeredAnimals.HEALTH_ILL);
            health.add(EndangeredAnimals.HEALTH_OKAY);
            List<String> age= new ArrayList<String>();
            age.add(EndangeredAnimals.AGE_ADULT);
            age.add(EndangeredAnimals.AGE_NEWBORN);
            age.add(EndangeredAnimals.AGE_YOUNG);
            model.put("health",health);
            model.put("age",age);
            String typeChosen="endangered";
            model.put("endangered",typeChosen);
            return new ModelAndView(model,"animal-form.hbs");
        },new HandlebarsTemplateEngine());


    }
}
