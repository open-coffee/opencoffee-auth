var selfservice = angular.module('Selfservice');

selfservice.controller('modules', function($scope){

});

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/modules', {
        templateUrl: '/modules/modules.html',
        controller: 'modules'
    });
}]);