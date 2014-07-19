var radiocontrolAppServices = angular.module('radiocontrolAppServices', ['ngResource']);

radiocontrolAppServices.factory('Schedule', ['$resource',
  function($resource) {
    return $resource('api/v1/schedule/:scheduleId', {}, {
      query: { method:'GET', params: { scheduleId: 'schedule' } }
    });
  }]);

radiocontrolAppServices.factory('Channel', ['$resource',
  function($resource) {
    return $resource('api/v1/channel/:channelId', {}, {
      query: { method:'GET', params: { channelId: 'channel' } },
      put: { method: 'PUT', params: { channelId: 'channel' }, isArray: false }
    });
  }]);

  radiocontrolAppServices.factory('ImageStore', ['$resource',
    function($resource) {
        return $resource('api/v1/imagestore', {}, {
            query: { method: 'GET', params: {} }
        });
    }
  ]);

radiocontrolAppServices.factory('DiscoveryService', ['$resource',
    function($resource) {
        return $resource('api/v1/discover', {}, {
            query: { method:'GET', params: {} }
        });
}]);