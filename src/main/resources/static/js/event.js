/**
 * Created by Martyna on 07.04.2016.
 */
var initLng = 17.0215279802915;
var initLat = 51.1080158802915;
var endLng =0.0;
var endLat =0.0;
function loadMap() {
    //here we put some outside logic to resolve user's home/event location coordinates
    var latlng = new google.maps.LatLng(51.1080158802915, 17.0215279802915);
    var myOptions = {
        zoom: 10,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("map_container"),myOptions);

    var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: true,
        title:"Wroclove"
    });
    google.maps.event.addListener(marker, 'dragend', function(a) {
        initLng = this.getPosition().lng();
        initLat = this.getPosition().lat();
        document.getElementById("geoLongitude").value = this.getPosition().lng();
        document.getElementById("geoLatitude").value = this.getPosition().lat();
        endLat = this.getPosition().lat();
        endLng= this.getPosition().lng();

    });
};

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

    app.controller('CreateEventController', function ($http, $scope) {
        var myapp = this;

        $http.get('/events/categories').success(function (result) {
            $scope.categories = result;
        });

        this.onClick = function (event) {
            event.address.geoLatitude = endLat;
            event.address.geoLongitude = endLng;
            $http.get('/users/security/logged').success(function (result) {
                myapp.usr = result;

                myapp.data = JSON.stringify({
                    title: event.title,
                    category: event.category.toUpperCase(),
                    place: event.address,
                    startTime: event.startTime,
                    creator: myapp.usr,
                    minParticipants: event.minParticipants,
                    maxParticipants: event.maxParticipants
                });

                console.log(event.title);
                console.log(event.usr);

                $http.post('/events', myapp.data);
            });
        };
    });

    app.filter('categoryFormatter', function () {
        return function (categories) {
            var filtered=[];
            angular.forEach(categories, function (element) {
                filtered.push(element.charAt(0).toUpperCase() + element.slice(1).toLowerCase());
            });
            return filtered;
        };
    });
    
})();


