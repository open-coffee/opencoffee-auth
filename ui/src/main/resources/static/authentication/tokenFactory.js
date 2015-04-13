angular.module('Selfservice').factory('tokenFactory', ['$http', '$q', function ($http, $q) {
    var modulesEndpoint = "/token"
    var tokenFactory = {};
    tokenFactory.token = {};
    var tokenLoaded = $q.defer()


    function resolve(token) {
        if (token != undefined) {
            tokenFactory.token = token;
        }
        tokenLoaded.resolve(tokenFactory.token);
    }

    tokenFactory.requestToken = function requestToken() {
        $http.get('token').success(function (token) {
            resolve(token)
        });
    };


    tokenFactory.getToken = function getToken() {
        return tokenLoaded.promise;
    };

    //tokenFactory.requestToken();

    return tokenFactory;
}]);