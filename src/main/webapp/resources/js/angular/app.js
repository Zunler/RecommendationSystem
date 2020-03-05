var app = angular.module('music', []);
//初始化播放列表
var myPlaylist = new jPlayerPlaylist({
    jPlayer: "#jplayer_N",
    cssSelectorAncestor: "#jp_container_N"
},
    {
    playlistOptions: {
        enableRemoveControls: true,
        autoPlay: true
    },
    swfPath: "js/jPlayer",
    supplied: "webmv, ogv, m4v, oga, mp3",
    smoothPlayBar: true,
    keyEnabled: true,
    audioFullScreen: false
});

app.controller('indexCtrl', function ($http, $scope) {

    $scope.userArtist = {};
    $scope.user = {};
    $scope.fetchMusicList = function () {
        $http.get('sys/user/musiclist').success(function (data) {
            $scope.musics = data;
        });
    };
    //加入播放列表，但不播放
    $scope.addToPlayList=function(userArtist){
        userArtist.musicId = userArtist.id;
        alert(userArtist.resourcePath)
        myPlaylist.add({
            title:userArtist.musicName,
            artist:userArtist.artistName,
            mp3: userArtist.resourcePath,
            poster: userArtist.imgPath
        })

    }

    $scope.playMusic = function (userArtist, userID) {
        userArtist.userID = userID;
        alert("play music:" + userArtist.id);
        userArtist.musicId = userArtist.id;
        //加入列表并播放当前歌曲
        $scope.addToPlayList(userArtist)
        myPlaylist.play(myPlaylist.playlist.length-1)
        $http.post('sys/user/playMusic/', userArtist).success(function (result) {
            if ("succ" == result) alert("succ!")
            else alert("fail!")
        });
    };
    $scope.fetchRecommendList = function () {
        //曲线救国
        var userIDget = document.getElementById("userID").innerHTML;
        $scope.user.id = userIDget;
        $http.post('sys/user/recommendlist', $scope.user.id).success(function (data) {
            $scope.recommendList = data;
        });
    }
    $scope.fetchMusicList();
    $scope.fetchRecommendList()

});
//从jplauer的demo.js拷贝过来的
$(document).ready(function(){

    $(document).on($.jPlayer.event.pause, myPlaylist.cssSelector.jPlayer,  function(){
        $('.musicbar').removeClass('animate');
        $('.jp-play-me').removeClass('active');
        $('.jp-play-me').parent('li').removeClass('active');
    });

    $(document).on($.jPlayer.event.play, myPlaylist.cssSelector.jPlayer,  function(){
        $('.musicbar').addClass('animate');
    });

    $(document).on('click', '.jp-play-me', function(e){
        e && e.preventDefault();
        var $this = $(e.target);
        if (!$this.is('a')) $this = $this.closest('a');

        $('.jp-play-me').not($this).removeClass('active');
        $('.jp-play-me').parent('li').not($this.parent('li')).removeClass('active');

        $this.toggleClass('active');
        $this.parent('li').toggleClass('active');
        if( !$this.hasClass('active') ){
            myPlaylist.pause();
        }else{
            var i = Math.floor(Math.random() * (1 + 7 - 1));
            myPlaylist.play(i);
        }

    });

});
