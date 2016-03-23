'use strict';

var publicControllers = angular.module('publicControllers', []);

/* AuthenticationController */
publicControllers.controller('SudokuSolverController', [ '$scope', 'Board', function($scope, Board) {

	$scope.solve = function() {
		$scope.message = "";
		Board().solve($scope.board, function(data) {
			$scope.board = transformResult(data);
			$scope.message = "Solved!";
		}, function(error) {
			$scope.message = "Cannot Solve.  Either too difficult or in an invalid state."
		});
	}
	
	$scope.clearBoard = function() {
		$scope.board = generateBoard();
	}

	var transformResult = function(input) {
		var new_board = [];
		for (var i = 0; i < input.length; i++) {
			var row = input[i];
			for (var j = 0; j < 9; j++) {
				var value = row[j];
				if (j == 0) {
					new_board[i] = [];
				}
				new_board[i][j] = value;
			}
		}
		return new_board;
	}

	var generateBoard = function() {
		var new_board = [];
		var counter = 1;
		for (var i = 0; i < 9; i++) {
			for (var j = 0; j < 9; j++) {
				if (j == 0) {
					new_board[i] = [];
				}
				new_board[i][j] = 0;
			}
		}

		return new_board;
	}

	var getBoard = function() {
		Board().get(function(data) {
			$scope.board = transformResult(data);
		}, function(error) {
			alert('failed');
		});
	}

	$scope.board = getBoard();

} ]);
