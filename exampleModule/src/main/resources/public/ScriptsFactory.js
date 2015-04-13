angular.module('Selfservice').factory('scriptsFactory', ['$http', '$q', function ($http, $q) {
    var scriptsEndpoint = "http://localhost:8090/scripts"
    var scriptsFactory = {};
    scriptsFactory.scripts = [];
    var scriptsLoaded = $q.defer()


    function success(data) {
        if (data._embedded != undefined) {
            scriptsFactory.scripts = data._embedded.scripts;
        }
        scriptsLoaded.resolve(scriptsFactory.scripts);
    }

    function error(data, status, headers, config) {
    }

    scriptsFactory.requestScripts = function requestScripts() {
        $http.get(scriptsEndpoint)
            .success(function (data) {
                success(data);
            })
            .error(function (data, status, headers, config) {
                error(data, status, headers, config);
            });
    };

    scriptsFactory.getScripts = function getScripts() {
        return scriptsLoaded.promise;
    };

    scriptsFactory.requestScripts();

    return scriptsFactory;
}]);