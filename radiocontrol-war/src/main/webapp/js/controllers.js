var radiocontrolAppControllers = angular.module('radiocontrolAppControllers', ['ngDraggable', 'mediaPlayer']);

radiocontrolAppControllers.controller('ScheduleCtrl', ['$scope', '$routeParams', 'Schedule',
    function($scope, $routeParams, Schedule) {
        Schedule.get({scheduleId: $routeParams.scheduleId}, function(data) {
            $scope.schedule = data;
        });
    }
]);

radiocontrolAppControllers.controller('ChannelCtrl', ['$scope', '$routeParams', '$location', 'Channel',
    function($scope, $routeParams, $location, Channel) {
        $scope.navManager = function() {
            $location.path('/manage');
        }
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

radiocontrolAppControllers.controller('NewChannelCtrl', ['$scope', '$location', '$window', 'Channel',
    function($scope, $location, $window, Channel, ImageStore) {
        $scope.submit = function() {
            postData = {
                'title': $scope.title,
                'stream': $scope.stream,
                'image': $scope.image
            }
            Channel.save({}, postData).$promise.then(function(result) {
                $location.path('/channel');
            })
        };
        $scope.cancel = function() {
            $window.history.back();
        };
    }
]);

radiocontrolAppControllers.controller('ManageCtrl', ['$scope', '$location',
    function($scope, $location) {
        $scope.navChannel = function() {
            $location.path('/channel/');
        };
    }
]);

radiocontrolAppControllers.controller('NavbarCtrl', ['$scope', '$location',
    function($scope, $location) {
        $scope.navChannels = function() {
            $location.path('/channel/');
        };
        $scope.navHome = function() {
            $location.path('/manage/');
        };
}]);
