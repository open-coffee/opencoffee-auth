package de.synyx.selfservice.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by klem on 14.04.15.
 */
@RestController
public class ExampleController {
    @RequestMapping(value = "/")
    public String sayHello(){
        return "hello";
    }

}
