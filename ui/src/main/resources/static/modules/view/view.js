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

selfservice.controller('moduleView', ['$scope', '$routeParams', 'modules', '$location' , '$http',
    function($scope, $routeParams, modules, $location, $http){
        $scope.moduleName = "";
        $scope.moduleNotFound = false;
        $scope.module = null;

        $scope.processAction = function(action){
            params = {};
            for(var i = 0; i < action.params.length; i++){
                params[action.params[i].name] = action.params[i].value;
            }
            alert("POST \"" + JSON.stringify(params) + "\" to " + action.endpoint);
        };

        var initModuleView = function () {
            modules.getModuleView($scope.module).then(function () {
                for (var actionIndex = 0; actionIndex < $scope.module.moduleView.actions.length; actionIndex++) {
                    var action = $scope.module.moduleView.actions[actionIndex];
                    action.radios = [];
                    action.selects = [];
                    action.inputs = [];
                    for (var paramIndex = 0; paramIndex < action.params.length; paramIndex++) {
                        var parameter = action.params[paramIndex];
                        var options = parameter.options;
                        if (options && options.length > 0) {
                            if (options.length <= 3) {
                                action.radios.push(parameter);
                            } else {
                                action.selects.push(parameter);
                                parameter.value = parameter.options[0];
                            }
                        }else{
                            action.inputs.push(parameter);
                        }
                    }
                }
            })
        };

        $scope.hasMax = function (param) {
            return param.limits && angular.isDefined(param.limits.max);
        };

        $scope.hasMin = function (param) {
            return param.limits && angular.isDefined(param.limits.min);
        };

        $scope.deleteModule = function(){
            modules.remove($scope.module);
            $location.path('modules/add');
        };

        $scope.$on('$routeChangeSuccess', function () {
            $scope.moduleName = $routeParams.name || '';
            modules.findByName($scope.moduleName)
                .then(function(modulesWithName){
                    if(modulesWithName.length > 0 ){
                        $scope.moduleNotFound = false;
                        $scope.module = modulesWithName[0];
                        initModuleView();
                    }else{
                        $scope.moduleNotFound = true;
                        $scope.module = null;
                    }
                });
        });
    }
]);