var selfservice = angular.module('Selfservice');

selfservice.config(['$routeProvider',
    function($routeProvider){
        var routeConfig = {
            templateUrl: '/modules/view/view.html',
            controller: 'moduleView'
        };
        $routeProvider
            .when('/view/:name', routeConfig);
    }
])

selfservice.controller('moduleView', ['$scope', '$routeParams', 'modules' ,
    function($scope, $routeParams, modules){
        $scope.moduleName = "";
        $scope.moduleNotFound = false;
        $scope.module = null;

        $scope.processAction = function(action){
            console.log("TODO");
        }

        $scope.$on('$routeChangeSuccess', function () {
            $scope.moduleName = $routeParams.name || '';
            modules.findByName($scope.moduleName)
                .then(function(modulesWithName){
                    if(modulesWithName.length > 0 ){
                        $scope.moduleNotFound = false;
                        $scope.module = modulesWithName[0];
                    }else{
                        $scope.moduleNotFound = true;
                        $scope.module = null;
                    }
                });
        });
    }
])