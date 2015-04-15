var selfservice = angular.module('Selfservice');

selfservice.controller('addModule', function($scope, $location, $http){
    var modulesEndpoint = "/selfserviceModules"

    $scope.module = {};

    $scope.addModule = function requestModules() {
        $http.post(modulesEndpoint, $scope.module);
    };

    $scope.fileChanged = function(element) {

        var config = element.files[0];
        var reader = new FileReader();
        reader.onload = function(e) {
            $scope.$apply(function() {
                var yamlContent = e.target.result;
                var YAML = window.YAML
                    , json
                    , data
                    , yml
                ;
                $scope.module = YAML.parse(yamlContent);
            });
        };
        reader.readAsText(config);
    };
});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/modules/add', {
        templateUrl: '/modules/add/addModule.html',
        controller: 'addModule'
    });
}]);