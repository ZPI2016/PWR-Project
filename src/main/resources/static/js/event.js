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
    
    app.controller('CreateEventController', function ($http) {

        var myapp = this;
        
        this.onClick = function (event) {
            event.address.geoLatitude = endLat;
            event.address.geoLongitude = endLng;
            $http.get('/users/username/martyna').success(function (result) {;
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


