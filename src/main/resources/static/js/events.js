/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp", []);

    app.controller("EventsController", function ($http, $scope) {

        // CHECK: not sure if this will work with the EventController
        // to make it work put the value of RequestMapping which returns a list of events in the brackets of get mathod
        $http.get('/events').success(function (result) {
            $scope.events = result;
        })
    });

    app.filter('searchTitle', function () {
        return function (item, query) {
            // var filtered = [];
            // if(items==null) return items;
            // for( var i = 0; i<Object.keys(items).length; i++){
            //     var item = items[0];
            //     if(item.title.indexOf(query)!=-1){
            //         filtered.push(item);
            //     }
            //     console.log(item.title);
            // }
            return item.title.indexOf(query)!=-1;
        };
    });

})();