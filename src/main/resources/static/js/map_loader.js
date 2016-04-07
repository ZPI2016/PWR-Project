/**
 * Created by Sandra on 2016-04-07.
 */
function loadMap() {
    //here we put some outside logic to resolve user's home coordinates
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

};