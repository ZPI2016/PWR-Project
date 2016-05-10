/**
 * Created by Martyna on 07.04.2016.
 */

(function () {
    var app = angular.module('myApp', []);
    
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
    
    app.controller('CreateEventController', function ($http) {

        var myapp = this;
        
        this.onClick = function (event) {

            $http.get('/users/username/martyna').success(function (result) {
                myapp.usr = result;

                myapp.data = JSON.stringify({
                    title: event.title,
                    category: 'DANCING',
                    place: event.address,
                    startTime: event.startTime,
                    creator: myapp.usr
                });

                console.log(event.title);
                console.log(event.usr);

                $http({
                    method: 'POST',
                    url: '/events',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    transformRequest: function(obj) {
                        var str = [];
                        for(var p in obj)
                            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                        return str.join("&");
                    },
                    data: myapp.data
                }).then(function success(response){
                    console.log(response.headers());
                    console.log("SUCCESS");
                }, function failure(response){
                    console.log(response.headers());
                    console.log("FAILURE");
                });
            });
        };
    });
    
})();
