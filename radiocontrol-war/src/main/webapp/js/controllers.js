var radiocontrolApp = angular.module('radiocontrolApp', []);

radiocontrolApp.controller('ScheduleCtrl', function ($scope) {
    $scope.schedulerows = [
      { 'starttime': '10:00:00',
        'type': 'live',
        'description': 'Test testsson' },
      { 'starttime': '12:00:00',
        'type': 'music',
        'description': 'Testrad' }
    ];
});