/**
 * Created by Sandra on 2016-04-07.
 */
var initLng = 17.0215279802915;
var initLat = 51.1080158802915;
var endLng = initLng;
var endLat = initLat;

function loadMap() {
    //here we put some outside logic to resolve user's home/event location coordinates
    var latlng = new google.maps.LatLng(51.1080158802915, 17.0215279802915);
    var myOptions = {
        zoom: 10,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    var map = new google.maps.Map(document.getElementById("register_map_container"),myOptions);

    var marker = new google.maps.Marker({
        position: latlng,
        map: map,
        draggable: true,
        title:"Wroclove",
        icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
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