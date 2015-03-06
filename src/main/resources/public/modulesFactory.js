angular.module('Selfservice').factory('modulesFactory', ['$http', '$q', function ($http, $q) {
    var modulesEndpoint = "/modules"
    var modulesFactory = {};
    modulesFactory.modules = [];
    var modulesLoaded = $q.defer()


    function success(data) {
        if (data._embedded != undefined) {
            modulesFactory.modules = data._embedded.modules;
        }
        modulesLoaded.resolve(modulesFactory.modules);
    }

    modulesFactory.requestModules = function requestModules() {
        $http.get(modulesEndpoint)
            .success(function (data) {
                success(data);
            });
    };

    modulesFactory.getModules = function getModules() {
        return modulesLoaded.promise;
    };

    modulesFactory.requestModules();

    return modulesFactory;
}]);