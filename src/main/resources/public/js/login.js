//  Login Controller. Wrapped in an IIFE to avoid global variables
//  Purpose: Hides the badgers?

(function (){

  var app = angular.module("BasicHttpAuthentication");

  var LoginController = function($scope, $rootScope, $location, AuthenticationService) {

      // Reset the login status before we start
      AuthenticationService.ClearCredentials();

      $scope.login = function (username, password) {
          $scope.dataLoading = true;
          console.log('login started');
          AuthenticationService.Login(username, password, function(response)
          {          console.log('login started response.success='+response.success);

              if(response=='Ok') {
                        console.log('response.success is True');

                  AuthenticationService.SetCredentials(username, password);
                  $location.path('/');
              } else {
                  $scope.error = response.message;
                  $scope.dataLoading = false;
              }
          });
      };
  };

  app.controller("LoginController", LoginController);

})();