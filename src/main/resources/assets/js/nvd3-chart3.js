var app = angular.module('plunker', ['nvd3']);

app.controller('nameCtrl', function($scope) {
    $scope.name = 'Johnson';
});

app.directive('sayhello', function() {
    return {
        restrict: 'E',
//        template: '<div>Hello {{ name }}</div>',
        template: '<div ng-controller="stockCtrl" >'
                 +'  <nvd3 options="options" data="appChartData" ng-init="initChartData(2317)" ></nvd3>'
                 +'</div>',
        replace: true
    };
});

app.controller('stockCtrl', function($scope, $http) {
    $scope.options = {
        chart: {
            type: 'multiChart',
            height: 200,
            width: 300,
            margin : {
                top: 20,
                right: 45,
                bottom: 45,
                left: 65
            },
            clipEdge: true,
            duration: 100,
            grouped: true,
            showControls: false,
            xAxis: {
//                axisLabel: 'Time (ms)',
                showMaxMin: true,
                tickFormat: function(d){
//                    return d3.format(',f')(d);
                    return d3.time.format('%x')(new Date(d))
                }
            },
            yAxis1: {
//                axisLabel: 'Y Axis',
                showMaxMin: true,
                tickFormat: function(d){
                    return d3.format(',f')(d);
                }
            },
            yAxis2: {
                showMaxMin: true,
                tickFormat: function(d){
                    return d3.format(',.2f')(d);
                }
            },
            showLegend: false
        }
    };
// Chart Data //////////////////////////////////////////////////////////////
	

  	$scope.initChartData = function(stockId) {
		var path = '/service/nvd3data/st?stockId='+stockId

		$http.get(path).success(function(data) {
            $scope.appChartData = [
               convertToChartFormat(data, 'buy', 'bar', 1),
               convertToChartFormat(data, 'sell', 'bar', 1),
               convertToChartFormat(data, 'close', 'line', 2)
            ];
		    console.log($scope.appChartData);
		});
	};

    $scope.appChartTopData =[];
	$scope.initChartTopData = function(securityId) {
        var path = '/service/nvd3data/top?securityId='+securityId

        $http.get(path).success(function(data) {
//            console.log(data);
            for (var key in data) {
                var obj = [
                    convertToChartFormat(data[key], 'buy', 'bar', 1),
                    convertToChartFormat(data[key], 'sell', 'bar', 1),
                    convertToChartFormat(data[key], 'close', 'line', 2)
                ];
                var temp = {
                        key: key,
                        value: obj
                    };
                $scope.appChartTopData.push(temp);
            }

//            console.log($scope.appChartTopData);

        });
    };

//////////////////////////////////////////////////////////////////////


});

function convertToChartFormat(data, seriesName, type, yAxis){
	
	var returnValue;
	var convertedChartArray = [];
	var i;
	for (i = 0; i < data.length; i++) {
	    switch(seriesName){
	        case 'buy':
	            convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].buy});
                break;
            case 'sell':
                convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].sell});
                break;
            case 'close':
                convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].close});
                break;
	    }
	}
	returnValue = {
        key: seriesName,
        type: type,
        yAxis: yAxis,
        values: convertedChartArray
    }
	return returnValue;
};