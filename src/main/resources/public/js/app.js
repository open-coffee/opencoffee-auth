var selfservice = angular.module('Selfservice', ['ngRoute', 'ngTouch', 'ui.bootstrap']);

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/'});
}]);
