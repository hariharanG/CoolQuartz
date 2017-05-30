'use strict';

angular.module('coolApp', [ 
	'ngRoute','coolApp.filter','coolApp.service','coolApp.directive','coolApp.controller','ngTable','ngResource'
]).config(['$routeProvider', function($routeProvider){
	$routeProvider.when('/cool-job-history', {templateUrl: 'app/views/job-history.html', controller: 'JobHistoryController'});
	$routeProvider.when('/cool-job-admin', {templateUrl: 'app/views/job-admin.html', controller: 'JobAdminController'});
	
	//Scheduler management aspect routing
	$routeProvider.when('/cool-mgmt-scheduler', {templateUrl: 'app/views/scheduler-admin.html', controller: 'SchedulerAdminController'});
	$routeProvider.when('/cool-mgmt-job', {templateUrl: 'app/views/scheduler-job-mgmt-info.html', controller: 'JobMgmtController'});
	$routeProvider.when('/cool-mgmt-info', {templateUrl: 'app/views/scheduler-info.html', controller: 'SchedulerInfoController'});
	
	
	//$routeProvider.when('/cool-job-admin', {templateUrl: 'app/views/job-admin.html'});
	$routeProvider.otherwise({redirectTo: '/cool-job-history'});
	
}]);