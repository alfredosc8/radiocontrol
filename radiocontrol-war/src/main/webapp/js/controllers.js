var radiocontrolAppControllers = angular.module('radiocontrolAppControllers', ['ngDraggable', 'mediaPlayer']);

radiocontrolAppControllers.controller('ScheduleCtrl', ['$scope', '$routeParams', 'Schedule',
    function($scope, $routeParams, Schedule) {
        Schedule.get({scheduleId: $routeParams.scheduleId}, function(data) {
            $scope.schedule = data;
        });
    }
]);

radiocontrolAppControllers.controller('ChannelCtrl', ['$scope', '$routeParams', 'Channel',
    function($scope, $routeParams, Channel) {
        Channel.get({channelId: $routeParams.channelId}, function(data) {
            $scope.channel = data;
            $scope.playlist1 = [{ src: data.stream, type: 'audio/mp3' }];
        });
    }
]);

radiocontrolAppControllers.controller('ListChannelCtrl', ['$scope', '$routeParams', '$location', 'Channel',
    function($scope, $routeParams, $location, Channel) {
        $scope.newchannel = function() {
            $location.path('/channel/new');
        };
        $scope.deletechannel = function(idx, channelId) {
            if (channelId > 0) {
                Channel.delete({channelId: channelId});
                $scope.channels.splice(idx, 1);
            }
        };
        $scope.playchannel = function(channelId) {
            $location.path('radioplayer/' + channelId);
        }
        Channel.get({}, function(data) {
            $scope.channels = data.channels;
        });
    }
]);

radiocontrolAppControllers.controller('NewChannelCtrl', ['$scope', '$location', 'Channel',
    function($scope, $location, Channel, ImageStore) {
        $scope.submit = function() {
            postData = {
                'title': $scope.title,
                'stream': $scope.stream
            }
            Channel.save({}, postData).$promise.then(function(result) {
                $location.path('/channel');
            })
        };
    }
]);