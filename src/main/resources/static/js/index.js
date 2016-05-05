/**
 * Created by Martyna on 21.03.2016.
 */
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

        document.getElementById("geoLongitude").value = this.getPosition().lng();
        document.getElementById("geoLatitude").value = this.getPosition().lat();
        endLat = this.getPosition().lat();
        endLng= this.getPosition().lng();

    });
};
(function(){
    var app = angular.module('myApp', []);
    var login = false;

    app.controller('FormController', function ($http){

        var myapp = this;

        this.onClick = function (user) {
            user.address.geoLatitude = endLat;
            user.address.geoLongitude = endLng;
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

    app.controller('LoginController', function () {
        var data;
        var myapp = this;

        this.onClick = function (user) {
            myapp.data = JSON.stringify({
                username: user.username,
                password: user.password
            });
        };

    });

    // app.selectTab = function (chosen){
    //     this.login = chosen;
    // };



})();