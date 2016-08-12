var app = angular.module('plunker', ['nvd3']);

app.controller('MainCtrl', function($scope, $http) {
    $scope.options = {
        chart: {
            type: 'linePlusBarChart',
            height: 400,
            margin: {
                top: 30,
                right: 75,
                bottom: 50,
                left: 75
            },
            bars: {
                forceY: [0]
            },
            bars2: {
                forceY: [0]
            },
            showControls: true,
            showValues: true,
            color: ['#2ca02c', '#b8ecb8', 'darkred'],
            x: function(d){ return d[0]; },
            y: function(d){ return d[1]; },
            xAxis: {
                axisLabel: 'X Axis',
                tickFormat: function(d) {
                    var dx = $scope.appChartData[0].values[d] && $scope.appChartData[0].values[d].x || 0;
                    if (dx > 0) {
                        return d3.time.format('%x')(new Date(dx))
                    }
                    return null;
                }
            },
            x2Axis: {
                tickFormat: function(d) {
                    var dx = $scope.appChartData[0].values[d] && $scope.appChartData[0].values[d].x || 0;
                    return d3.time.format('%b-%Y')(new Date(dx))
                },
                showMaxMin: false
            },
            y1Axis: {
                axisLabel: 'Y1 Axis',
                tickFormat: function(d){
                    return d3.format(',f')(d);
                },
                axisLabelDistance: 12
            },
            y2Axis: {
                axisLabel: 'Y2 Axis',
                tickFormat: function(d) {
                    return '$' + d3.format(',.2f')(d)
                }
            },
            y3Axis: {
                tickFormat: function(d){
                    return d3.format(',f')(d);
                }
            },
            y4Axis: {
                tickFormat: function(d) {
                    return '$' + d3.format(',.2f')(d)
                }
            }
        }
    };
  

  
// Chart Data //////////////////////////////////////////////////////////////
	
	
  	$scope.initChartData = function() {
		
		$http.get('/service/nvd3data/st').success(function(data) {
			
			console.log(data);
			$scope.appChartData = [
               convertToChartFormat(data, 'buy', true),
               convertToChartFormat(data, 'sell', true),
               convertToChartFormat(data, 'close', false)
               ];

			console.log($scope.appChartData);
		});

	};

//////////////////////////////////////////////////////////////////////


});


function convertToChartFormat(data, seriesName, isBar){
	
	var returnValue;
	
	var convertedChartArray = [];
	var i;
	for (i = 0; i < data.length; i++) {
	    if(seriesName == 'buy')
		    convertedChartArray.push( {0: data[i].tradeDateMinSec, 1: data[i].buy});
        if(seriesName == 'sell')
            convertedChartArray.push( {0: data[i].tradeDateMinSec, 1: data[i].sell});
        if(seriesName == 'close')
            convertedChartArray.push( {0: data[i].tradeDateMinSec, 1: data[i].close});
	}
	returnValue = {
        key: seriesName,
        bar: isBar,
        values: convertedChartArray
    }
	return returnValue;
};