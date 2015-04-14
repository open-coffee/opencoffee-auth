var selfservice = angular.module('Selfservice');

selfservice.controller('modules', function($scope, $location, tokenFactory, $http){
    var modulesEndpoint = "/selfserviceModules"
    var authToken = {};
    $scope.module = {};

    var tokenPromise = tokenFactory.getToken();
    tokenPromise.then(
        function (token) {
            authToken = token;
        }
    );

    $scope.addModule = function requestModules() {
        $http({
            url: modulesEndpoint,
            method: 'POST',
            headers: {
                'X-Auth-Token': authToken.token
            },
            data: $scope.module
        }).success(function (data) {

        });
    };
});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/modules', {
        templateUrl: '/modules/modules.html',
        controller: 'modules'
    });

    $routeProvider.when('/modules/add', {
        templateUrl: '/modules/addModules.html',
        controller: 'modules'
    });
}]);