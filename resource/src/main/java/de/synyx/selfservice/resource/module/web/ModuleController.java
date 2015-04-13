package de.synyx.selfservice.resource.module.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.synyx.selfservice.resource.ResourceApplication;
import de.synyx.selfservice.resource.module.Module;
import de.synyx.selfservice.resource.module.ModuleRepository;
import de.synyx.selfservice.resource.script.Script;
import de.synyx.selfservice.resource.script.web.ScriptResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by klem on 12.03.15.
 */
@RestController
@RequestMapping("/modules")
public class ModuleController
{
    @Autowired
    private ModuleRepository moduleRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    public HttpEntity<Resources<Module>> getModules() {
        Iterable<Module> moduleIterable = moduleRepository.findAll();
        ArrayList<Module> modules = new ArrayList<>();
        for(Module module: moduleIterable){
            module.add(linkTo(methodOn(ModuleController.class).getModule(module.getModuleId())).withSelfRel());
            modules.add(module);
        }
        Resources<Module> resources = new Resources<Module>(modules);
        resources.add(linkTo(methodOn(ModuleController.class).getModules()).withSelfRel());

        return new HttpEntity<Resources<Module>>(resources);
    }

    @RequestMapping(value = "{moduleId}", method = RequestMethod.GET, produces = "application/hal+json")
    public HttpEntity<Module> getModule(@PathVariable Long moduleId){
        Module module = moduleRepository.findOne(moduleId);
        module.add(linkTo(methodOn(ModuleController.class).getModule(moduleId)).withSelfRel());
        return new HttpEntity<Module>(module);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/hal+json")
    public HttpEntity<Void> addModule(@RequestBody Module module, @CookieValue("XSRF-TOKEN") String xsrfToken,
                                      @RequestHeader("Authorization") String auth) throws URISyntaxException {

        module.setScripts(getScriptsOfPostedModule(module, auth));
        moduleRepository.save(module);
        Link modulesLink = linkTo(methodOn(ModuleController.class).getModules()).withSelfRel();
        URI modulesUri = new URI(modulesLink.getHref());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(modulesUri);

        return new ResponseEntity<Void>(null,headers, HttpStatus.CREATED);
    }

    private List<Script> getScriptsOfPostedModule(Module module, String auth){
        RestTemplate restTemplate = restTemplate();

        HttpHeaders httpRequestHeaders = new HttpHeaders();
        httpRequestHeaders.set("Authorization", auth);

        HttpEntity<?> entity = new HttpEntity<Object>(httpRequestHeaders);
        String url = module.getUri() + ResourceApplication.MODULES_SCRIPTS_ENDPOINT;

        ResponseEntity<ScriptResources> scriptResources = restTemplate.exchange(url,
                HttpMethod.GET, entity, ScriptResources.class);

        return new ArrayList<>(scriptResources.getBody().getContent());
    }

    @Bean
    public RestTemplate restTemplate() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jackson2HalModule());

        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        return new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
    }
}
