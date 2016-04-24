    /**
 * Created by Martyna on 21.03.2016.
 *
 * Updated by Kuba on 03.04.2016
 */

angular.module('index', [ 'ngRoute' ])
    .config(function($routeProvider, $httpProvider) {

        $routeProvider.when('/signin', {
            templateUrl : 'login.html',
            controller : 'loginCtrl',
            controllerAs: 'controller'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'registerCtrl',
            controllerAs: 'controller'
        }).otherwise('/');

//      $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })

    // WYSYŁA PUSTY REQUEST (???)
    //
    //.controller('loginCtrl', function($http) {
    //    var self = this;
    //    self.user = {};
    //    self.error = {};
    //    self.login = function(){
    //        console.log("Login: " + self.user.username)
    //        $http({
    //            method  : 'POST',
    //            url     : '/login',
    //            data    : {
    //                username: self.user.username,
    //                password: self.user.password
    //            },
    //            headers : {'Content-Type': 'application/json'}
    //        })
    //            .then(function successful(response){
    //                console.log("SUCCESS");
    //                console.log(response);
    //            }, function error(response){
    //                console.log("FAILURE");
    //                console.log(response);
    //                self.error = true;
    //            })
    //    }
    //})

    .controller("registerCtrl", function($http) {
        var self = this;
        self.user = {};
        self.register = function(){
            console.log("Output: " + self.user.username)
            $http.post("/users", self.user)
                .then(function successful(response){
                    console.log(response);
                }, function error(response){
                    for(k in response)
                        console.log(k + ": " + response[k])
                })
        }

    });