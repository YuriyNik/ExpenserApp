
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

    $scope.showByType = function(type) {
        console.log('showByType for type='+type);
        $http.get(host+'/expenceByType/'+type+'/'+$scope.currmonth,config).
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
