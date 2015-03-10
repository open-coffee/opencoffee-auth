var selfservice = angular.module('Selfservice');

selfservice.service('selfserviceHttpInterceptor',['$location', '$q', function ($location ,$q) {
    return {
        'responseError': function(rejection) {
            if(rejection.status == 401) {
                $location.path("/login");
            }
            return $q.reject(rejection);
        }
    };
}]);