var app = angular.module('register', ['ngRoute']);

app.controller('register', function ($route, $http){

	var self = this;
	self.account = {};
	
	//var registrationErrors = [];


	//posting the user to the controller
	//controller checks for a valid option and returns errors if there are any	
	self.registration = function() {
		$http.post('register', self.account)
		.then(function(response) {
			
			//setting error messages to be data variable
			var registrationErrors = response.data;
			
			console.log("in reg register call");
			console.log(response.data);
			
			if(registrationErrors.length > 0 ){
				console.log("Errors");
				self.registrationErrors = registrationErrors;
				self.error = true;
				self.registerSuccess = false;
				
			}
			else
			{
				self.registerSuccess = true;
				self.error = false;
			}
			
		});
				
	}	
});

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