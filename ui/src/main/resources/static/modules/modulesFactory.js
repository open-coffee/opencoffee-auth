angular.module('Selfservice').factory('modules', ['$resource', '$filter', 'HALResource',
    function ($resource, $filter, HALResource) {
        var modulesEndpoint = '/selfserviceModules/:id';

        var Module = $resource(modulesEndpoint);

        var modulesList = [];

        var modules = {
            get: function(){
                return modulesList;
            },
            add: function(module){
                return Module.save(module).$promise.then(function(savedModule){
                    modulesList.push(savedModule);
                });
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