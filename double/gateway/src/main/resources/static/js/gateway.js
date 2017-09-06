var app = angular.module('gateway', [])


app.config(function($httpProvider) {

	$httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

});

app.controller('navigation',

function($http) {

	
	var self = this;


	var authenticate = function(credentials, callback) {
		console.log("in auth");
		var headers = credentials ? {
			authorization : "Basic "
					+ btoa(credentials.username + ":"
							+ credentials.password)
		} : {};

		self.user = ''
		$http.get('user', {
			headers : headers
		}).then(function(response) {
		
			var data = response.data;
			console.log(data.name);
			if (data.name) {
				self.authenticated = true;
				self.register = false;
				self.user = data.name
				self.admin = data && data.roles && data.roles.indexOf("ROLE_ADMIN")>-1;
			} else {
				self.authenticated = false;
				self.admin = false;
				self.register = false;
			}
			callback && callback(true);
		}, function() {
			console.log(self.credentials)
			self.authenticated = false;
			self.register = false;
			callback && callback(false);
		});

	}

	authenticate();

	self.credentials = {};

	self.login = function() {
		authenticate(self.credentials, function(authenticated) {
			self.authenticated = authenticated;
			self.loginError = !authenticated;
			self.register = false;
		})
	};
	

	self.goToRegister = function () {
			self.authenticated = false;
			self.register = true;
	}
	
	
	self.showHome = function () {
			self.loginError = false;
			self.registerError = false;
			self.register = false;
			self.authenticated = self.authenticated;
	}
			
	
	self.account = {};
	
	//posting the user to the controller
	//controller checks for a valid option and returns errors if there are any
	self.registration = function() {
		$http.post('register', self.account)
		.then(function(response) {
			
			//setting error messages to be data variable
			var registrationErrors = response.data;
			
			if(registrationErrors.length > 0 ){
				self.registerError = true;
				self.register = true;
				self.registrationErrors = registrationErrors;
			}
			else{
				self.registerError = false;
				self.registerSuccess = true;
			}
			
		});
				
	}
	
	self.logout = function() {
		$http.post('logout', {}).finally(function() {
			self.authenticated = false;
			self.admin = false;
			self.register = false;
		});
	}

});

//this allowed to create new "functions" in the html
app.directive("compareTo", function() {
    return {
      require: "ngModel",
      scope: {
        otherModelValue: "=compareTo" //looking for the value labelled "compareTo
      },
      link: function(scope, element, attributes, ngModel) {

        ngModel.$validators.compareTo = function(modelValue) { //adding compareTo validator to $validators
          return modelValue == scope.otherModelValue;    		// return boolean 
        };

        scope.$watch("otherModelValue", function() {
          ngModel.$validate();
        });
      }
    };
  });

	
