/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp",[]);

    app.controller("UserDataController", function ($scope) {
        $scope.user = {
            username : "Martyna",
            email : "m@l.com"
        };

    });
})();