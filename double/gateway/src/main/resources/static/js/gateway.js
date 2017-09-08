var app = angular.module('gateway', ['ngRoute','auth', 'home', 'message', 'navigation', 'register'])  
//App depends on two other modules in other JS files
//Need to load in correct order in index.html (script src="js/home/home.js" ...) and in config method

app.config(function($httpProvider, $routeProvider, $locationProvider) {

	//Needed for natural routes 
	//Also <base href="/" /> needed in index.html
	$locationProvider.html5Mode(true);
	
	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
	
	$routeProvider.when("/", {
		templateUrl: 'js/home/home.html',
		controller: 'home',
		controllerAs: 'controller'
		}).when('/message', {
			templateUrl : 'js/message/message.html',
			controller : 'message',
			controllerAs : 'controller'
		}).when("/login", {
			templateUrl: 'js/navigation/login.html',
			controller: 'navigation',
			controllerAs: 'controller'
		}).when("/register", {
			templateUrl: 'js/register/register.html',
			controller: 'register',
			controllerAs: 'controller'
		}).otherwise('/');

});

app.run(function (auth){
	auth.init('/', '/login', '/logout', '/register')
});

//this allowed to create new "functions" in the html


	
