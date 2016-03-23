'use strict';

var sudokuSolverApp = angular.module('sudokuSolverApp', [ 'ngRoute', 'ngResource', 'ngCookies', 'publicControllers', 'sudokuServices' ]);

sudokuSolverApp.config([ '$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {
	$routeProvider.when('/solve', {
		templateUrl : 'resources/html/sudoku-solver.html',
		controller : 'SudokuSolverController',
	}).otherwise({
		redirectTo : '/solve'
	});

	$httpProvider.interceptors.push(function() {
		return {
			request : function(request) {
				if ((request.method === 'GET')) {
					var sep = request.url.indexOf('?') === -1 ? '?' : '&';
					request.url = request.url + sep + 'cacheBust=' + new Date().getTime();
				}
				return request;
			}
		};
	});
} ]);
