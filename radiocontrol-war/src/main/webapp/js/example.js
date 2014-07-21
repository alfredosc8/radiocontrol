function MyCtrl($scope) {
  $scope.visible = true;

  $scope.toggle = function() {
    $scope.visible = !$scope.visible;
  };
}

function MyValueCtrl($scope) {
  $scope.value = 1;

  $scope.incrementValue = function(increment) {
    $scope.value += increment;
  };
}