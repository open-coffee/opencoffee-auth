var selfservice = angular.module('Selfservice');

selfservice.controller('login',

    function($rootScope, $scope, $http, $location) {

        $scope.checkAuthentication(function() {
            if ($rootScope.authenticated) {
                $location.path("/");
            }
        });

        $scope.credentials = {};

        var successfulLogin = function(data){
            $scope.checkAuthentication(function() {
                if ($rootScope.authenticated) {
                    $location.path("/");
                    $scope.error = false;
                } else {
                    $location.path("/login");
                    $scope.error = true;
                }
            });
        };

        var unsuccessfulLogin = function(data){
            $location.path("/login");
            $scope.error = true;
            $rootScope.authenticated = false;
        };

        $scope.login = function() {
            $http({
                method: 'POST',
                url: 'login',
                headers : {
                    "content-type" : "application/x-www-form-urlencoded"
                },
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data:$scope.credentials}
            )
                .success(function(data){
                    successfulLogin(data);
                })
                .error(function(data) {
                    unsuccessfulLogin(data);
                });
        };
    });

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'login/login.html',
        controller: 'login'
    });
}]);
