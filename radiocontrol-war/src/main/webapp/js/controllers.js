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
