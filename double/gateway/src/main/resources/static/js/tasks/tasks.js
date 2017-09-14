var app = angular.module('tasks', []);

app.controller('tasks', function($http){
	
		
	
	var self = this;
	//Goes to User Endpoint and sets user 
	//need / at begning of end of endpoint
	$http.get('/user').then(function(response) {
		self.user = response.data.name;
	}).then(function () {
		
			$http.post('/resource/alltasks', self.user).then(function (response){
				self.alltasks = response.data;
			});
		
		
	});
	
	self.task = {};
	
	self.displayTasks = function () {
		$http.post('/resource/alltasks', self.user).then(function (response){
			self.alltasks = response.data;
		});
	};
	
	self.addTask = function () {
		console.log("IN ADD TASK")
		self.newtask = { 
				username: self.user,
				description: self.task.description,
				status: "To Do",
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
//				$http.post('/resource/alltasks', self.user).then(function (response){
//					self.alltasks = response.data;
//				}
			}
		});
	};
	
});