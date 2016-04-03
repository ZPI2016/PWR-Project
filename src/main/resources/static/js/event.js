/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp",[]);

    app.controller("EventController", function ($http, $scope) {

        // CHECK: not sure if this will work with the EventController
        // to make it work put the value of RequestMapping which returns a list of events in the brackets of get mathod
        // $scope.events = $http.get('');

        $scope.events = [
            {
                title: "Afternoon Squash",
                category: "SPORTS"
            },
            {
                title: "Salsa class",
                category: "DANCING"
            }
        ];
    });
})();