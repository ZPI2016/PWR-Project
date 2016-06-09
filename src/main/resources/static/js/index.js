/**
 * Created by Martyna on 21.03.2016.
 */
angular.module('index', ['ngRoute'])

    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/home', {
            templateUrl: 'login.html',
            controller: 'loginController',
            controllerAs: 'loginCtrl'
        }).when('/register', {
            templateUrl: 'register.html',
            controller: 'registerController',
            controllerAs: 'registerCtrl'
        }).otherwise('/home')
    })

    .config(['$httpProvider', function ($httpProvider) {
        //fancy random token
        function b(a) {
            return a ? (a ^ Math.random() * 16 >> a / 4).toString(16) : ([1e16] + 1e16).replace(/[01]/g, b)
        };

        document.cookie = 'CSRF-TOKEN=' + b() + '; expires=' + new Date(0).toUTCString();

        $httpProvider.interceptors.push(function () {
            return {
                'request': function (response) {
                    // put a new random secret into our CSRF-TOKEN Cookie before each request
                    document.cookie = 'CSRF-TOKEN=' + b() + ';path=/';
                    return response;
                }
            };
        });

        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    }])

    .service("AlertService", function () {
        var justRegistered = false;
        var loggingError = false;
        return {
            isJustRegistered: function () {
                return justRegistered;
            },
            setJustRegistered: function (val) {
                justRegistered = val;
            },
            hasLoginFailed: function () {
                return loggingError;
            },
            setLoggingError: function (val) {
                loggingError = val;
            }
        };
    })

    .controller('registerController', function ($scope, $http, $location, AlertService) {
        var myapp = this;

        var initLng = 17.0215279802915;
        var initLat = 51.1080158802915;
        var endLng = initLng;
        var endLat = initLat;


        //here we put some outside logic to resolve user's home/event location coordinates
        var latlng = new google.maps.LatLng(51.1080158802915, 17.0215279802915);
        var myOptions = {
            zoom: 10,
            center: latlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("register_map_container"), myOptions);

        var marker = new google.maps.Marker({
            position: latlng,
            map: map,
            draggable: true,
            title: "Wroclove",
            icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
        });
        google.maps.event.addListener(marker, 'dragend', function (a) {
            initLng = this.getPosition().lng();
            initLat = this.getPosition().lat();
            document.getElementById("geoLongitude").value = this.getPosition().lng();
            document.getElementById("geoLatitude").value = this.getPosition().lat();
            endLat = this.getPosition().lat();
            endLng = this.getPosition().lng();

        });

        $scope.registationFailed = false;

        this.onClick = function (user) {
            var place = {
                geoLongitude: endLng,
                geoLatitude: endLat
            }
            user.address = place;
            $http.post('/users', user)
                .then(function success(response) {
                    $location.path("/home");
                    AlertService.setJustRegistered(true);;
                    AlertService.setLoggingError(false);
                    $('#li-register').removeClass("active");
                    $('#li-login').addClass("active");
                }, function failure(response) {
                    $scope.registrationFailed = true;
                });
        };
    })

    .controller('loginController', function ($scope, $http, $location, $routeParams, $window, AlertService) {
        $scope.justRegistered = AlertService.isJustRegistered();
        $scope.loggingError = AlertService.hasLoginFailed();

        this.onClick = function (user) {
            AlertService.setJustRegistered(false);
            $scope.justRegistered = AlertService.isJustRegistered();
            $http({
                method: 'POST',
                url: '/login',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (obj) {
                    var str = [];
                    for (var p in obj)
                        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
                data: user
            }).then(function success(response) {
                window.location.href ="/main";
                AlertService.setLoggingError(false);
                $scope.loggingError = AlertService.hasLoginFailed();
            }, function failure(response) {
                $location.path("/home");
                AlertService.setLoggingError(true);
                $scope.loggingError = AlertService.hasLoginFailed();
            });
        };
    })
