var selfservice = angular.module('Selfservice');

selfservice.controller('navigation', function ($rootScope, $scope, $location, $route, $http, $sce, modulesFactory) {

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

    $scope.checkAuthentication = function(callback) {

        $http.get('user').success(function(data) {
            if (data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }).error(function() {
            $rootScope.authenticated = false;
            callback && callback();
        });

    }

    $scope.checkAuthentication(function() {
        if ($rootScope.authenticated) {
            $location.path("/");
        }else{
            $location.path("/login");
        }
    });

    $scope.isLoginPage = function(){
        return $location.path() == "/login"
    }
});