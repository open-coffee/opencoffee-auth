var selfservice = angular.module('Selfservice');

selfservice.controller('navigation', function ($rootScope, $scope, $location, $route, $http, $sce, modulesFactory) {

    $scope.modules = [];
    $scope.scripts = [];
    $scope.user = {};
    $rootScope.token =  {};
    $scope.lastPath = "";

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

    $scope.isLoginPage = function(){
        return $location.path() == "/login"
    }

    $scope.isLogoutPage = function(){
        return $location.path() == "/logout"
    }

    $rootScope.$on('$routeChangeStart', function () {
        if (!$scope.isLoginPage() && !$scope.isLogoutPage() && !$rootScope.authenticated) {
            $scope.lastPath = $location.path();
            $location.path('/login');
        }
    });
});