angular.module('Selfservice').factory('modulesFactory', ['$http', '$q', 'tokenFactory', function ($http, $q, tokenFactory) {
    var modulesEndpoint = "http://localhost:9000/modules"
    var modulesFactory = {};
    modulesFactory.modules = [];
    var modulesLoaded = $q.defer()
    var authToken = {};


    function success(data) {
        if (data._embedded != undefined) {
            modulesFactory.modules = data._embedded.modules;
        }
        modulesLoaded.resolve(modulesFactory.modules);
    }

    var tokenPromise = tokenFactory.getToken();
    tokenPromise.then(
        function (token) {
            authToken = token
            modulesFactory.requestModules();
        }
    );

    modulesFactory.requestModules = function requestModules() {
        $http({
            url: modulesEndpoint,
            method: 'GET',
            headers: {
                'X-Auth-Token': authToken.token
            }
        }).success(function (data) {
            success(data);
        });
    };


    modulesFactory.getModules = function getModules() {
        return modulesLoaded.promise;
    };

    return modulesFactory;
}]);