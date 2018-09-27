
myApp.controller('Expence', ['$scope','$http', function($scope,$http) {

    $scope.today = new Date();
    $scope.premonth = new Date().getMonth();
    $scope.prePremonth = new Date().getMonth() - 1;

    $scope.currmonth = new Date().getMonth() +1;

    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);

    $scope.data = {
        userTypes: []
       };
    $scope.selected = {};

    console.log('Expence started premonth='+$scope.premonth+';prePremonth='+$scope.prePremonth);
    $http.get(host+'/userDetails', config).
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/expenceForThisMonth/'+$scope.currmonth+'/'+$scope.curryear,config).
    then(function(response) {
         $scope.expences = response.data;
    });

    $http.get(host+'/expenceTypes',config).
    then(function(response) {
        console.log('expenceTypes='+response.data);
        $scope.data.userTypes = response.data;
    });
   // start of editing functions: gets the template to ng-include for a table row / item
    $scope.getTemplate = function (expence) {
        if (expence.id === $scope.selected.id) return 'edit';
        else return 'display';
    };

    $scope.editExpence = function (expence) {
        console.log('editing expence = '+ expence.id+';'+expence.amount);
        expence.date= new Date(expence.date);
        $scope.selected = angular.copy(expence);
        console.log('saveExpence selected = '+ $scope.selected.id+';'+$scope.selected.type+';'+$scope.selected.amount+';'+$scope.selected.date);
    };

    $scope.saveExpence = function (selected) {
      console.log('saveExpence selected = '+ $scope.selected.id+';'+$scope.selected.type+';'+$scope.selected.amount+';'+$scope.selected.date);
       var index = 0;
       for(var i = 0; i < $scope.expences.length; i++){
          if($scope.expences[i].id==selected.id) {
             index= i;
             break;
          }
       }
       console.log('saveExpence index = '+index);
       if (typeof $scope.selected.type == "undefined") $scope.selected.type='Overall';
       $http.post(host+'/expence',$scope.selected,config).
           then(function(response) {
                       console.log('Update success');
                       console.log(response);
                       $scope.expences[index] = angular.copy($scope.selected);
                       $scope.reset();
           }, function (response) {
                       console.log('Update error');
                       console.log(response);
           });
    };

    $scope.reset = function () {
        $scope.selected = {};
    };
    ////end of editing functions





    $scope.showForThisMonth = function(month) {
        $scope.currmonth = month;
        console.log('show for month='+month+';year='+$scope.curryear);
        $http.get(host+'/expenceForThisMonth/'+$scope.currmonth+'/'+$scope.curryear,config).
        then(function(response) {
        $scope.expences = response.data;
        console.log('expencies reloaded');
    });
    };

    $scope.showByType = function(type) {
        console.log('showByType for type='+type+';month='+$scope.currmonth+';year='+$scope.curryear);
        $http.get(host+'/expenceByType/'+type+'/'+$scope.currmonth+'/'+$scope.curryear,config).
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
            console.log('$scope.expence.amount:'+ $scope.expence.amount+';type='+$scope.expence.type);
            if (typeof $scope.expence.type == "undefined") $scope.expence.type='Overall';
            $http.post(host+'/expence',$scope.expence,config).
            then(function(response) {
                console.log('success');
                console.log(response);
                $scope.expences.push(response.data);
                $scope.expence.amount = '';
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
