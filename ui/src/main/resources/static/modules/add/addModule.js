var selfservice = angular.module('Selfservice');

selfservice.controller('addModule', ['$scope', 'modules', '$location',
    function($scope, modules, $location){
        var portSpecified = function(){
            return $scope.module.port != $scope.protocols[0].port
                && $scope.module.port != $scope.protocols[1].port;
        };

        $scope.protocols = [
            {
                name: "https",
                text: "https://",
                port: 443
            },{
                name: "http",
                text: "http://",
                port: 80
            }
        ];

        $scope.module = {
            protocol : $scope.protocols[0],
            port : $scope.protocols[0].port
        };

        $scope.addModule = function requestModules() {
            $scope.module.protocol = $scope.module.protocol.name;
            modules.add($scope.module).then(function(module){
                $location.path("/view/" + module.name);
            });
        };

        $scope.portIsDefaultProtocolPort = function(){
            return $scope.module.port == $scope.module.protocol.port;
        };

        $scope.updatePort = function(){
            if(!portSpecified()){
                $scope.module.port = $scope.module.protocol.port;
            }
        };

        $scope.fileChanged = function(element) {

            var config = element.files[0];
            var reader = new FileReader();
            reader.onload = function(e) {
                $scope.$apply(function() {
                    var yamlContent = e.target.result;
                    var YAML = window.YAML
                        , json
                        , data
                        , yml
                    ;
                    $scope.module = YAML.parse(yamlContent);
                });
            };
            reader.readAsText(config);
        };
    }
]);

selfservice.config(['$routeProvider', function($routeProvider) {
    $routeProvider.when('/modules/add', {
        templateUrl: '/modules/add/addModule.html',
        controller: 'addModule'
    });
}]);