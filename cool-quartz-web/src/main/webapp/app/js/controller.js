'use strict';

angular.module('coolApp.controller', [])
	.controller('JobHistoryController', ['$scope','$http', function($scope, $http){
		
		
		
		$scope.fetchAll = function(){
			$http({method:'GET', url:'~cool-quartz/history'})
				.success(function(data, status, headers, config){
					$scope.result = data ;
					$scope.totalJobHistory = $scope.result.length;
					$scope.histories = $scope.result.content;
					$("#example2").DataTable();					
				})
				.error(function(data, status, headers, config){
					//log('Ajax Error ');
				});
		}
			
		$scope.refresh = function(){
			$scope.fetchAll();
		}
		
		$scope.fetchAll();
		
		
	}])
	.controller('JobAdminController', ['$scope', '$http', function($scope, $http){
		
		$scope.fetchAll = function(){
			$http({method:'GET', url:'~cool-quartz/triggers'})
				.success(function(data , status, headers, config){
					$scope.triggers = data;
				})
				.error(function(data, status, headers , config){
					//log('error');
				});
		}
			
		$scope.refresh = function(){
			$scope.fetchAll();
		}
		
		$scope.forceRun = function (scheduler, jobGroup, jobName){
			
			$http( {
				method:'POST',
				url: '~cool-force-quartz/'+ scheduler+'/'+jobGroup+'/'+jobName
			})
				.success(function(data, status, headers, config){
					$scope.refresh();
				})
				.error(function(data, status, h, c){
					// log
				});				
		}
		$scope.fetchAll();
		
	}]);