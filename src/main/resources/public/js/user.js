
myApp.controller('User', ['$scope','$http', function($scope,$http) {

      console.log('User started');

      $scope.data = {
          userTypes: []
      };

       $scope.user = {
                password: ''
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
      $scope.onLoad = function(){
            console.log('User onLoad');
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

       };
       $scope.submit = function() {

        };
        $scope.messagePassword='';
        $scope.changePass = function() {
            console.log("$scope.newpass="+$scope.newpass+"$scope.newpass2="+$scope.newpass2);

            if (($scope.newpass==$scope.newpass2)&&($scope.newpass!=='')&&(typeof $scope.newpass!== "undefined")){
                    $scope.user.password=$scope.newpass;
                   $http.post(host+'/userProfile',$scope.user,config).
                                    then(function(response) {
                                     console.log('userProfile new pass success!');
                                   $scope.messagePassword='Пароль обновлён';
                                   $scope.user.password='';
                                    }, function (response) {
                                        console.log('error');
                                        console.log(response);

                                    });
            }else
                {
                $scope.messagePassword='Введите непустой пароль в оба поля';
                }

        };
        $scope.newUser = function() {
           console.log("newUser $scope.user="+$scope.user);

        /*   $http.post(host+'/user',$scope.user,config).
                                    then(function(response) {
                                     console.log('newUser success!');

                                   $scope.user.password='';
                                    }, function (response) {
                                        console.log('error');
                                        console.log(response);

                                    });
          */

        };


    }]);