var myApp = angular.module('App',[]);

var host='';//http://localhost:8080';

var config = {headers: {
          //  'Authorization': 'Basic ',//for development only
            'Content-Type': 'application/json'
        }
    };

myApp.controller('Expence', ['$scope','$http', function($scope,$http) {

    $scope.currmonth = 0;

    $scope.data = {
        userTypes: []
       };

    console.log('Expence started');
    $http.get(host+'/userDetails', config).
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/expenceForThisMonth/'+$scope.currmonth,config).
    then(function(response) {
         $scope.expences = response.data;
    });

    $http.get(host+'/expenceTypes',config).
    then(function(response) {
        console.log('expenceTypes='+response.data);
        $scope.data.userTypes = response.data;
    });


    $scope.showForThisMonth = function(month) {
        console.log('show for month='+month);
        $scope.currmonth = month;
        $http.get(host+'/expenceForThisMonth/'+$scope.currmonth,config).
        then(function(response) {
        $scope.expences = response.data;
        console.log('expencies reloaded');
    });
    };

    $scope.showByType = function(index) {
        console.log('showByType for index='+index+';type='+$scope.expences[index].type);
        $http.get(host+'/expenceByType/'+$scope.expences[index].type+'/'+$scope.currmonth,config).
            then(function(response) {
                $scope.expences = response.data;
                console.log('expencies reloaded by type for this month');
            }, function (response) {
                console.log('error!');
                console.log(response);
            });
    };

    $scope.removeItem = function(expence) {
        console.log('delete expence:'+expence+';id='+expence.id);
        $http.delete(host+'/expence/'+expence.id,config).
            then(function(response) {
                console.log(response);
               var index = $scope.expences.indexOf(expence);
               console.log('index='+index);
                $scope.expences.splice(index, 1);

            }, function (response) {
                console.log('error!');
                console.log(response);
            });
    };
    $scope.submit = function() {
            console.log('$scope.expence.amount'+ $scope.expence.amount);
            console.log('$scope.expence.type='+$scope.expence.type);
            if (typeof $scope.expence.type == "undefined") $scope.expence.type='Overall';
            $http.post(host+'/expence',$scope.expence,config).
            then(function(response) {
                console.log('success');
                console.log(response);
                $scope.expences.push(response.data);

                $scope.expence.amount = '';

               // $scope.expence = '';

            }, function (response) {
                console.log('error');
                console.log(response);
                console.log($scope.expence);

            });

    };

    $scope.getTotal = function(){
        if (typeof $scope.expences !== "undefined") {
        var total = 0;
        for(var i = 0; i < $scope.expences.length; i++){
            var expence = $scope.expences[i].amount;
            total += expence;
        }
        return total;
    }
    }

}]);


myApp.controller('User', ['$scope','$http', function($scope,$http) {

      console.log('User started');

      $scope.data = {
          userTypes: []
      };

      $scope.add = function() {

      var a = $scope.data.userTypes.indexOf($scope.input);

      if (($scope.input !== '') && (a == -1)) {

        $scope.data.userTypes.push($scope.input);
        $http.post(host+'/expenceTypes',$scope.data.userTypes,config).
                then(function(response) {
                    console.log('success');
                    console.log(response);
                }, function (response) {
                    console.log('error');
                    console.log(response);

                });
        console.log('Added and updated='+$scope.data.userTypes);
        }
        $scope.input = '';

        };
      $scope.remove = function(index) {
           	$scope.data.userTypes.splice(index, 1);
        $http.post(host+'/expenceTypes',$scope.data.userTypes,config).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.userTypes);
        };

        $http.get(host+'/userDetails', config).
              then(function(response) {
                  $scope.userDetails = response.data;
                  console.log('userDetails loaded');
                  }, function (response) {
                      console.log('error!');
                      console.log(response);
                      });

        $http.get(host+'/expenceTypes',config).
            then(function(response) {
                console.log('expenceTypes='+response.data);
                if (response.data=='')
                {$scope.data.userTypes = [];}
                else { $scope.data.userTypes = response.data;};

            });
        $scope.submit = function() {

        };
    }]);


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
