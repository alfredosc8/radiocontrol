var radiocontrolApp = angular.module('radiocontrolApp', []);

radiocontrolApp.controller('ScheduleCtrl', function ($scope) {
    $scope.schedulerows = [
      { 'start': '10:00:00',
        'type': 'live',
        'description': 'Test testsson' },
      { 'start': '12:00:00',
        'type': 'music',
        'description': 'Testrad' }
    ];
});