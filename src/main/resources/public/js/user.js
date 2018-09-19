
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
        $scope.messagePassword='';
        $scope.changePass = function() {
            console.log("$scope.newpass="+$scope.newpass+"$scope.newpass2="+$scope.newpass2);

            if (($scope.newpass==$scope.newpass2)&&($scope.newpass!=='')&&(typeof $scope.newpass!== "undefined")){
                $scope.messagePassword='Пароль обновлён';
            }else
                {
                $scope.messagePassword='Ввведите непустой пароль в оба поля';
                }

        };
    }]);