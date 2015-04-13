var selvservice = angular.module('Selfservice');
selvservice.factory('authService',['$http', '$rootScope', function($http, $rootScope){
    var USER_ENDPOINT = 'api/user';

    var authService = {
        authenticate : function (credentials, callback) {

            var headers = authService.getAuthenticationHeader(credentials);

            $http.get(USER_ENDPOINT, {
                headers: headers
            }).success(function (data) {
                $rootScope.authenticated = true;
                callback && callback(data.name);
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

        },

        isAuthenticated : function(callback){
            $http.get(USER_ENDPOINT)
                .success(function (data) {
                    if(data.name){
                        $rootScope.authenticated = true;
                        callback && callback(data.name);
                    }else{
                        $rootScope.authenticated = false;
                        callback && callback();
                    }
                }).error(function () {
                    $rootScope.authenticated = false;
                    callback && callback();
                });
        },

        getAuthenticationHeader : function(credentials){
            return credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":"
                + credentials.password)
            } : {};
        },

        logout : function() {
            $http.post('/logout', {}).success(function () {
                $rootScope.authenticated = false;
                $rootScope.headers = {};
                $rootScope.user = '';
            }).error(function () {
                $rootScope.authenticated = false;
                $rootScope.headers = {};
                $rootScope.user = '';
            });
        }
    }
    return authService;
}]);