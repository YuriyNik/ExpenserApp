(function (){

  var app = angular.module("ExpenceApplication");

  var TrainingController = function($scope, $http, host) {

   console.log('Training started');

    $scope.today = new Date();
    $scope.currmonth = new Date().getMonth() +1;
    $scope.curryear = new Date().getFullYear();//can be changed for testing
    console.log('curryear='+$scope.curryear);
    $scope.yesterday = (function(d){ d.setDate(d.getDate()-1); return d})(new Date);

    $scope.ActivityType = '';

    $scope.selected = {};

    $scope.activity= {
   // timeDuration: {
    //    minutes: 2,
    //    seconds: 50 }
     };

        /* LocalDateTime date,
        String type,
        int duration,
        Double distance,
        int pulseAve,
        int pulseMax,
        String location,
        String notes,
        String weather,
        Double speedAve,
        Double paceAve,*/
    $scope.data = {
        userTypes: []
       // activityPlaces:[]
       };

    console.log('Training started currmonth='+$scope.currmonth+';curryear='+$scope.curryear);
    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Training userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });

    $http.get(host+'/activityLabels').
        then(function(response) {
            console.log('activityLabels='+response.data);
            $scope.data.activityLabels = response.data;
        });

     $http.get(host+'/activityPlaces').
        then(function(response){
        console.log('activityPlaces='+response.data);
        console.log(response);
        $scope.data.activityPlaces = response.data;
        });


   $http.get(host+'/activityForYear/'+$scope.currmonth+'/'+$scope.curryear+'/null').then(function(response) {
               $scope.activitiesList = response.data;
               $scope.activitiesList.sort(sortByDateDesc());
          });

    function sortByDateDesc(){
            return function(a,b){
                   return new Date(b.date) - new Date(a.date);
            };
    };

//post new
    $scope.addActivity = function(){
         console.log('addActivity');
         console.log($scope.activity);
         if (typeof $scope.activity.type == "undefined") $scope.activity.type='Ходьба';
                   $http.post(host+'/activity',$scope.activity).
                   then(function(response) {
                       console.log('success');
                       console.log(response);
                       $scope.activitiesList.push(response.data);
                       $scope.activitiesList.sort(sortByDateDesc());
                       $scope.activity = {};
                   }, function (response) {
                       console.log('error');
                       console.log(response);
                       console.log($scope.activity);
                   });
    };
//delete selected
    $scope.deleteActivity = function(activity){
               console.log('delete activity:'+activity+';id='+activity.id);
               $http.delete(host+'/activity/'+activity.id).
                   then(function(response) {
                      console.log(response);
                      var index = $scope.activitiesList.indexOf(activity);
                      console.log('index='+index);
                      $scope.activitiesList.splice(index, 1);
                   }, function (response) {
                       console.log('error!');
                       console.log(response);
                   });
    };

     $scope.getTemplate = function (activity) {
                   if (activity.id === $scope.selected.id) return 'edit';
                   else return 'display';
               };

     $scope.editActivity = function (activity) {
                   console.log('editing activity = '+ activity.id+';'+activity.amount);
                   activity.date= new Date(activity.date);
                   $scope.selected = angular.copy(activity);
                   console.log('saveActivity selected = '+ $scope.selected.id+';'+$scope.selected.type+';'+$scope.selected.distance+';'+$scope.selected.date);
               };

    $scope.reset = function () {
               $scope.selected = {};
    };
    $scope.saveActivity = function(selected){
            console.log('saveExpence selected = '+ $scope.selected.id+';'+$scope.selected.type+';'+$scope.selected.amount+';'+$scope.selected.date);
              var index = 0;
              for(var i = 0; i < $scope.activitiesList.length; i++){
                 if($scope.activitiesList[i].id==selected.id) {
                    index= i;
                    break;
                 }
              }
              console.log('saveActivity index = '+index);
              if (typeof $scope.selected.type == "undefined") $scope.selected.type='Overall';
              $http.put(host+'/activity/'+$scope.selected.id,$scope.selected).
                  then(function(response) {
                              console.log('Update success');
                              console.log(response);
                              $scope.activitiesList[index] = angular.copy(response.data);
                          //    $scope.activitiesList.sort(sortByDateDesc());
                              $scope.reset();
                  }, function (response) {
                              console.log('Update error');
                              console.log(response);
                  });

    };

    $scope.showByType = function(type) {
      console.log('Activities showByType for type='+type);
      $http.get(host+'/activityForYear/'+$scope.currmonth+'/'+$scope.curryear+'/'+type).
                       then(function(response) {
                       $scope.activitiesList = response.data;
                       $scope.activitiesList.sort(sortByDateDesc());
                       console.log('activity reloaded by type');
                       $scope.ActivityType = type;
                       }, function (response) {
                           console.log('error!');
                           console.log(response);
                       });
    };

    $scope.showForYear = function(year,month){
        $scope.curryear = year;
        $scope.currmonth = month;
        $http.get(host+'/activityForYear/'+$scope.currmonth+'/'+$scope.curryear+'/null').then(function(response) {
             $scope.activitiesList = response.data;
             $scope.activitiesList.sort(sortByDateDesc());
        });
        $scope.ActivityType = '';
    };

     $scope.getTotal = function(){
                   if (typeof $scope.activitiesList !== "undefined") {
                   var total = 0;
                   for(var i = 0; i < $scope.activitiesList.length; i++){
                       var distance = $scope.activitiesList[i].distance;
                       total += distance;
                   }
                   return total;
               }
               }


  };

  app.controller("TrainingController", TrainingController);

})();