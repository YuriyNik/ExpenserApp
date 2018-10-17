(function (){

  var app = angular.module("ExpenceApplication");

  var TodoController = function($scope, $http, host) {

    console.log('Todos started');

    $scope.data = {
        userTypes: []
       };
    $scope.todo ={
        description:'',
        date:''

    }

    $http.get(host+'/userDetails').
        then(function(response) {
            $scope.userDetails = response.data;
            console.log('Todos userDetails loaded');
            }, function (response) {
                console.log('error!');
                console.log(response);
                });
    $http.get(host+'/todo').then(function(response) {
         $scope.todos = response.data;
    });

   $scope.getTotalTodos = function () {
    return $scope.todos.length;
     };


    $scope.addTodo = function () {
     if ((typeof $scope.formTodoText == "undefined")||($scope.formTodoText == '')) return;
       console.log('$scope.formTodoText:'+ $scope.formTodoText);
       $scope.todo.description=$scope.formTodoText;
       $scope.todo.date=$scope.formTodoDate;
       $http.post(host+'/todo',$scope.todo).
                       then(function(response) {
                           console.log('success');
                           console.log(response);
                           $scope.todos.push(response.data);
                           $scope.formTodoText = '';
                           $scope.formTodoDate = '';
                       }, function (response) {
                           console.log('error');
                           console.log(response);
                           console.log($scope.todo);
                       });
     };

    $scope.onCheckboxClick = function(todo) {
    console.log('onCheckboxClick todo='+todo.id+';'+todo.description+';'+todo.completed);
    $http.put(host+'/todo/'+todo.id,todo).
                           then(function(response) {
                               console.log('Update - put success');
                               var index = $scope.todos.indexOf(todo);
                               console.log('index='+index);
                               $scope.todos.splice(index, 1);
                           }, function (response) {
                               console.log('error');
                               console.log(response);
                               console.log(todo);
                           });
    };



  };

  app.controller("TodoController", TodoController);

})();