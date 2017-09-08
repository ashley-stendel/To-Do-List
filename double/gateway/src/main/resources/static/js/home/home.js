var app = angular.module('home', []);

app.controller('home', function($http) {
	var self = this;
	
	//Goes to User Endpoint and sets user 
	//need / at begning of end of endpoint
	$http.get('/user').then(function(response) {
		self.user = response.data.name;
	});
});
