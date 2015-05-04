var selfservice = angular.module('Selfservice');

selfservice.controller('logout',['$rootScope', '$scope', '$http',
    function($rootScope, $scope, $http) {

        $scope.logout = function() {
            $http.post('/logout', {}).success(function() {
                $rootScope.authenticated = false;
            }).error(function(data) {
                $rootScope.authenticated = false;
            });
        };

        $scope.logout();
    }
]);

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/logout', {
        templateUrl: 'authentication/logout/logout.html',
        controller: 'logout'
    });
}]);
