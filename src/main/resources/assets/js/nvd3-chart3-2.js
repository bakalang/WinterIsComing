
var app2 = angular.module('name', ['nvd3']);


app2.controller('nameCtrl', function($scope) {
    $scope.name = 'Johnson';
});

app2.directive('sayhello', function() {
    return {
        restrict: 'E',
        template: '<div>Hello {{ name }}</div>',
        replace: true
    };
});
