var selfservice = angular.module('Selfservice');

selfservice.controller('exampleModule', function($scope, $location, $http){

    $scope.greeting = "";

    $http.get("/api/proxy/example/hello")
        .then(function(hello){
            $scope.greeting = hello.data.huhu;
        });
});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/modules/example', {
        templateUrl: '/modules/example/exampleModule.html',
        controller: 'exampleModule'
    });
}]);