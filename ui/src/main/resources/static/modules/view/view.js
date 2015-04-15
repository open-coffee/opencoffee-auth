var selfservice = angular.module('Selfservice');

selfservice.config(['$routeProvider', function($routeProvider){
    var routeConfig = {
        templateUrl: '/modules/view/view.html',
        controller: 'moduleView'
    };
    $routeProvider
        .when('/modules/view/:name', routeConfig);
}])

selfservice.controller('moduleView', function($scope, $resource, $http, $routeParams){
    var Module = $resource('/selfserviceModules/:id');

    $scope.moduleName = "";
    $scope.moduleNotFound = false;

    $scope.processAction = function(action){
        console.log("TODO");
    }

    $scope.$on('$routeChangeSuccess', function () {
        $scope.moduleName = $routeParams.name || '';
        Module.get().$promise
            .then(function(modules){
                var modulesList = modules._embedded.selfserviceModules;
                for(var i = 0; i < modulesList.length; i ++){
                    if(modulesList[i].name == $scope.moduleName){
                        return modulesList[i];
                    }
                }
                return null;
            }).then(function(module){
                if(!module){
                    $scope.moduleNotFound=true;
                }else{
                    $scope.moduleNotFound=false;
                    $scope.module=module;
                }
            });

    });
})