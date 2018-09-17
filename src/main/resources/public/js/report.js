
myApp.controller('Report', ['$scope','$http', function($scope,$http) {

        console.log('Report started');

        $scope.removeItem = function(index) {
            console.log('delete index='+index);
            console.log('delete id='+$scope.reports[index].id);
            $http.delete('http://localhost:8080/report/'+$scope.reports[index].id).
                then(function(response) {
                    console.log('deleted!');
                    console.log(response);
                    $scope.reports.splice(index, 1);
                }, function (response) {
                    console.log('error!');
                    console.log(response);
                });
        };

        $http.get('http://localhost:8080/report').
                then(function(response) {
                  $scope.reports = response.data;
                 });
        $scope.submit = function() {
            $http.post('http://localhost:8080/report',$scope.report,{
                headers: {'Content-Type': 'application/json'}}).
                then(function(response) {
                    console.log('first');
                    console.log(response);
                   $scope.reports.push(response.data);
                  // console.log($scope.reports);

                }, function (response) {
                    console.log('second');
                    console.log(response);
                    console.log($scope.report);

                });
        };
    }]);
