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
        String ret = "{\n" +
                "  \"category\":\"irgendwas\",\n" +
                "  \"actions\": [\n" +
                "    {\n" +
                "      \"name\": \"doStuff\",\n" +
                "      \"endpoint\": \"/doStuff\",\n" +
                "      \"accept\" : \"JSON\",\n" +
                "      \"params\": [\n" +
                "        {\n" +
                "          \"name\" : \"selectedValues\",\n" +
                "          \"options\":[\"hello\", \"bye\", \"test\", \"tada\"]\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"radioValues\",\n" +
                "          \"options\":[\"eins\", \"zwei\", \"drei\"]\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"anyNumber\",\n" +
                "          \"type\": \"NUMBER\",\n" +
                "          \"limits\":{\n" +
                "            \"min\": 1,\n" +
                "            \"max\": 99\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"doOtherStuff\",\n" +
                "      \"endpoint\": \"/doOtherStuff\",\n" +
                "      \"accept\": \"XML\",\n" +
                "      \"params\": [\n" +
                "        {\n" +
                "          \"name\": \"anEmail\",\n" +
                "          \"type\": \"EMAIL\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"aDate\",\n" +
                "          \"type\": \"DATE\",\n" +
                "          \"limits\": {\n" +
                "            \"min\": \"2014-01-01\",\n" +
                "            \"max\": \"2015-06-01\"\n" +
                "          }\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        try {
             ret = new FileReader("viewConfig.json").toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return ret;
    }

}
