package mo.springrestful.controllers;

import mo.springrestful.models.HelloWorldBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    //@RequestMapping(method=RequestMethod.GET,path="/hello-world")
    @GetMapping("/hello-world")
    public String helloworld() {
        //System.out.println("Hello World");
        return "Hello World";
    }


    @GetMapping("/hello-world-bean")
    public HelloWorldBean helloWorldBean() {
        //System.out.println("Hello World");
        return new HelloWorldBean("Hello World");
    }


}