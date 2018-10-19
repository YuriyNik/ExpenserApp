(function (){

  var app = angular.module("ExpenceApplication");

  var TrainingController = function($scope, $http, host) {

   console.log('Training started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() +1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.activity = {
    };
        /* LocalDateTime date,
        String type,
        int duration,
        Double distance,
        int pulseAve,
        int pulseMax,
        String location,
        String notes,
        String weather,
        Double speedAve,
        Double paceAve,*/
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

    $http.get(host+'/activity/').then(function(response) {
         $scope.activitySummarys = response.data;
    });
//post new
    $scope.addActivity = function(){
         console.log('addActivity');
         console.log($scope.activity);
         if (typeof $scope.activity.type == "undefined") $scope.activity.type='Роллеры';
                   $http.post(host+'/activity',$scope.activity).
                   then(function(response) {
                       console.log('success');
                       console.log(response);
                       $scope.activitySummarys.push(response.data);
                       $scope.activity = {};
                   }, function (response) {
                       console.log('error');
                       console.log(response);
                       console.log($scope.activity);

                   });
    };
//delete selected
    $scope.deleteActivity = function(){

    };
//update exisiting via PUT
    $scope.updateActivity = function(){

    };

    $scope.showByType = function(type) {
      console.log('showByType for type='+type);
      $http.get(host+'/activity/'+type).
                       then(function(response) {
                       $scope.expences = response.data;
                       console.log('activity reloaded by type');
                       }, function (response) {
                           console.log('error!');
                           console.log(response);
                       });
    };


  };

  app.controller("TrainingController", TrainingController);

})();