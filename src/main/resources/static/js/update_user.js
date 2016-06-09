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
            }
        );
        var myApp = this;
        myApp.submitData = function (user) {
            myApp.data = JSON.stringify({
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
            $http.put('/users/' + oldUserData.id, myApp.data).success(function (data) {
                console.log("SUCCESS");
                $scope.ServerResponse = data;

            })
                .error(function (data) {

                    console.log("Error during updating user data.");
                });
        };


    })
    function initializeFormFields(result) {
        document.getElementById("username").value = result.username;
        document.getElementById("email").value = result.email;
        document.getElementById("dob").value = result.dob;
        document.getElementById("firstName").value = result.firstName;
        document.getElementById("lastName").value = result.lastName;
    }
}
());