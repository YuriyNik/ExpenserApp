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
/*
    $http.get(host+'/reports/'+$scope.currmonth+'/'+$scope.curryear+'/null',config).
    then(function(response) {
         $scope.expenceSummarys = response.data;
    });

    $http.get(host+'/expenceTypes',config).
    then(function(response) {
        console.log('Report expenceTypes='+response.data);
        $scope.data.userTypes = response.data;
    });

*/
/*
    $scope.showForMonthYear = function(month,year) {
        $scope.currmonth = month;
        console.log('Report for month='+month+';year='+year);
        $http.get(host+'/reports/'+$scope.currmonth+'/'+year+'/null',config).
        then(function(response) {
        $scope.expenceSummarys = response.data;
        console.log('expenceSummary reloaded');
    });
    };
*/
/*
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
*/
}]);