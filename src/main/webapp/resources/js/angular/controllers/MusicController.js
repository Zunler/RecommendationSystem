'use strict';

/**
 * CarController
 * @constructor
 */
var MusicController = function ($scope, $http) {
    $scope.playMusic = function (userID, ArtistID) {
        log(userID, ArtistID);
        $http({
            method:'POST',
            url:'sys/user/playMusic/',
            data:{userID: userID, ArtistID:ArtistID}
        }).success(function () {
            $scope.fetchCarsList();
        });
        $scope.carName = '';
    };
};
