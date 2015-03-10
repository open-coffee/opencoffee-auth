var selfservice = angular.module('Selfservice');

selfservice.controller('login',

    function ($rootScope, $scope, $http, $location) {

        var authenticate = function (credentials, callback) {

            $rootScope.headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":"
                + credentials.password)
            } : {};

            $http.get('user', {
                headers: $rootScope.headers
            }).success(function (data) {
                $rootScope.authenticated = true;
                $scope.$parent.user = data;
                $location.path($scope.$parent.lastPath);
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });
        }

        authenticate();

        $scope.credentials = {};
        $scope.login = function () {
            authenticate($scope.credentials, function () {
                $scope.error = true;
            })
        };
    });

selfservice.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'authentication/login/login.html',
        controller: 'login'
    });
}]);
