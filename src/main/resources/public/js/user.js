(function (){

  var app = angular.module("ExpenceApplication");

  var UserController = function($scope, $http, $location, AuthenticationService, host) {
      console.log('User started');

      $scope.data = {
          userTypes: [],
          currencyTypes: [],
          activityLabels: [],
          todoLabels: [],
          activityPlaces: []
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

//add - post new  activityLabels
      $scope.addActivityLabel = function() {

      var a = $scope.data.activityLabels.indexOf($scope.inputActivity);

      if (($scope.inputActivity !== '') && (a == -1)) {

        $scope.data.activityLabels.push($scope.inputActivity);
        $http.post(host+'/activityLabels',$scope.data.activityLabels).
                then(function(response) {
                    console.log('success');
                    console.log(response);
                }, function (response) {
                    console.log('error');
                    console.log(response);

                });
        console.log('Added and updated='+$scope.data.activityLabels);
        }
        $scope.inputActivity = '';

        };
//remove expence activityLabels
      $scope.removeActivityLabel = function(index) {
        $scope.data.activityLabels.splice(index, 1);
        $http.post(host+'/activityLabels',$scope.data.activityLabels).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.activityLabels);
       };
//add - post new  currency
      $scope.addCurrencyTypes = function() {

      var a = $scope.data.currencyTypes.indexOf($scope.inputCurrency);

      if (($scope.inputCurrency !== '') && (a == -1)) {

        $scope.data.currencyTypes.push($scope.inputCurrency);
        $http.post(host+'/currencyTypes',$scope.data.currencyTypes).
                then(function(response) {
                    console.log('success');
                    console.log(response);
                }, function (response) {
                    console.log('error');
                    console.log(response);
                });
        console.log('Added and updated='+$scope.data.currencyTypes);
        }
        $scope.inputCurrency = '';

        };
//remove expence activityLabels
      $scope.removeCurrencyTypes = function(index) {
        $scope.data.currencyTypes.splice(index, 1);
        $http.post(host+'/currencyTypes',$scope.data.currencyTypes).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.currencyTypes);
       };
//add - post new  activityPlaces
      $scope.addActivityPlaces = function() {

      var a = $scope.data.activityPlaces.indexOf($scope.inputActivityPlaces);

      if (($scope.inputActivityPlaces !== '') && (a == -1)) {

        $scope.data.activityPlaces.push($scope.inputActivityPlaces);
        $http.post(host+'/activityPlaces',$scope.data.activityPlaces).
                then(function(response) {
                    console.log('success');
                    console.log(response);
                }, function (response) {
                    console.log('error');
                    console.log(response);

                });
        console.log('Added and updated='+$scope.data.activityPlaces);
        }
        $scope.inputActivityPlaces = '';

        };
//remove expence activityPlaces
      $scope.removeActivityPlaces = function(index) {
        $scope.data.activityPlaces.splice(index, 1);
        $http.post(host+'/activityPlaces',$scope.data.activityPlaces).
                        then(function(response) {
                            console.log('success');
                            console.log(response);
                        }, function (response) {
                            console.log('error');
                            console.log(response);

                        });
        console.log('Removed and updated='+$scope.data.activityPlaces);
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
       $http.get(host+'/currencyTypes').
                        then(function(response) {
                            console.log('currencyTypes='+response.data);
                            if (response.data=='')
                            {$scope.data.userTypes = [];}
                            else { $scope.data.currencyTypes = response.data;};

                        });
       $http.get(host+'/todoLabels').
                        then(function(response) {
                            console.log('todoLabels='+response.data);
                            if (response.data=='')
                            {$scope.data.todoLabels = [];}
                            else { $scope.data.todoLabels = response.data;};

                        });

       $http.get(host+'/activityLabels').
                                 then(function(response) {
                                     console.log('activityLabels='+response.data);
                                     if (response.data=='')
                                     {$scope.data.activityLabels= [];}
                                     else { $scope.data.activityLabels = response.data;};

                                 });

       $http.get(host+'/activityPlaces').
                                    then(function(response) {
                                        console.log('activityPlaces='+response.data);
                                        if (response.data=='')
                                           {$scope.data.activityPlaces= [];}
                                           else { $scope.data.activityPlaces = response.data;};

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
