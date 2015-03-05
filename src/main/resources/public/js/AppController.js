var selfservice = angular.module('Selfservice');

selfservice.controller('AppController', function ($scope, $location, $route, $http, $sce, modulesFactory) {

    $scope.modules = [];
    $scope.scripts = [];

    var modulesPromise = modulesFactory.getModules();

    modulesPromise.then(function(modules){
        $scope.modules = modules;
        angular.forEach(modules, function(value, key) {
            angular.forEach(value.scripts, function(value, key) {
                var script = {};
                script.src =  $sce.trustAsResourceUrl(value.src);
                this.push(script);
            }, this);
        },$scope.scripts);


    });

    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };

    $scope.changeView = function(view){
        $scope.navCollapsed = true;
        if(!angular.equals($location.path(), view)){
            $location.path(view); // path not hash
        }
        $route.reload();
    };
});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: '/home/home.html',
        controller: 'AppController'
    });
}]);