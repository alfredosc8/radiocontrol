var radiocontrolApp = angular.module('radiocontrolApp', [
    'ngRoute',
    'mediaPlayer',
    'flow',
    'xeditable',
    'ui.bootstrap',
    'radiocontrolAppControllers',
    'radiocontrolAppServices',
]);

radiocontrolApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
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
            when('/manage', {
                templateUrl: 'partials/manage.html'
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
     'http://aws*.mxt.se:8000/**',
     '*'
   ]);
});

radiocontrolApp.run(function(editableOptions, editableThemes) {
    editableThemes.bs3.inputClass = 'input-sm';
    editableThemes.bs3.buttonsClass = 'btn-sm';
    editableOptions.theme = 'bs3'; // bootstrap3 theme. Can be also 'bs2', 'default'
});