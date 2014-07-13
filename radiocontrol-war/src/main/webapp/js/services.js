var radiocontrolAppServices = angular.module('radiocontrolAppServices', ['ngResource']);

radiocontrolAppServices.factory('Schedule', ['$resource',
  function($resource) {
    return $resource('api/v1/schedule/:scheduleId', {}, {
      query: { method:'GET', params: { scheduleId: 'schedule' } }
    });
  }]);