var selfservice = angular.module('Selfservice', ['ngRoute', 'ngTouch', 'ui.bootstrap']);

selfservice.config(['$routeProvider', '$sceDelegateProvider', '$controllerProvider', '$provide', '$compileProvider',
    function($routeProvider,$sceDelegateProvider, $controllerProvider, $provide, $compileProvider) {
        $routeProvider.otherwise({redirectTo: '/'});
        $sceDelegateProvider.resourceUrlWhitelist([
            // Allow same origin resource loads.
            'self',
            // Allow loading from our assets domain.  Notice the difference between * and **.
            'http://localhost:8090/**'
        ]);

        selfservice._controller = selfservice.controller;
        selfservice._service = selfservice.service;
        selfservice._factory = selfservice.factory;
        selfservice._value = selfservice.value;
        selfservice._directive = selfservice.directive;
        selfservice.addRoute = function(callback){
            callback($routeProvider);
        };

        // Provider-based controller.
        selfservice.controller = function( name, constructor ) {

            $controllerProvider.register( name, constructor );
            return( this );

        };

        // Provider-based service.
        selfservice.service = function( name, constructor ) {

            $provide.service( name, constructor );
            return( this );

        };

        // Provider-based factory.
        selfservice.factory = function( name, factory ) {

            $provide.factory( name, factory );
            return( this );

        };

        // Provider-based value.
        selfservice.value = function( name, value ) {

            $provide.value( name, value );
            return( this );

        };

        // Provider-based directive.
        selfservice.directive = function( name, factory ) {

            $compileProvider.directive( name, factory );
            return( this );

        };
    }
]);



