var selfservice = angular.module('Selfservice');

selfservice.directive('autofocus', ['$timeout',
    function($timeout) {
        return {
            restrict: 'A',
            link : function($scope, $element) {
                $timeout(function() {
                    $element[0].focus();
                });
            }
        }
    }
]);