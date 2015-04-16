var selfservice = angular.module('Selfservice');

selfservice.controller('login',['$rootScope', '$scope', '$location', 'authService',
    function ($rootScope, $scope, $location, authService) {
        $scope.credentials = {};

        var redirectIfLoggedIn = function (){
            authService.isAuthenticated(function (username) {
                $rootScope.user = username;
                if($rootScope.authenticated){
                    $location.path($scope.$parent.lastPath);
                }
            })
        }

        redirectIfLoggedIn();
    }
]);

selfservice.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'authentication/login/login.html',
        controller: 'login'
    });
}]);
