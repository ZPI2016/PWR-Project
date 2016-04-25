/**
 * Created by Martyna on 21.03.2016.
 */

(function(){
    var app = angular.module('myApp', []);
    var login = false;
    
    app.config(['$httpProvider', function($httpProvider) {
        //fancy random token, losely after https://gist.github.com/jed/982883
        function b(a){return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)};

        $httpProvider.interceptors.push(function() {
            return {
                'request': function(response) {
                    // put a new random secret into our CSRF-TOKEN Cookie before each request
                    document.cookie = 'CSRF-TOKEN=' + b();
                    return response;
                }
            };
        });

        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';

    }]);

    app.controller('FormController', function ($scope, $http){
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

    app.controller('LoginController', function ($scope, $http) {
        this.onClick = function (user) {
            $http({
                method: 'POST',
                url: '/',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: user
            });
        };
    });

})();