/**
 * fix function definition 
 */

var app = angular.module('tasks', []);

app.controller('tasks', function($http, $scope){
	
		
	
	var self = this;
	//Goes to User Endpoint and sets user 
	//need / at begning of end of endpoint
	$http.get('/user').then(function(response) {
		self.user = response.data.name;
	}).then(function () {
		
		$scope.displayTasks();

		
		
	});
	
	$http.get('/resource/allstatuses').then(function (response){
		$scope.statuses = response.data;
	});

	self.updateStatus = function (status, task){
		console.log(status);
	
		$http({
			url: '/resource/changestatus/',
			method: 'GET',
			params: {taskId: task.id, newStatus : status},
			paramSerialzier: '$httpParamSerializer'
		}).then(function(response){
			$scope.displayTasks();
		});
		
		
	}
	
	$scope.displayTasks =function (){
		$http.post('/resource/alltasks', self.user).then(function (response){
			self.alltasks = response.data;
		});
	};
	
	self.task = {};
	
	self.deleteTask = function (currTask){

		if(confirm("Are you sure you want to delete?"))
		{
			$http.post('/resource/deletetask', currTask.id).then(function (response){
				if (response.data == 0){
					console.log("No error");
				}
				else{
					console.log("Error");
				}

				$scope.displayTasks();

			});
		}
	};
	
	self.taskToEdit = {};
	self.editTask = function (task) {
		self.edit = true;
		self.taskToEdit = task;
		console.log(self.taskToEdit);
	};
	
	

	self.updateTask = function(){


		$http.post('/resource/edittask', {
				id : self.taskToEdit.id, 
				username: self.user ,
				description: self.taskToEdit.description,
				status: self.taskToEdit.status,
				name: self.taskToEdit.name
				}).then(function(response){
					console.log(">???")
		
				})

		
		self.edit = false;
		
	}
	
	self.addTask = function () {
		console.log("IN ADD TASK")
		self.newtask = { 
				username: self.user,
				description: self.task.description,
				status: "TO_DO",
				name: self.task.name
				}
		console.log(self.newtask);
		$http.post('/resource/task', self.newtask)
	
		.then(function (response){
			var data = response.data;
			
			if (data == null){
				console.log("error");
			}
			else{
				console.log(data);
				console.log("no error");
				$scope.displayTasks();

			}
		});
	};
	
});