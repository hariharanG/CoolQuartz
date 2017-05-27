'use strict';

angular.module('coolApp.controller', [])
	.controller('JobHistoryController', ['$rootScope','$scope','$http','NgTableParams', '$resource', '$timeout','$filter', function($rootScope,$scope, $http, ngTableParams, $resource,$timeout,$filter){	
		
		
		$scope.fetchAll = function(){
			
			/*$http({method:'GET', url:'~cool-quartz/history'})
				.success(function(originalData, status, headers, config){
					$scope.result = originalData ;
					$scope.totalJobHistory = $scope.result.length;
					$scope.histories = $scope.result.content;
					//$("#example2").DataTable();
					
				})
				.error(function(data, status, headers, config){
					//log('Ajax Error ');
			});	*/		
			
		    var Api = $resource("~cool-quartz/history");

		    $scope.tableParams = new ngTableParams({
		        page: 1,            // show first page
		        count: 10,          // count per page
		        sorting: {
		            name: 'asc'     // initial sorting
		        }
		    }, {
		        total: 0,           // length of data
		        getData: function ($defer, params) {
		            // ajax request to api
		            Api.get(params.url(), function (data) {
		                $timeout(function() {
		                    // update table params
		                    params.total(data.total);
		                    // For dashboard
		                    // console.log(data)
		                    $rootScope.totalJobHistories = data.total;
		                    console.log('rootScope '+$rootScope.totalJobHistories )
		                    // set new data
		                    var orderedData = params.sorting() ?
		                                      $filter('orderBy')(data.content, params.orderBy()) :
		                                      data.content;
		                    $defer.resolve(orderedData.slice((params.page() - 1) * 
		                        params.count(), params.page() * 
		                        params.count()));
		                    /*console.log(data.result);*/
		                }, 10);
		            });
		        }
		    });
		}
			
		$scope.refresh = function(){
			$scope.fetchAll();
			$scope.tableParams.reload();
		}
		
		$scope.fetchAll();		
		
		
				
	}])
	.controller('JobAdminController', ['$scope', '$http','NgTableParams', '$resource', '$timeout','$filter', function($scope, $http, ngTableParams, $resource, $timeout, $filter){
		
		$scope.fetchAll = function(){
			$http({method:'GET', url:'~cool-quartz/triggers'})
				.success(function(data , status, headers, config){
					$scope.triggers = data;
				})
				.error(function(data, status, headers , config){
					//log('error');
				});
			/*
			var Api = $resource("~cool-quartz/triggers");

		    $scope.tableParams = new ngTableParams({
		        page: 1,            // show first page
		        count: 10,          // count per page
		        sorting: {
		            name: 'asc'     // initial sorting
		        }
		    }, {
		        total: 0,           // length of data
		        getData: function ($defer, params) {
		            // ajax request to api
		            Api.get(params.url(), function (data) {
		                $timeout(function() {
		                    // update table params
		                    params.total(data.total);
		                    // set new data
		                    var orderedData = params.sorting() ?
		                                      $filter('orderBy')(data.content, params.orderBy()) :
		                                      data.content;
		                    $defer.resolve(orderedData.slice((params.page() - 1) * 
		                        params.count(), params.page() * 
		                        params.count()));
		                    console.log(data.result);
		                }, 10);
		            });
		        }
		    });
			*/
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
			$scope.fetchAll();
		}
		$scope.fetchAll();
		
	}]);