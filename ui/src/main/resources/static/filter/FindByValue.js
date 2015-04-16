var selfservice = angular.module('Selfservice');
selfservice.filter('findByValue',
    function(){
        return function(inputArray, key, value){
            var result = [];
            for(var i = 0;i<inputArray.length; i++){
                if(inputArray[i][key] == value){
                    result.push(inputArray[i]);
                }
            }
            return result;
        }
    }
);

selfservice.filter('findByValueInverse',
    function(){
        return function(inputArray, key, value){
            var result = [];
            for(var i = 0;i<inputArray.length; i++){
                if(inputArray[i][key] != value){
                    result.push(inputArray[i]);
                }
            }
            return result;
        }
    }
);