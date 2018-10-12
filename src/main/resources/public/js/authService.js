//  Authentication service. Wrapped in an IIFE to avoid global variables
//  Purpose: To handle all user authentication methods

(function() {

  var AuthenticationService = function($http, $cookieStore, $rootScope, $timeout , $base64) {

        //  Function defined for when the user login is initiate
        var Login = function (username, password, callback) {
            authdata = $base64.encode(username + ':' + password);
            var config = {headers: {}};
            config.headers['Authorization'] = 'Basic ' + authdata;
            console.log('login='+config.headers['Authorization']);
                $http.get('http://localhost:8080'+'/login', config).
                    then(function(response) {
                           console.log('Login success!');
                           response.success=1;
                          callback('Ok');
                        }, function (response) {
                            response.message = 'Username or password is incorrect..';
                            console.log('Login error!'+response.message);
                            });

        };

        //  Sets the cookie and the state to logged in
        var SetCredentials = function (username, password) {

            var authdata = $base64.encode(username + ':' + password);
            console.log ('authdata='+authdata);

            $rootScope.globals = {
                currentUser: {
                    username: username,
                    authdata: authdata
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata;
            $cookieStore.put('globals', $rootScope.globals);
        };

        //  Clears the cookie and the state for the application to recognise a logged out state
        var ClearCredentials = function () {
            $rootScope.globals = {};
            $cookieStore.remove('globals');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };


    return {
      Login: Login,
      SetCredentials: SetCredentials,
      ClearCredentials: ClearCredentials
    };

  }

  //  Register the service with the application
  var module = angular.module("BasicHttpAuthentication");
  module.factory("AuthenticationService", AuthenticationService)

}());