var selfservice = angular.module('Selfservice');
selfservice.factory('HALResource', [
    function(){
        var HALResource = {
            getContent : function(resource){
                if(resource._embedded){
                    for(var key in resource._embedded){
                        return resource._embedded[key];
                    }
                }
                return resource;
            },
            getRelations : function(resource){
                var relations = [];
                if(resource._links){
                    for(var key in resource._links){
                        if(key != "self"){
                            relations.push(resource._links[key]);
                        }
                    }
                }
                return relations;
            }
        };

        return HALResource;
    }
]);