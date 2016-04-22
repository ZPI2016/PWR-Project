/**
 * Created by Martyna on 07.04.2016.
 */

(function () {
    var app = angular.module('myApp', []);
    
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

                $http.post('/events', myapp.data);
            });
        };
    });
    
})();
