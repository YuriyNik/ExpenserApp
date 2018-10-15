(function (){

  var app = angular.module("ExpenceApplication");

  var TrainingController = function($scope, $http, host) {

   console.log('Training started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() +1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.data = {
        userTypes: []
       };

    console.log('Training started currmonth='+$scope.currmonth+';curryear='+$scope.curryear);
    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Training userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });


    //$http.get(host+'/activity/'+$scope.currmonth+'/'+$scope.curryear+'/null',config).

     $http.get(host+'/activity/').

    then(function(response) {
         $scope.activitySummarys = response.data;
    });


  };

  app.controller("TrainingController", TrainingController);

})();