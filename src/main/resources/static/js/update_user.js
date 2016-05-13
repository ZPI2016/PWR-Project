/**
 * Created by Sandra on 2016-05-05.
 */
(function () {
    var app = angular.module('myApp', []);


    app.controller('UserUpdateController', function ($scope, $http) {

        $http.get('/users/username/martyna').success(function (result) {
            $scope.userData = result;
            initializeFormFields(result);
        })
            .error(function (data, status, header, config) {
                    console.log("Error during getting user data.");
                    $scope.ServerResponse = htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
            });
        var myapp = this;
        this.onClick = function (user) {
            $http.get('/users/security/logged').success(function (result) {
                oldUserData = {
                    userID: result.id,
                    email: result.email,
                    geoLatitude: result.geoLatitude,
                    geoLongitude: result.geoLongitude,
                    //firstName: result.firstName,
                    //lastName: result.lastName,
                    dob: result.dob
                };
            })
                .error(function (result) {
                    console.log("Error during getting user data.");
                });
            myapp.data = JSON.stringify({
                username: user.username,
                password: user.password,
                email: user.email,
                address: user.address,
                dob: user.dob,
               // firstName: user.firstName,
               //lastName: user.lastName,
                radius: user.radius
            });
            $http.put('/users/security/logged', myapp.data).success(function (data, status, headers) {
                console.log("SUCCESS");
                $scope.ServerResponse = data;

            })
                .error(function (data, status, header, config) {
                    console.log("Error during updating user data.");
                    $scope.ServerResponse = htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
                });
        };


    })
    function initializeFormFields(result){
        document.getElementById("username").value = result.username;
        document.getElementById("email").value = result.email;
        document.getElementById("geoLatitude").value = result.geoLatitude;
        document.getElementById("geoLongitude").value = result.geoLongitude;
        document.getElementById("dob").value = result.dob;
        document.getElementById("firstName").value = result.firstName;
        document.getElementById("lastName").value = result.lastName;
        document.getElementById("radius").value = result.radius;
    }
}
());