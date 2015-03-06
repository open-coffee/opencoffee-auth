var selfservice = angular.module('Selfservice');

selfservice.controller('login',

    function($rootScope, $scope, $http, $location) {

        $scope.checkAuthentication(function() {
            if ($rootScope.authenticated) {
                $location.path("/");
            }
        });

        $scope.credentials = {};
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
            ).success(function(data) {
                    $scope.checkAuthentication(function() {
                    if ($rootScope.authenticated) {
                        $location.path("/");
                        $scope.error = false;
                    } else {
                        $location.path("/login");
                        $scope.error = true;
                    }
                });
            }).error(function(data) {
                $location.path("/login");
                $scope.error = true;
                $rootScope.authenticated = false;
            })
        };

        $scope.logout = function() {
            $http.post('logout', {}).success(function() {
                $rootScope.authenticated = false;
                $location.path("/");
            }).error(function(data) {
                $rootScope.authenticated = false;
            });
        }
    });

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'login/login.html',
        controller: 'login'
    });
}]);
