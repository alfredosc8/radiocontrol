var radiocontrolAppControllers = angular.module('radiocontrolAppControllers', []);

/*
radiocontrolAppControllers.controller('ScheduleCtrl', ['$scope', '$routeParams', 'Schedule',
    function($scope, $routeParams, Schedule) {
        Schedule.get({scheduleId: $routeParams.scheduleId}, function(data) {
            $scope.schedulerows = data.schedulerows;
        });
    }
]);
*/

radiocontrolAppControllers.controller('ScheduleCtrl', function ($scope, $routeParams, $http) {
    $http.get('api/v1/schedule/' + $routeParams.scheduleId).success(function(data) {
        $scope.schedule = data;
    });
});