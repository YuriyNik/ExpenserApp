(function() {

  var app = angular.module("ExpenceApplication", ["ngRoute", "ngCookies", 'base64']);


  app.config(function($routeProvider) {

    $routeProvider
     .when('/login', {
        controller: 'LoginController',
        templateUrl: 'login.html',
        hideMenus: true
      })

    .when('/', {
      controller: 'HomeController',
      templateUrl: 'home.html'
    })
/*
    .when('/', {
      controller: 'HomeController',
      templateUrl: 'reports.html'
    })

    .when('/', {
      controller: 'HomeController',
      templateUrl: 'user.html'
    })
*/

    .otherwise({
      redirectTo: '/login'
    });

  });

   app.run(function($rootScope, $location, $cookieStore, $http) {
 // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser) {
                $location.path('/login');
            }
        });
   });

}());