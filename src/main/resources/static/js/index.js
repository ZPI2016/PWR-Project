/**
 * Created by Martyna on 21.03.2016.
 */

(function(){
    var app = angular.module('myApp', ['ngRoute']);
    var login = false;

    app.controller('FormController', function ($http){

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

    app.controller('LoginController', function () {
        var data;
        var myapp = this;

        this.onClick = function (user) {
            myapp.data = JSON.stringify({
                username: user.username,
                password: user.password
            });
        };

    });

    // app.selectTab = function (chosen){
    //     this.login = chosen;
    // };



})();