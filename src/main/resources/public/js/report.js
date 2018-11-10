(function (){

  var app = angular.module("ExpenceApplication");

  var ReportController = function($scope, $http, host) {

    console.log('Report started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() + 1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.data = {
        userTypes: []
       };

    console.log('Report started currmonth='+$scope.currmonth+';curryear='+$scope.curryear);
    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Report userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/reports/'+$scope.currmonth+'/'+$scope.curryear+'/null').
    then(function(response) {
         $scope.expenceSummarys = response.data;
    });

    $http.get(host+'/expenceTypes').
    then(function(response) {
        console.log('Report expenceTypes='+response.data);
        $scope.data.userTypes = response.data;
    });


    $scope.showForMonthYear = function(month,year) {
        $scope.currmonth = month;
        console.log('Report for month='+month+';year='+year);
        $http.get(host+'/reports/'+$scope.currmonth+'/'+year+'/null').
        then(function(response) {
        $scope.expenceSummarys = response.data;
        console.log('expenceSummary reloaded');
    });
    };
   $scope.showForYearType = function(type,year) {
        console.log('Report for type='+type+';year='+year);
        $http.get(host+'/reports/'+$scope.currmonth+'/'+year+'/'+type).
        then(function(response) {
        $scope.expenceSummarys = response.data;
        console.log('expenceSummary showForYearType reloaded');
    });
    };

  $scope.getTotalSummary = function(){
        if (typeof $scope.expenceSummarys !== "undefined") {
        var total = 0;
        for(var i = 0; i < $scope.expenceSummarys.length; i++){
            var expenceSummary = $scope.expenceSummarys[i].totalAmount;
            total += expenceSummary;
        }
        return total;
    }
    }

  };

  app.controller("ReportController", ReportController);

})();