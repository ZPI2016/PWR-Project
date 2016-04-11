/**
 * Created by Martyna on 21.03.2016.
 *
 * Updated by Kuba on 03.04.2016
 */

angular.module('index', [ 'ngRoute' ])
    .config(function($routeProvider, $httpProvider) {

        $routeProvider.when('/', {
            templateUrl : 'home.html',
            controller : 'home',
            controllerAs: 'controller'
        }).when('/login', {
            templateUrl : 'login.html',
            controller : 'loginCtrl',
            controllerAs: 'controller'
        }).when('/register', {
            templateUrl : 'register.html',
            controller : 'registerCtrl',
            controllerAs: 'controller'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

    })

    .controller('home', function($http) {
        var self = this;
    })

    .controller('homeCtrl', function($http) {
        var self = this;
        //TODO: show login status
    })

    .controller('loginCtrl', function($rootScope, $http, $location) {
        var self = this;
        //TODO: process login
    })

    .controller("registerCtrl", function($rootScope, $http, $location) {
        var self = this;
    })

    .controller('navigation', function() {});
