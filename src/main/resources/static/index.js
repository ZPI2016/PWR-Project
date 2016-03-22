/**
 * Created by Martyna on 21.03.2016.
 */

(function(){
    var app = angular.module('myApp', []);

    app.controller('FormController', function ($http) {

        var data = JSON.stringify({
                username: 'username',
                password: 'pass',
                email: 'email'
            })
            ;
        
        var myapp = this;
        this.onClick = function (user) {

            myapp.data = JSON.stringify({
                    username: user.username,
                    password: user.password,
                    email: user.email
                })
            ;
            $http.post('/user', myapp.data);

        };
    });
})();




