var app = angular.module('plunker', ['nvd3']);

app.controller('MainCtrl', function($scope, $http) {
    $scope.options = {
        chart: {
            type: 'linePlusBarChart',
            height: 500,
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
            color: ['#2ca02c', 'darkred'],
            x: function(d){ return d[0]; },
            y: function(d){ return d[1]; }
        }
    };
  

  
// Chart Data //////////////////////////////////////////////////////////////
	
	
  	$scope.initChartData = function() {
		
		$http.get('/service/nvd3data/st').success(function(data) {
			
			console.log(data);
			$scope.appChartData =[
			data.quantity,
			data.price
			];
//			$scope.appChartData = [
//			                       convertToChartFormat(data.xAxisData, data.a, 'A'),
//			                       convertToChartFormat(data.xAxisData, data.b, 'B'),
//			                       convertToChartFormat(data.xAxisData, data.c, 'C'),
//			                       convertToChartFormat(data.xAxisData, data.d, 'D')
//			                       ];

			console.log($scope.appChartData);
		});

	};

//////////////////////////////////////////////////////////////////////


});


function convertToChartFormat(xData, yData, seriesName){
	
	var returnValue;
	
	var convertedChartArray = [];
	var i;
	for (i = 0; i < xData.length; i++) {
		convertedChartArray.push( {x: xData[i], y: yData[i]});

	}
	returnValue = {
        key: seriesName,
        values: convertedChartArray
    }
	return returnValue;
};