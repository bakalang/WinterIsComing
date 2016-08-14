var app = angular.module('plunker', ['nvd3']);

app.controller('MainCtrl', function($scope, $http) {
    $scope.options = {
        chart: {
            type: 'multiChart',
            height: 250,
            width: 500,
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
//            x2Axis: {
//                tickFormat: function(d){
//                    return d3.time.format('%x')(new Date(d))
//                }
//            },
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
            yDomain1: [0, 10000],
            yDomain2: [10, 11]
        }
//        chart: {
//            type: 'linePlusBarChart',
//            height: 400,
//            margin: {
//                top: 30,
//                right: 75,
//                bottom: 50,
//                left: 75
//            },
//            bars: {
//                forceY: [0]
//            },
//            bars2: {
//                forceY: [0]
//            },
//            showControls: true,
//            showValues: true,
//            color: ['#2ca02c', '#b8ecb8', 'darkred'],
//            x: function(d){ return d[0]; },
//            y: function(d){ return d[1]; },
//            focusEnable: false,
//            xAxis: {
//                axisLabel: 'X Axis',
//                tickFormat: function(d) {
//                    var dx = $scope.appChartData[0].values[d] && $scope.appChartData[0].values[d].x || 0;
//                    if (dx > 0) {
//                        return d3.time.format('%x')(new Date(dx))
//                    }
//                    return null;
//                }
//            },
//            x2Axis: {
//                tickFormat: function(d) {
//                    var dx = $scope.appChartData[0].values[d] && $scope.appChartData[0].values[d].x || 0;
//                    return d3.time.format('%b-%Y')(new Date(dx))
//                },
//                showMaxMin: false
//            },
//            y1Axis: {
//                axisLabel: 'Y1 Axis',
//                tickFormat: function(d){
//                    return d3.format(',f')(d);
//                },
//                axisLabelDistance: 12
//            },
//            y2Axis: {
//                axisLabel: 'Y2 Axis',
//                tickFormat: function(d) {
//                    return '$' + d3.format(',.2f')(d)
//                }
//            },
//            y3Axis: {
//                tickFormat: function(d){
//                    return d3.format(',f')(d);
//                }
//            },
//            y4Axis: {
//                tickFormat: function(d) {
//                    return '$' + d3.format(',.2f')(d)
//                }
//            }
//        }
    };
  

  
// Chart Data //////////////////////////////////////////////////////////////
	
	
  	$scope.initChartData = function(stockId) {
		//1605
		var path = '/service/nvd3data/st?stockId='+stockId
		$http.get(path).success(function(data) {
			
//			console.log(data);
            var aa = $scope.appChartDataaaa+"_"+data;
			aa = [
               convertToChartFormat(data, 'buy', 'bar', 1),
               convertToChartFormat(data, 'sell', 'bar', 1),
               convertToChartFormat(data, 'close', 'line', 2)
               ];

//			console.log($scope.appChartDataaaa+"_"+data);
		});

	};

//////////////////////////////////////////////////////////////////////


});


function convertToChartFormat(data, seriesName, type, yAxis){
	
	var returnValue;
	
	var convertedChartArray = [];
	var i;
	for (i = 0; i < data.length; i++) {
	    if(seriesName == 'buy')
		    convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].buy});
        if(seriesName == 'sell')
            convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].sell});
        if(seriesName == 'close')
            convertedChartArray.push( {x: data[i].tradeDateMinSec, y: data[i].close});
	}
	returnValue = {
        key: seriesName,
        type: type,
        yAxis: yAxis,
        values: convertedChartArray
    }
	return returnValue;
};