/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp", []);

    var markers = {};
    var infos = {};

    var myOptions = {
        zoom: 10,
        center: new google.maps.LatLng(51.1080158802915, 17.0215279802915),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var gMap = new google.maps.Map(document.getElementById("map_container"), myOptions);

    function checkCategory(event) {
        var category = event.category.charAt(0).toUpperCase() + event.category.slice(1).toLowerCase().replace("_", " ");
        return ($('[data-on="' + category + '"]').is(':checked'));
    }

    function check_is_in_or_out(id, marker){
        if( gMap.getBounds() != undefined && gMap.getBounds().contains(marker.getPosition())){
            marker.setVisible(true);
            $('#' + id).show();
        }
        else{
            marker.setVisible(false);
            $('#' + id).hide();
        }
    }

    app.config(['$httpProvider', function($httpProvider) {
        //fancy random token
        function b(a){return a?(a^Math.random()*16>>a/4).toString(16):([1e16]+1e16).replace(/[01]/g,b)};

        document.cookie = 'CSRF-TOKEN=' + b() + '; expires=' + new Date(0).toUTCString();

        $httpProvider.interceptors.push(function() {
            return {
                'request': function(response) {
                    // put a new random secret into our CSRF-TOKEN Cookie before each request
                    document.cookie = 'CSRF-TOKEN=' + b() + ';path=/';
                    return response;
                }
            };
        });

        $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
        $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    }]);

    app.controller("EventsController", function ($http, $scope) {

        $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
            angular.forEach($scope.categories, function (element) {
                element = element.charAt(0).toUpperCase() + element.slice(1).toLowerCase().replace("_", " ");
                $('[data-on="' + element + '"]').bootstrapToggle({
                    on: element,
                    off: element
                })
                $('[data-on="' + element + '"]').change(function() {
                    checkEvents();
                })
            });
        });

        function checkEvents() {
            angular.forEach($scope.events, function (element) {
                    if (checkCategory(element)) {
                        check_is_in_or_out(element.id, markers[element.id]);
                    }
                    else {
                        markers[element.id].setVisible(false);
                        $('#' + element.id).hide();
                    }
            });
        }
        
        google.maps.event.addListener(gMap, 'bounds_changed', function() {
            for (var m in markers){
                angular.forEach($scope.events, function (element) {
                   if (element.id == m) {
                       if (checkCategory(element)) check_is_in_or_out(m, markers[m]);
                       else markers[m].setVisible(false);
                   }
                });
            }
        });

        google.maps.event.addListener(gMap, 'click', function() {
            var i = 0;
            angular.forEach(infos, function (element) {
                element.close();
                if ($('#collapse' + i).is( ":visible" )) {
                    $('#collapse' + i).collapse('hide');
                }
                i += 1;
            });
        });

        var showpin = function (element, index) {
            // return function (scope, element, attrs) {
            var latlng = new google.maps.LatLng(element.place.geoLatitude, element.place.geoLongitude);
            console.log(element.place.geoLatitude);
            console.log(element.place.geoLongitude);

            var marker = new google.maps.Marker({
                position: latlng,
                map: gMap,
                title: element.title
            });
            markers[element.id] = marker;

            var infowindow =  new google.maps.InfoWindow({
                content: '<b>' + element.title + '</b><br />' + element.startTime
            });

            infos[element.id] = infowindow;

            google.maps.event.addListener(marker, 'click', function() {
                var i = 0;
                angular.forEach(infos, function (element) {
                    element.close();
                    if ($('#collapse' + i).is( ':visible' ) && i != index) {
                        $('#collapse' + i).collapse('hide');
                    }
                    i += 1;
                });
                infowindow.open(gMap, this);
                if (!$('#collapse' + index).is( ':visible' ))
                    $('#collapse' + index).collapse('show');
            });
        };

        $http.get('/events/categories').success(function (result) {
            $scope.categories = result;

            // angular.forEach($scope.categories, function (element) {
            //     $('[data-on="' + element.charAt(0).toUpperCase() + element.slice(1).toLowerCase().replace("_", " ") + '"]').bootstrapToggle();
            // });
        });
        
        $http.get('/events/startTime', {
            params: {
                date: new Date()
            }
        }).success(function (result) {
            $scope.events = result;
            var len = $scope.events.length;

            for (var i = 0; i < len; i++) {
                $scope.events[i].startTime = new Date($scope.events[i].startTime);
                $scope.events[i].info = 'info'.concat($scope.events[i].id);
            }

            for (var i = 0; i < len; i++) {
                showpin($scope.events[i], i);
            }
        });
    });

    app.filter('categoryFormatter', function () {
        return function (categories) {
            var filtered=[];
            angular.forEach(categories, function (element) {
                filtered.push(element.charAt(0).toUpperCase() + element.slice(1).toLowerCase().replace("_", " "));
            });
            return filtered;
        };
    });

    app.filter('eventsFilter', function () {
        return function (events, options) {
            var query = options["search"] || "";
            var filtered=[];
            angular.forEach(events, function (element) {
                if (element.title.toUpperCase().indexOf(query.toUpperCase()) >= 0 && checkCategory(element)) {
                    check_is_in_or_out(element.id, markers[element.id]);
                    element.category = element.category.charAt(0).toUpperCase() + element.category.slice(1).toLowerCase().replace("_", " ");
                    filtered.push(element);
                }
                else {
                    markers[element.id].setVisible(false);
                }
            });
            return filtered;
        };
    });

    app.directive('onFinishRender', function ($timeout) {
        return {
            restrict: 'A',
            link: function (scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function () {
                        scope.$emit('ngRepeatFinished');
                    });
                }
            }
        }
    });

})();