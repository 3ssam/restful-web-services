package mo.springrestful.controllers;

import mo.springrestful.models.Name;
import mo.springrestful.models.PersonVersion1;
import mo.springrestful.models.PersonVersion2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    //URL Versioning
    @RequestMapping("/person/v1")
    public PersonVersion1 personVersion1(){
        return new PersonVersion1("Mohamed Essam");
    }

    @RequestMapping("/person/v2")
    public PersonVersion2 personVersion2(){
        return new PersonVersion2(new Name("Mohamed", "Essam"));
    }

    //Request Parameter Versioning
    @RequestMapping(value = "/person/param",params = "Version=1")
    public PersonVersion1 paramVersion1(){
        return new PersonVersion1("Mohamed Essam");
    }

    @RequestMapping(value = "/person/param",params = "Version=2")
    public PersonVersion2 paramVersion2(){
        return new PersonVersion2(new Name("Mohamed", "Essam"));
    }


    //Header Versioning
    @RequestMapping(value = "/person/header",headers = "X-API-VERSION=1")
    public PersonVersion1 headerVersion1(){
        return new PersonVersion1("Mohamed Essam");
    }

    @RequestMapping(value = "/person/header",headers = "X-API-VERSION=2")
    public PersonVersion2 headerVersion2(){
        return new PersonVersion2(new Name("Mohamed", "Essam"));
    }


    //Mime Versioning
    @RequestMapping(value = "/person/produces",produces = "application/v1+json")
    public PersonVersion1 producesVersion1(){
        return new PersonVersion1("Mohamed Essam");
    }

    @RequestMapping(value = "/person/produces",produces = "application/v2+json")
    public PersonVersion2 producesVersion2(){
        return new PersonVersion2(new Name("Mohamed", "Essam"));
    }

}
