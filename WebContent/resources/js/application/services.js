var sudokuServices = angular.module('sudokuServices', [ 'ngResource' ]);
sudokuServices.factory('Board', [ '$resource', function($resource) {
	return function() {
		return $resource('../sudoku-solver//board', null, {
			solve : {
				method : 'POST',
				isArray : true,
				transformResponse : function(data, global) {
					return angular.fromJson(data);
				}
			},
			get : {
				method : 'GET',
				isArray : true,
				transformResponse : function(data, global) {
					return angular.fromJson(data);
				}
			}
		});
	};
} ]);

