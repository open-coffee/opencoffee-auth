var selfservice = angular.module('Selfservice');

selfservice.controller('HomeController', function ($scope, $http, modulesFactory) {

    $scope.modules = [];

    var modulesPromise = modulesFactory.getModules();

    modulesPromise.then(function(modules){
        $scope.modules = modules;
    });
});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
       templateUrl: '/home/home.html',
        controller: 'HomeController'
    });
}]);