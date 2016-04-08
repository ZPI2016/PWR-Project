/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp",[]);

    app.controller("UserDataController", function ($http, $scope) {
        
        $http.get('/users').success(function (result) {
            $scope.users = result;
        });
        
       /* $scope.user = {
            username : "Martyna",
            email : "m@l.com"
        };*/
        
    });
})();
