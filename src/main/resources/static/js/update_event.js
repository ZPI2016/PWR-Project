/**
 * Created by Sandra on 2016-05-05.
 */
(function () {
    var app = angular.module('myApp', []);


    app.controller('EventUpdateController', function ($scope, $http) {

        $http.get('/events/55636bf2-dcb6-4145-b530-efdf13c418ee').success(function (result) {
            $scope.eventData = result;
            initializeFormFields(result);
        })
            .error(function (data, status, header, config) {
                console.log("Error during getting event data.");
                $scope.ServerResponse = htmlDecode("Data: " + data +
                "\n\n\n\nstatus: " + status +
                "\n\n\n\nheaders: " + header +
                "\n\n\n\nconfig: " + config);
            });
        var myapp = this;
        this.onClick = function (event) {
            $http.get('/events/' + event.id).success(function (result) {
                oldUserData = {
                    title: result.title,
                    category: result.category,
                    place: result.place,
                    startTime: result.startTime,
                    endTime: result.endTime,
                    minParticipants: result.minParticipants,
                    maxParticipants: result.maxParticipants,
                    geLatitude: result.geoLatitude,
                    geoLongitude: result.geoLongitude,
                    radius: result.radius
                };
            })
                .error(function (result) {
                    console.log("Error during getting event data.");
                });
            myapp.data = JSON.stringify({
                title: event.title,
                category: event.category,
                place: event.place,
                startTime: event.startTime,
                endTime: event.endTime,
                minParticipants: event.minParticipants,
                maxParticipants: event.maxParticipants,
                geLatitude: event.geoLatitude,
                geoLongitude: event.geoLongitude,
                radius: event.radius
            });
            $http.put('/events/568a25ba-daf8-4782-becd-f19bf0c00026', myapp.data).success(function (data, status, headers) {
                console.log("SUCCESS");
                $scope.ServerResponse = data;

            })
                .error(function (data, status, header, config) {
                    console.log("Error during updating event data.");
                    $scope.ServerResponse = htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
                });
        };


    })
    function initializeFormFields(result) {
        document.getElementById("title").value = result.title;
        document.getElementById("category").value = result.category;
        document.getElementById("place").value = result.place;
        document.getElementById("startTime").value = result.startTime;
        document.getElementById("endTime").value = result.endTime;
        document.getElementById("minParticipants").value = result.minParticipants;
        document.getElementById("maxParticipants").value = result.maxParticipants;
        document.getElementById("geoLatitude").value = result.geoLatitude;
        document.getElementById("geoLongitude").value = result.geoLongitude;

    }
}
());