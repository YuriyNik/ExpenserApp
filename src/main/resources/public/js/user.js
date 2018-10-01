
myApp.controller('User', ['$scope','$http', function($scope,$http) {

      console.log('User started');

      $scope.data = {
          userTypes: []
      };

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
       $scope.addType = function() {

         var a = $scope.user.expenceTypes.indexOf($scope.input);

             if (($scope.newInput !== '') && (a == -1)) {

               $scope.user.expenceTypes.push($scope.newInput);
               console.log('Added type, now values='+$scope.user.expenceTypes);
               }
               $scope.newInput = '';

               };
       $scope.removeType = function(index) {
               $scope.user.expenceTypes.splice(index, 1);
              console.log('Removed type, now values='+$scope.user.expenceTypes);
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
           console.log("newUser $scope.user.username="+$scope.user.username);
           console.log("newUser $scope.user.password="+$scope.user.password);
           console.log("newUser $scope.user.expenceTypes="+$scope.user.expenceTypes);

           $http.post(host+'/user',$scope.user,config).
                                   then(function(response) {
                                   console.log('newUser success!='+response.data.username);
                                   if(typeof response.data.username!=="undefined") {
                                        $scope.messagePassword='Пользователь '+ response.data.username +' создан!';
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

    }]);