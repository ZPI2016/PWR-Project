/**
 * Created by Sandra on 2016-05-05.
 */
(function () {
    var app = angular.module('myApp', []);

    app.config(['$httpProvider', function ($httpProvider) {
        //fancy random token
        function b(a) {
            return a ? (a ^ Math.random() * 16 >> a / 4).toString(16) : ([1e16] + 1e16).replace(/[01]/g, b)
        };

        document.cookie = 'CSRF-TOKEN=' + b() + '; expires=' + new Date(0).toUTCString();

        $httpProvider.interceptors.push(function () {
            return {
                'request': function (response) {
                    // put a new random secret into our CSRF-TOKEN Cookie before each request
                    document.cookie = 'CSRF-TOKEN=' + b() + ';path=/';
                    return response;
                }
            };
        });

        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    }]);

    app.controller('UserUpdateController', function ($scope, $http) {
        var oldUserData;
        $http.get('/users/security/logged').success(function (result) {
            initializeFormFields(result);
            oldUserData = result;
        });
        var myapp = this;
        this.onClick = function (user) {
            $http.get('/users/security/logged').success(function (result) {
                // initializeFormFields(result);
                oldUserData = {
                    id: result.id,
                    email: result.email,
                    geoLatitude: result.geoLatitude,
                    geoLongitude: result.geoLongitude,
                    password: result.password,
                    firstName: result.firstName,
                    lastName: result.lastName,
                    dob: result.dob
                };
            })
                .error(function (result) {
                    console.log("Error during getting user data.");
                });
            if(!user.password){
                console.log(oldUserData.password);
                user.password = oldUserData.password;
            }
            myapp.data = JSON.stringify({
                id: oldUserData.id,
                username: user.username,
                password: user.password,
                email: user.email,
                address: {
                    geoLatitude: endLat,
                    geoLongitude: endLng
                },
                dob: user.dob,
                firstName: user.firstName,
                lastName: user.lastName,
                radius: user.radius
            });
            $http.put('/users/' + oldUserData.id, myapp.data).success(function (data) {
                console.log("SUCCESS");
                $scope.ServerResponse = data;

            })
                .error(function (data) {

                    console.log("Error during updating user data.");
                    //$scope.ServerResponse = htmlDecode("Data: " + data +
                    //"\n\n\n\nstatus: " + status +
                    //"\n\n\n\nheaders: " + header +
                    //"\n\n\n\nconfig: " + config);
                });
        };


    })
    function initializeFormFields(result) {
        console.log(result.username);
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