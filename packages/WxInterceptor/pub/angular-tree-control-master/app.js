var treeTestApp = angular.module('treeTestApp', ["treeControl", "ui.bootstrap"]);

treeTestApp.controller('treeCtrl', function ($scope, $http) {
	 $http({method: 'GET', url: 'http://localhost:5597/rest/wx/interceptor/pub/ui/measureRuntimeGraph'}).success(function(data) {
                $scope.treeData = data;
            });
        });
