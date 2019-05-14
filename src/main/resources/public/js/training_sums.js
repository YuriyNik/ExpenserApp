(function (){

  var app = angular.module("ExpenceApplication");

  var TrainingSumsController = function($scope, $http, host) {

    console.log('Report started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() + 1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.data = {
        userTypes: []
       };
    function sortByYearDesc(){
            return function(a,b){
                   return b.year - a.year;
            };
        };

    console.log('TrainingSumsController started currmonth='+$scope.currmonth+';curryear='+$scope.curryear);
    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Report userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/activitySumAll/').
    then(function(response) {
         $scope.activitySummarys = response.data;
         $scope.activitySummarys.sort(sortByYearDesc());
    });

    $scope.showForYear = function(year) {
        console.log('TrainingSumsController.showForYear for year='+year);
        $http.get(host+'/activitySum/'+year).
        then(function(response) {
        $scope.activitySummarys = response.data;
        $scope.activitySummarys.sort(sortByYearDesc());
        console.log('activitySummarys reloaded');
    });
    };

    $scope.showForAll = function() {
        console.log('TrainingSumsController.showForAll');
        $http.get(host+'/activitySumAll').
        then(function(response) {
        $scope.activitySummarys = response.data;
        $scope.activitySummarys.sort(sortByYearDesc());
        console.log('activitySummarys showForAll reloaded');
    });
    };

   $scope.showForYearByMonth = function(year) {
        console.log('TrainingSumsController.showForYearByMonth for year='+year);
        $http.get(host+'/activitySumByMonth/'+year).
        then(function(response) {
        $scope.activitySummarys = response.data;
        console.log('activitySummarys showForYearByMonth reloaded');
    });
    };

  $scope.getDistanceSum = function(){
        if (typeof $scope.activitySummarys !== "undefined") {
        var total = 0;
        for(var i = 0; i < $scope.activitySummarys.length; i++){
            var distanceSum = $scope.activitySummarys[i].totalDistance;
            total += distanceSum;
        }
        return total;
    }
    }

  };

  app.controller("TrainingSumsController", TrainingSumsController);

})();