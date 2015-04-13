var selfservice = angular.module('Selfservice');
var hostname = 'http://localhost:8090';

var $routeProvider = selfservice.getRouteProvider();

$routeProvider.when('/Example', {
    templateUrl: hostname + '/ExampleModule.html',
    controller: 'ExampleController'
});

selfservice.addTrustedResourceURL(hostname + '/**');

selfservice.controller('ExampleController', function ($scope, scriptsFactory) {
    $scope.scripts = [];

    var scriptsPromise = scriptsFactory.getScripts();

    scriptsPromise.then(function(scripts){
       $scope.scripts = scripts;
    });
});




