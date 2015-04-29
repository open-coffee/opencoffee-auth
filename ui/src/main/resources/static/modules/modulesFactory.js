angular.module('Selfservice').factory('modules', ['$resource', '$filter', 'HALResource', '$http',
    function ($resource, $filter, HALResource, $http) {
        var modulesEndpoint = '/selfserviceModules/:id';

        var Module = $resource(modulesEndpoint);

        var modulesList = [];

        var modules = {
            get: function(){
                return modulesList;
            },
            add: function(module){
               return Module.save(module).$promise
                   .then(function(savedModule){
                        return $http.get("/api/proxy/" + savedModule.name + "/viewConfig.json")
                            .then(function(response){
                                savedModule.moduleView = response.data;
                                return Module.save(savedModule).$promise;
                            });
                    })
                   .then(function(savedModule){
                       modulesList.push(savedModule);
                   })
            },
            remove: function(module){
                return Module.remove(module).$promise.then(function () {
                    var index = modulesList.indexOf(module);
                    modulesList.splice(index, 1);
                })
            },
            findByName: function(name){
                return modulesList.promise.then(function(){
                    return $filter('findByValue')(modulesList, "name", name);
                });
            },
            fetch: function(){
                modulesList.promise = Module.get().$promise
                    .then(function(response){
                        modulesList.splice(0,modulesList.length);
                        modulesList.push.apply(modulesList, HALResource.getContent(response));
                    });
            }
        };

        modules.fetch();

        return modules;
    }
]);