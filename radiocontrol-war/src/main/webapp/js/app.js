var radiocontrolApp = angular.module('radiocontrolApp', [
    'ngRoute',
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
            otherwise({
                redirectTo: '/schedule/0'
            });
    }]);