myApp.controller('Training', ['$scope','$http', function($scope,$http) {

    console.log('Training started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() +1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.data = {
        userTypes: []
       };

    console.log('Training started currmonth='+$scope.currmonth+';curryear='+$scope.curryear);
    $http.get(host+'/userDetails', config).
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Training userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });


    //$http.get(host+'/activity/'+$scope.currmonth+'/'+$scope.curryear+'/null',config).

     $http.get(host+'/activity/',config).

    then(function(response) {
         $scope.activitySummarys = response.data;
    });


  /*  $http.get(host+'/activityTypes',config).
    then(function(response) {
        console.log('Report activityTypes='+response.data);
        $scope.data.userTypes = response.data;
    });*/


/*
    $scope.showForMonthYear = function(month,year) {
        $scope.currmonth = month;
        console.log('Report for month='+month+';year='+year);
        $http.get(host+'/reports/'+$scope.currmonth+'/'+year+'/null',config).
        then(function(response) {
        $scope.activitySummarys = response.data;
        console.log('activitySummarys reloaded');
    });
    };
*/

/*  $scope.getTotalSummary = function(){
        if (typeof $scope.activitySummarys !== "undefined") {
        var total = 0;
        for(var i = 0; i < $scope.activitySummarys.length; i++){
            var activitySummarys = $scope.activitySummarys[i].totalAmount;
            total += activitySummarys;
        }
        return total;
    }
    }
*/
}]);