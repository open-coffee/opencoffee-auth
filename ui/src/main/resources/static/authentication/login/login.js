var selfservice = angular.module('Selfservice');

selfservice.controller('login',

    function ($rootScope, $scope, $http, $location, $window) {
/*
        var authenticate = function (credentials, callback) {

            $rootScope.headers = credentials ? {
                authorization: "Basic "
                + btoa(credentials.username + ":"
                + credentials.password),
                'Content-Type': 'application/x-www-form-urlencoded'
            } : {'Content-Type': 'application/x-www-form-urlencoded'};

            $http({
                method: 'POST',
                url: '/authorize',
                headers: $rootScope.headers,
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: {
                    'user_oauth_approval': 'true',
                    'scope.openid': 'true',
                    'authorize': 'Authorize',
                }
            })

            $http.get('user', {
                headers: $rootScope.headers
            }).success(function (data) {
                $rootScope.authenticated = true;
                $scope.$parent.user = data;
                $location.path($scope.$parent.lastPath);
            }).error(function () {
                $rootScope.authenticated = false;
                callback && callback();
            });

       }
       */
        $http.get('user')
            .success(function (data) {
                $rootScope.authenticated = true;
                $scope.$parent.user = data;
                $location.path("/");
            });
/*
        $scope.credentials = {};
        $scope.login = function () {
            authenticate($scope.credentials, function () {
                $scope.error = true;
            })
            $window.location.href = "http://localhost:8080/login";
        };
        */
    });

selfservice.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/login', {
        templateUrl: 'authentication/login/login.html',
        controller: 'login'
    });
}]);
