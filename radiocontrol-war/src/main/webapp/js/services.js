var radiocontrolAppServices = angular.module('radiocontrolAppServices', ['ngResource']);

radiocontrolAppServices.factory('Schedule', ['$resource',
  function($resource) {
    return $resource('api/v1/schedule/:scheduleId', {}, {});
  }]);

radiocontrolAppServices.factory('Channel', ['$resource', 'AuthService',
  function($resource, AuthService) {
    ownerId = AuthService.getCurrentUser().id;
    return $resource('api/v1/channel/:channelId', {}, {
      get: { method:'GET', params: { filter: 'owner', filterval: ownerId } },
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

radiocontrolAppServices.factory('AuthService', function() {
    var authServiceInstance = {};

    authServiceInstance.getCurrentUser = function() {
        return {
            "id": 'jonas.birme@gmail.com',
            "first": 'Jonas',
            "last": 'Birmé'
        };
    };
    authServiceInstance.authenticated = function() {
        return true;
    };
    return authServiceInstance;
});
