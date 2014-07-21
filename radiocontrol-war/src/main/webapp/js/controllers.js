var radiocontrolAppControllers = angular.module('radiocontrolAppControllers', ['mediaPlayer']);

radiocontrolAppControllers.controller('ApplicationCtrl', ['$scope', 'AuthService',
    function($scope, AuthService) {
        $scope.currentuser = AuthService.getCurrentUser();
        $scope.authenticated = AuthService.authenticated();
    }
]);

radiocontrolAppControllers.controller('ScheduleCtrl', ['$scope', '$routeParams', 'Schedule',
    function($scope, $routeParams, Schedule) {
        Schedule.get({scheduleId: $routeParams.scheduleId}, function(data) {
            $scope.schedule = data;
        });
    }
]);

radiocontrolAppControllers.controller('ChannelCtrl', ['$scope', '$routeParams', '$location', 'Channel',
    function($scope, $routeParams, $location, Channel) {
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
        };
        $scope.updatechannel = function(newchannel) {
            Channel.put({ channelId: newchannel.id }, newchannel);
        };
        Channel.get({}, function(data) {
            $scope.channels = data.channels;
        });
    }
]);

radiocontrolAppControllers.controller('NewChannelCtrl', ['$scope', '$location', '$window', 'Channel', 'AuthService',
    function($scope, $location, $window, Channel, AuthService) {
        $scope.submit = function() {
            postData = {
                'title': $scope.title,
                'ownerid': AuthService.getCurrentUser().id,
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

radiocontrolAppControllers.controller('PlayerCtrl', ['$scope', '$location', 'DiscoveryService',
    function($scope, $location, DiscoveryService) {
        $scope.seekfwd = function() {
            DiscoveryService.get({}, function(data) {
                channelId = data.id;
                $location.path('/radioplayer/' + channelId);
            });
        };
}]);

radiocontrolAppControllers.controller('NavbarCtrl', ['$scope', '$location', '$modal',
    function($scope, $location, $modal) {
        $scope.navChannels = function() {
            $location.path('/channel/');
        };
        $scope.navHome = function() {
            $location.path('/manage/');
        };
        $scope.navManager = function() {
            $location.path('/manage');
        };
        $scope.navAbout = function() {
            var modalInstance = $modal.open({
                templateUrl: 'partials/about.html',
                controller: AboutModalCtrl
            });
        };
}]);

var AboutModalCtrl = function($scope, $modalInstance) {
    $scope.ok = function() {
        $modalInstance.dismiss('cancel');
    };
};

radiocontrolAppControllers.controller('ShareCtrl', ['$scope', '$location',
    function($scope, $location, DiscoveryService) {
        $scope.fbshare = function(channelId) {
            FB.ui({
                method: 'share',
                href: 'http://play.mxt.se/shareme.jsp?channelId=' + channelId
            }, function(response) {
            });
        };
}]);
