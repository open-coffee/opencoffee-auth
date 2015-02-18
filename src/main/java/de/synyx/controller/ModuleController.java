/*package de.synyx.controller;

import com.google.common.collect.Lists;
import de.synyx.enity.Module;
import de.synyx.exception.AlreadyExistsException;
import de.synyx.repository.ModuleRepository;
import de.synyx.resource.ModuleResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by klem on 18.02.15.
 */
/*
@RestController
@RequestMapping("/modules")
public class ModuleController {

    @Autowired
    private ModuleRepository moduleRepository;

    @RequestMapping(method = RequestMethod.GET, produces = "application/hal+json")
    @ResponseBody
    public HttpEntity<List<ModuleResource>> getModules(){
        ArrayList<Module> modules = Lists.newArrayList(moduleRepository.findAll());
        ArrayList<ModuleResource> moduleResources = new ArrayList<ModuleResource>();

        for(Module module : modules) {
            ModuleResource moduleResource = new ModuleResource(module.getUri(), module.getName());
            moduleResource.add(linkTo(ModuleController.class).slash(module).withSelfRel());
            moduleResources.add(moduleResource);
        }
        return new ResponseEntity<List<ModuleResource>>(moduleResources, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<ModuleResource> getModuleWithId(@PathVariable("id") long id){
        Module module = moduleRepository.findOne(id);
        ModuleResource moduleResource = new ModuleResource(module.getUri(), module.getName());
        moduleResource.add(linkTo(ModuleController.class).slash(module).withSelfRel());
        return new ResponseEntity<ModuleResource>(moduleResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addModule(@RequestBody @Valid Module module){
        try {
            moduleRepository.save(module);
        }catch (DataIntegrityViolationException e){
            throw new AlreadyExistsException("Ein Modul mit der Adresse: \"" + module.getUri() + "\" existiert bereits");
        }
    }

}
*/