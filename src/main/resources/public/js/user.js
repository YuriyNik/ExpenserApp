(function (){

  var app = angular.module("ExpenceApplication");

  var UserController = function($scope, $http, $location, AuthenticationService, host) {
      console.log('User started');

      $scope.data = {
          userTypes: [],
          todoLabels: []
      };

      $scope.user = {
                      password: ''
                      };

//add - post new expence type
      $scope.add = function() {

      var a = $scope.data.userTypes.indexOf($scope.input);

      if (($scope.input !== '') && (a == -1)) {

        $scope.data.userTypes.push($scope.input);
        $http.post(host+'/expenceTypes',$scope.data.userTypes).
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
//remove expence type
      $scope.remove = function(index) {
        $scope.data.userTypes.splice(index, 1);
        $http.post(host+'/expenceTypes',$scope.data.userTypes).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.userTypes);
       };
//add - post new todoLabels
      $scope.addTodoLabel = function() {

      var a = $scope.data.todoLabels.indexOf($scope.inputTodo);

      if (($scope.inputTodo !== '') && (a == -1)) {

        $scope.data.todoLabels.push($scope.inputTodo);
        $http.post(host+'/todoLabels',$scope.data.todoLabels).
                then(function(response) {
                    console.log('success');
                    console.log(response);
                }, function (response) {
                    console.log('error');
                    console.log(response);

                });
        console.log('Added and updated='+$scope.data.todoLabels);
        }
        $scope.inputTodo = '';

        };
//remove expence todoLabels
      $scope.removeTodoLabel = function(index) {
        $scope.data.todoLabels.splice(index, 1);
        $http.post(host+'/todoLabels',$scope.data.todoLabels).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.todoLabels);
       };
       console.log('user.js onLoad');
       $http.get(host+'/userDetails').
                          then(function(response) {
                              $scope.userDetails = response.data;
                              console.log('userDetails loaded');
                              }, function (response) {
                                  console.log('error!');
                                  console.log(response);
                                  });

       $http.get(host+'/expenceTypes').
                        then(function(response) {
                            console.log('expenceTypes='+response.data);
                            if (response.data=='')
                            {$scope.data.userTypes = [];}
                            else { $scope.data.userTypes = response.data;};

                        });
       $http.get(host+'/todoLabels').
                        then(function(response) {
                            console.log('todoLabels='+response.data);
                            if (response.data=='')
                            {$scope.data.todoLabels = [];}
                            else { $scope.data.todoLabels = response.data;};

                        });
        $scope.messagePassword='';
        $scope.changePass = function() {
            console.log("$scope.newpass="+$scope.newpass+"$scope.newpass2="+$scope.newpass2);

            if (($scope.newpass==$scope.newpass2)&&($scope.newpass!=='')&&(typeof $scope.newpass!== "undefined")){
                    $scope.user.password=$scope.newpass;
                   $http.post(host+'/userProfile',$scope.user).
                                    then(function(response) {
                               console.log('userProfile new pass success!');
                               $scope.messagePassword='Пароль обновлён';
                              // AuthenticationService.SetCredentials(username, password);
                               $location.path('/login');
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


  };

  app.controller("UserController", UserController);

})();
