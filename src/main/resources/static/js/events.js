/**
 * Created by Martyna on 01.04.2016.
 */

(function () {
    var app = angular.module("myApp", []);

    var markers = {};
    var infos = {};
    var events = [];
    var markerListener;
    var draggableMarker;
    var loggedUser;
    var query;

    var initLng = 17.0215279802915;
    var initLat = 51.1080158802915;
    var endLng = initLng;
    var endLat = initLat;

    var myOptions = {
        zoom: 10,
        center: new google.maps.LatLng(51.1080158802915, 17.0215279802915),
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var gMap = new google.maps.Map(document.getElementById("map_container"), myOptions);

    function checkFiltering(event) {
        var notFiltered = event.title.toUpperCase().indexOf(query.toUpperCase()) >= 0;

        var len = event.participants.length;
        var doesNotTakePartIn = true;
        for (var i = 0; i < len; i++) {
            if(event.participants[i].id === loggedUser.id) {
                doesNotTakePartIn = false;
                break;
            }
        }

        if (!$('#takes-part').is(':checked') && !doesNotTakePartIn) {
            return false;
        }

        if (event.creator.id === loggedUser.id) {
            notFiltered = notFiltered && $('#show-mine').is(':checked');
        } else {
            notFiltered = notFiltered && $('#show-others').is(':checked');
        }

        var category = event.category.charAt(0).toUpperCase() + event.category.slice(1).toLowerCase().replace("_", " ");
        notFiltered = notFiltered && ($('[data-on="' + category + '"]').is(':checked'));
        return notFiltered;
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

    var formMap;
        $scope.$on('ngRepeatFinished', function(ngRepeatFinishedEvent) {
            $('#show-mine').bootstrapToggle({
                on: 'Created events',
                off: 'Created events'
            });
            $('#show-mine').change(function() {
                $scope.checkEvents();
            });
            $('#show-others').bootstrapToggle({
                on: 'Show other events',
                off: 'Show other events'
            });
            $('#show-others').change(function() {
                $scope.checkEvents();
            });
            $('#takes-part').bootstrapToggle({
                on: 'Upcoming events',
                off: 'Upcoming events'
            });
            $('#takes-part').change(function() {
                $scope.checkEvents();
            });
            angular.forEach($scope.categories, function (element) {
                element = element.charAt(0).toUpperCase() + element.slice(1).toLowerCase().replace("_", " ");
                $('[data-on="' + element + '"]').bootstrapToggle({
                    on: element,
                    off: element
                })
                $('[data-on="' + element + '"]').change(function() {
                    $scope.checkEvents();
                })
            });
        });

        $scope.checkEvents = function () {
            angular.forEach($scope.events, function (element) {
                    if (checkFiltering(element)) {
                        check_is_in_or_out(element.id, markers[element.id]);
                    }
                    else {
                        markers[element.id].setVisible(false);
                        $('#' + element.id).hide();
                    }
            });
        };
        
        google.maps.event.addListener(gMap, 'bounds_changed', function() {
            for (var m in markers){
                angular.forEach($scope.events, function (element) {
                   if (element.id == m) {
                       if (checkFiltering(element)) check_is_in_or_out(m, markers[m]);
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

        var eventsCtrl = this;
        $scope.editing = false;

        var showpin = function (element, index) {
            // return function (scope, element, attrs) {
            var latlng = new google.maps.LatLng(element.place.geoLatitude, element.place.geoLongitude);
            console.log(element.place.geoLatitude);
            console.log(element.place.geoLongitude);

            var marker = new google.maps.Marker({
                position: latlng,
                map: gMap,
                title: element.title,
                icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
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
                infos[element.id].open(gMap, this);
                if (!$('#collapse' + index).is( ':visible' ))
                    $('#collapse' + index).collapse('show');
            });
        };

        $http.get('/users/security/logged').success(function (result) {
            loggedUser = result;
            $scope.loggedUser = result;
            var latlng = new google.maps.LatLng(result.address.geoLatitude, result.address.geoLongitude);
            var userMarker = new google.maps.Marker({
                position: latlng,
                map: gMap,
                title: "Your location",
                icon: 'http://maps.google.com/intl/en_us/mapfiles/ms/micons/red-dot.png'
            });
            userMarker.setVisible(true);

            var window =  new google.maps.InfoWindow({
                content: '<b>Your location</b>'
            });

            google.maps.event.addListener(userMarker, 'click', function() {
               window.open(gMap, this);
            });
        });

        $http.get('/events/categories').success(function (result) {
            $scope.categories = result;
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
                // $scope.events[i].colourPins = false;
                $scope.events[i].info = 'info'.concat($scope.events[i].id);
            }

            for (var i = 0; i < len; i++) {
                showpin($scope.events[i], i);
            }
        });

        eventsCtrl.openForm = function ($uibModal) {
            console.log('opening pop up');

            var uibModalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'eventPopUp.html',
                controller: 'PopupCont',
            });
        };

        eventsCtrl.colourPins = function (index, $scope) {
            console.log(index);
            var len = $scope.events.length;

            for (var i = 0; i < len; i++) {
                var divId = '#collapse' + index;
                if ($scope.events[i].id === index) {
                    // $(divId).removeClass('out');
                    // $(divId).toggle();
                    (divId).collapse('show');
                    $scope.events[i].visible = true;
                    markers[element.id].icon = 'http://maps.google.com/mapfiles/ms/icons/green-dot.png';
                }
                else {
                    $scope.events[i].visible = false;
                    // $(divId).toggle();
                    // $(divId).removeClass('in');
                    // $(divId).addClass('collapse out');
                    $('.panel-collapse.in').collapse('hide');
                    markers[element.id].icon = 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png';
                }
            }
        };

        eventsCtrl.shouldShowRegister = function (event) {
            return loggedUser && eventsCtrl.doesNotTakePartIn(event) && event.participants.length < event.maxParticipants;
        }

        eventsCtrl.shouldShowLeave = function (event) {
            return loggedUser && !eventsCtrl.doesNotTakePartIn(event);
        }

        eventsCtrl.shouldShowCRUDButtons = function (event) {
            return loggedUser.id === event.creator.id;
        }

        eventsCtrl.doesNotTakePartIn = function (event) {
            var len = event.participants.length;
            var doesNotTakePartIn = true;
            for (var i = 0; i < len; i++) {
                if(event.participants[i].id === loggedUser.id) {
                    doesNotTakePartIn = false;
                    break;
                }
            }
            return doesNotTakePartIn;
        };

        eventsCtrl.unregisterParticipant = function (event) {
            $http.delete('/events/' + event.id + '/participants/' + loggedUser.id).success(function (result) {
                var len = $scope.events.length;
                result.startTime = new Date(result.startTime);
                for (var i = 0; i < len; i++) {
                    if ($scope.events[i].id === result.id) {
                        $scope.events[i] = result;
                        break;
                    }
                }
            });
        };

        eventsCtrl.registerParticipant = function (event) {
            $http.post('/events/' + event.id + '/participants', loggedUser).success(function (result) {
                var len = $scope.events.length;
                result.startTime = new Date(result.startTime);
                for (var i = 0; i < len; i++) {
                    if ($scope.events[i].id === result.id) {
                        $scope.events[i] = result;
                        break;
                    }
                }
            });
        };

        eventsCtrl.deleteEvent = function (event) {
            var mapping = '/events/' + event.id.toString();
            $http.delete(mapping).success(function () {
                console.log("event deleted");
                $scope.events.splice($scope.events.indexOf(event), 1);
                events.splice($scope.events.indexOf(event), 1);
                markers[event.id].setMap(null);
                delete markers[event.id];
            });
        };

        eventsCtrl.onClick = function (eventId, event) {

            console.log("click: "+eventId);
            event.id = eventId;
            event.category = $('#category').val().toUpperCase().replace(" ", "_");
            var place = {
                geoLongitude: endLng,
                geoLatitude: endLat
            }

            event.place = place;

            $http.put(('/events/' + eventId), event).success(function (data, status, headers) {
                // $scope.events;
                console.log("SUCCESS");
                $scope.ServerResponse = data;
                data.startTime = new Date(data.startTime);
                var len = $scope.events.length;
                for (var i = 0; i < len; i++)
                {
                    if ($scope.events[i].id === data.id) {
                        $scope.events[i] = data;
                        var latlng = new google.maps.LatLng(data.place.geoLatitude, data.place.geoLongitude);
                        markers[data.id].setPosition(latlng);
                        infos[data.id].content = '<b>' + data.title + '</b><br />' + data.startTime;
                    }
                }
                $('#editModal' + eventId).modal('hide');
            })
                .error(function (data, status, header, config) {
                    console.log("Error during updating event data.");
                });


        };

        eventsCtrl.updateMap=function (event) {

            // $('#title').val(event.title);
            // $('#category').val(event.category);
            // $('#startTime').val(event.startTime.toISOString().slice(0, 19));
            // $('#minParticipants').val(event.minParticipants);
            // $('#maxParticipants').val(event.maxParticipants);
            // $('#geoLongitude').val(event.place.geoLongitude);
            // $('#geoLatitude').val(event.place.geoLatitude);

            formMap = new google.maps.Map(document.getElementById("form_map_container" + event.id), myOptions);
            console.log("setting map");

            var latlng = new google.maps.LatLng(event.place.geoLatitude, event.place.geoLongitude);
            endLat = event.place.geoLatitude;
            endLng = event.place.geoLongitude;
            var marker = new google.maps.Marker({
                position: latlng,
                map: formMap,
                title: event.title,
                icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
            });
            marker.setDraggable(true);
            draggableMarker = marker;
            markerListener = google.maps.event.addListener(marker, 'dragend', function(a) {
                console.log(this.getPosition().lat());
                console.log(this.getPosition().lng());
                // $("#editModal" + event.id + " #geoLongitude").val(this.getPosition().lng());
                // $("#editModal" + event.id + " #geoLatitude").val(this.getPosition().lat());
                endLng = this.getPosition().lng();
                endLat= this.getPosition().lat();

            });
            $('#editModal' + event.id).on('shown.bs.modal', function () {
                var currCenter = formMap.getCenter();
                google.maps.event.trigger(formMap, 'resize');
                formMap.setCenter(currCenter);
            });


        };


    });




    app.controller('PopupCont', ['$scope', '$uiModal', function ($scope, $uibModalInstance) {
        $scope.cancel = function () {
            $uibModalInstance.dismiss('cancel');
        };
    }]);

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
            query = options["search"] || "";
            var filtered=[];
            angular.forEach(events, function(element) {
                element.category = element.category.charAt(0).toUpperCase() + element.category.slice(1).toLowerCase().replace("_", " ");
                filtered.push(element);
            });
            $('#eventsCtrlId').scope().checkEvents();
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