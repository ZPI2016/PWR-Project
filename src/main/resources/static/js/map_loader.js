/**
 * Created by Sandra on 2016-04-07.
 */
endLng =17.00;
endLat =51.00;
function loadMap() {
    //here we put some outside logic to resolve user's home/event location coordinates
    var initLng = 17.0215279802915;
    var initLat = 51.1080158802915;
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
        console.log(this.getPosition().lat());
        console.log(this.getPosition().lng());
        initLng = this.getPosition().lng();
        initLat = this.getPosition().lat();
        endLng = this.getPosition().lng();
        endLat= this.getPosition().lat();

    });


};