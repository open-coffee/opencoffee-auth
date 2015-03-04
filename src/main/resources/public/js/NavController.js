angular.module('Selfservice').controller('NavController', function ($scope, $location, $route, $http, modulesFactory) {

    $scope.modules = [];
    $scope.scripts = [];

    var modulesPromise = modulesFactory.getModules();

    modulesPromise.then(function(modules){
        $scope.modules = modules;
        angular.forEach(modules, function(value, key) {
            angular.forEach(value.scripts, function(value, key) {
                var script = {'src':value.endpoint};
                this.push(script)
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