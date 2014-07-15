var radiocontrolApp = angular.module('radiocontrolApp', [
    'ngRoute',
    'ngDraggable',
    'mediaPlayer',
    'flow',
    'radiocontrolAppControllers',
    'radiocontrolAppServices'
]);

radiocontrolApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/schedule/:scheduleId', {
                templateUrl: 'partials/schedule.html',
                controller: 'ScheduleCtrl'
            }).
            when('/channel/', {
                templateUrl: 'partials/list-channel.html',
                controller: 'ListChannelCtrl'
            }).
            when('/channel/new', {
                templateUrl: 'partials/new-channel.html',
                controller: 'NewChannelCtrl'
            }).
            when('/channel/:channelId', {
                templateUrl: 'partials/channel.html',
                controller: 'ChannelCtrl'
            }).
            when('/radioplayer/:channelId', {
                templateUrl: 'partials/player.html',
                controller: 'ChannelCtrl'
            }).
            otherwise({
                redirectTo: '/radioplayer/0'
            });
    }]);

radiocontrolApp.config(function($sceDelegateProvider) {
$sceDelegateProvider.resourceUrlWhitelist([
     // Allow same origin resource loads.
     'self',
     // Allow loading from our assets domain.  Notice the difference between * and **.
     'http://aws*.mxt.se:8000/**'
   ]);
});