angular.module('Selfservice').factory('modulesFactory', ['$http', '$q',function($http, $q){
    var modulesEndpoint = "/modules"
    var modulesFactory = {};
    modulesFactory.modules = [];
    var deferred = $q.defer()


    function success(data){
        var id = 0;
        if(data._embedded != undefined) {
            modulesFactory.modules = data._embedded.modules;
        }
        deferred.resolve(modulesFactory.modules);
    }

    function error(data, status, headers, config){
    }

    modulesFactory.requestModules = function requestModules(){
        $http.get(modulesEndpoint).success(function(data){success(data);}).error(function(data, status, headers, config) { error(data, status, headers, config);});
    };

    modulesFactory.getModules = function getModules(){
        return deferred.promise;
    };

    modulesFactory.requestModules();

    return modulesFactory;
}]);