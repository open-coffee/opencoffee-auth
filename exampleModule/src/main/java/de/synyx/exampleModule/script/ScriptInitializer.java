package de.synyx.exampleModule.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by Yannic on 27.02.2015.
 */
@Configuration
public class ScriptInitializer {
    @Autowired
    ScriptRepository scriptRepository;

    @PostConstruct
    public void init(){
        Script script;
        script = new Script();
        script.setSrc("/ExampleModule.js");
        scriptRepository.save(script);
        script = new Script();
        script.setSrc("/ScriptsFactory.js");
        scriptRepository.save(script);
    }
}
