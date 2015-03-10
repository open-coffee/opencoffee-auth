var selfservice = angular.module('Selfservice');

selfservice.controller('logout',

    function($rootScope, $scope, $http, $location) {

        $scope.logout = function() {
            $http.post('/logout', {}).success(function() {
                $rootScope.authenticated = false;
                $rootScope.headers = {};
            }).error(function(data) {
                $rootScope.authenticated = false;
                $rootScope.headers = {};
            });
        }

        $scope.logout();
    });

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/logout', {
        templateUrl: 'logout/logout.html',
        controller: 'logout'
    });
}]);
