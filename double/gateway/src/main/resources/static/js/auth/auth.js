/**
 * Separating Authentication Concerns - Authentication Service 
 * Using $rootScope to share information between controllers 
 * 
 * Contains authenticated flag and function to support login, logout
 */

var app = angular.module('auth', []).factory(
		'auth',

		function($rootScope, $http, $location) {

			enter = function() {
				  if ($location.path() != auth.loginPath) {
				    auth.path = $location.path();
				    if (!auth.authenticated && auth.path != auth.registerPath) {
				      $location.path(auth.loginPath);
				    }
				  }
				}
			
			var auth = { 
					authenticated : false,

					loginPath: '/login',
					logoutPath: '/logout',
					homePath: '/',
					registerPath: '/register',


					authenticate : function(credentials, callback) {
						var headers = credentials ? {
							authorization : "Basic "
								+ btoa(credentials.username + ":"
										+ credentials.password)
						} : {};
					

							$http.get('user', {
								headers : headers
							}).then(function(response) {
								console.log("getting user");
								console.log(response.data);
								var data = response.data;
								//check whether it is needed to change paths after authentication
								$location.path(auth.path == auth.loginPath ? auth.homePath : auth.path)

								if (data.name) {
									auth.authenticated = true;

									//self.admin = data && data.roles && data.roles.indexOf("ROLE_ADMIN")>-1;
								} else {
									auth.authenticated = false;
									//self.admin = false;
								}
								
								//return homepage
								$location.path(auth.homePath);
								callback && callback(auth.authenticated);
								
							}, function() {
								console.log("no response");
								//console.log(self.credentials)
								auth.authenticated = false;
		
								callback && callback(false);
							});
					},

					clear: function() {
						//when logout, set authenticated to false, route to login page
						auth.authenticated = false;
						$location.path(auth.loginPath);
						
						//Posting to logout path 
						//Succeeds because CSRF protection 
						$http.post(auth.logoutPath, {});

					}, 

					init: function(homePath, loginPath, logoutPath, registerPath){
						//setting auth instance variables
						auth.homePath = homePath;
						auth.loginPath = loginPath;
						auth.logoutPath = logoutPath;
						auth.registerPath = registerPath
						
						$rootScope.$on('$routeChangeStart', function(){
							enter();
							});
						
						auth.authenticate({}, function(authenticated){
							if (authenticated){
								$location.path(auth.path);
							}
						
						});
					}
			};

			
			
			return auth;
		});