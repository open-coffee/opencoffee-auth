var selfservice = angular.module('Selfservice', ['ngRoute', 'ngTouch', 'ui.bootstrap']);

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.otherwise({redirectTo: '/'});
}]);

selfservice.config(function($sceDelegateProvider) {
    $sceDelegateProvider.resourceUrlWhitelist([
        // Allow same origin resource loads.
        'self',
        // Allow loading from our assets domain.  Notice the difference between * and **.
        'http://localhost:8090/**'
    ])
});