/**
 * Created by Martyna on 07.04.2016.
 */

(function () {
    var app = angular.module('myApp', []);
    
    app.config(['$httpProvider', function($httpProvider) {
        //fancy random token
        function b(a){return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)};

        document.cookie = 'CSRF-TOKEN=' + b() + '; expires=' + new Date(0).toUTCString();

        $httpProvider.interceptors.push(function() {
            return {
                'request': function(response) {
                    // put a new random secret into our CSRF-TOKEN Cookie before each request
                    document.cookie = 'CSRF-TOKEN=' + b() + ';path=/';
                    return response;
                }
            };
        });

        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    }]);
    
    app.controller('CreateEventController', function ($http) {

        var myapp = this;
        
        this.onClick = function (event) {
            $http.get('/users/security/logged').success(function (result) {
                myapp.usr = result;

                myapp.data = JSON.stringify({
                    title: event.title,
                    category: 'DANCING',
                    place: event.address,
                    maxParticipants: event.maxParticipants,
                    minParticipants: event.minParticipants,
                    startTime: event.startTime,
                    creator: myapp.usr
                });

                $http.post('/events', myapp.data);
            });
        };
    });
    
})();
