package de.synyx.selfservice.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by klem on 14.04.15.
 */
@RestController
public class ExampleController {

    @RequestMapping(value = "/hello")
    public Hello sayHello(){
        return new Hello("hello");
    }

    class Hello {
        private String huhu;

        public Hello(String huhu) {
            this.huhu = huhu;
        }

        public String getHuhu() {
            return huhu;
        }
    }
}
