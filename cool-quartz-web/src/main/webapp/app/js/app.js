'use strict';

angular.module('coolApp', [ 
	'ngRoute','coolApp.filter','coolApp.service','coolApp.directive','coolApp.controller'
]).config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/cool-job-history', {templateUrl: 'app/views/job-history.html', controller: 'JobHistoryController'});
	$routeProvider.when('/cool-job-admin', {templateUrl: 'app/views/job-admin.html', controller: 'JobAdminController'});
	//$routeProvider.when('/cool-job-admin', {templateUrl: 'app/views/job-admin.html'});
	$routeProvider.otherwise({redirectTo: '/cool-job-history'});
	
}]);