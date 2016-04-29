/**
 * Created by Martyna on 21.03.2016.
 */

(function(){
    var app = angular.module('myApp', [ 'ngRoute' ]);
    var login = false;

    app.config(function($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl : 'home.html',
            controller : 'homeController',
            controllerAs: 'homeCtrl'
        }).when('/signin', {
            templateUrl : 'login.html',
            controller : 'loginController',
            controllerAs: 'loginCtrl'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'registerController',
            controllerAs: 'registerCtrl'
        }).otherwise('/')
    });

    app.config(['$httpProvider', function($httpProvider) {
        //fancy random token
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

    app.controller('registerController', function ($scope, $http){
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

    app.controller('loginController', function ($scope, $http) {
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

    app.controller("homeController", function(){
       // :) tu byłam! ~Iza Małysz
    });

})();