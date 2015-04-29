package de.synyx.selfservice.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by klem on 14.04.15.
 */
@RestController
public class ExampleController {

    @RequestMapping(value = "/config")
    public String getViewConfig(){
        String ret = "";
        try {
             ret = new FileReader("viewConfig.json").toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
