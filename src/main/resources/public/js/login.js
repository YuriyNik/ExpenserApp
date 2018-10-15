(function (){

  var app = angular.module("ExpenceApplication");

  var LoginController = function($scope, $rootScope, $location,$http, AuthenticationService, host) {

      $scope.user = {
                password: '',
                roles:["USER"],
                expenceTypes: [
                 "Обеды-Еда",
                 "Продукты",
                 "Одежда",
                 "Счета",
                 "Overall",
                 "Grocery",
                 "Топливо",
                 "Проезд",
                 "Машина",
                 "Развлечения",
                 "Путешествия",
                 "Хобби",
                 "Аптека",
                 "Подарки"
                 ]
            };

       $scope.newUser = function() {
           console.log("newUser $scope.user.username="+$scope.user.username);
           console.log("newUser $scope.user.password="+$scope.user.password);
           console.log("newUser $scope.user.expenceTypes="+$scope.user.expenceTypes);

           $http.post(host+'/user',$scope.user).
                                   then(function(response) {
                                   console.log('newUser success!='+response.data.username);
                                   if(typeof response.data.username!=="undefined") {
                                        $scope.messagePassword='Пользователь '+ response.data.username +' создан!';
                                        $scope.login($scope.user.username,$scope.user.password);

                                   } else {
                                    $scope.messagePassword=$scope.user.username +' пользователь уже есть!';
                                   }

                                  $scope.user.username='';
                                  $scope.user.password='';

                                    }, function (response) {
                                        console.log('error');
                                        console.log(response);
                                    });

        };
       //new user form
       $scope.addType = function() {

         var a = $scope.user.expenceTypes.indexOf($scope.input);

             if (($scope.newInput !== '') && (a == -1)) {

               $scope.user.expenceTypes.push($scope.newInput);
               console.log('Added type, now values='+$scope.user.expenceTypes);
               }
               $scope.newInput = '';

               };
       //new user form
       $scope.removeType = function(index) {
               $scope.user.expenceTypes.splice(index, 1);
              console.log('Removed type, now values='+$scope.user.expenceTypes);
       };




      // Reset the login status before we start
      AuthenticationService.ClearCredentials();

      $scope.login = function (username, password) {
        //  $scope.dataLoading = true;
          console.log('login started');
          AuthenticationService.Login(username, password, function(response)
          {          console.log('login started response.success='+response.success);

              if(response=='Ok') {
                        console.log('response.success is True');

                  AuthenticationService.SetCredentials(username, password);
                  $location.path('/');
              } else {
               console.log('response.message='+response);
                  $scope.error = response;
                 // $scope.dataLoading = false;
              }
          });
      };

  };

  app.controller("LoginController", LoginController);

})();