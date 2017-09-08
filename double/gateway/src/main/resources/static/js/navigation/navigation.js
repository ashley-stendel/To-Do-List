var app = angular.module('navigation', ['ngRoute', 'auth']);

app.controller('navigation', function($route, auth) {
	
	var self = this;
	
	self.credentials = {};
	
	self.tab = function(route) {
		return $route.current && route == $route.current.controller;
	}
	
	
	self.authenticated = function() {
		return auth.authenticated;
	
	}
	
	self.login = function() {
		console.log("in login function");
		auth.authenticate(self.credentials, function(authenticated) {
			
			if (authenticated){
				console.log("Successful Login");
				self.error = false;
			}
			else{
				console.log("Error during login");
				self.error = true;
			}
		})
	};
	
		
	self.logout = auth.clear;
	
});