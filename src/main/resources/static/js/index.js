/**
 * Created by Martyna on 21.03.2016.
 */

(function(){
    var app = angular.module('myApp', []);

    app.controller('FormController', function ($http) {

        var data;
        var myapp = this;

        this.onClick = function (user) {
            myapp.data = JSON.stringify({
                username: user.username,
                password: user.password,
                email: user.email,
                address: user.address,
                dob: user.dob
            });
            $http.post('/users', myapp.data);
        };
    });
})();