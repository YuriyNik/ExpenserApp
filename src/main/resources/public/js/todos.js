(function (){

  var app = angular.module("ExpenceApplication");

  var TodoController = function($scope, $http, host) {

    console.log('Todos started');

    $scope.data = {
        userTypes: []
       };

    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Todos userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/todo').then(function(response) {
         $scope.todos = response.data;
    });

  };

  app.controller("TodoController", TodoController);

})();