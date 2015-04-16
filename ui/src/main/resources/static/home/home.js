var selfservice = angular.module('Selfservice');

selfservice.controller('home', ['$scope',
    function($scope){

    }
]);

selfservice.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: '/home/home.html',
            controller: 'home'
        });
    }
]);