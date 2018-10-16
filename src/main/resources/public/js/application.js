(function() {

  var app = angular.module("ExpenceApplication", ["ngRoute", "ngCookies", 'base64']);

  app.constant('host', '');//http://localhost:8080');

  app.config(function($routeProvider) {

    $routeProvider
     .when('/login', {
        controller: 'LoginController',
        templateUrl: 'login.html',
        hideMenus: true
      })
     .when('/register', {
        controller: 'LoginController',
        templateUrl: 'register.html'
      })
    .when('/', {
      controller: 'HomeController',
      templateUrl: 'home.html'
    })

    .when('/reports', {
      controller: 'ReportController',
      templateUrl: 'reports.html'
    })

    .when('/user', {
      controller: 'UserController',
      templateUrl: 'user.html'
    })

    .when('/todos', {
      controller: 'TodoController',
      templateUrl: 'todos.html'
    })

    .otherwise({
      redirectTo: '/login'
    });

  });

   app.run(function($rootScope, $location, $cookieStore, $http ) {
 // keep user logged in after page refresh
        $rootScope.globals = $cookieStore.get('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in
            if ($location.path() !== '/login' && !$rootScope.globals.currentUser && $location.path() !== '/register') {
                $location.path('/login');
            }
        });
   });

}());