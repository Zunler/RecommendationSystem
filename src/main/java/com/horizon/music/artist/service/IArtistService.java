package com.horizon.music.artist.service;

import com.horizon.music.artist.vo.Music;
import com.horizon.music.artist.vo.UserArtist;

import java.util.List;
public interface IArtistService {

    List<Music> getAllMusicList();

    List<Music> getNewMusicList();

    List<Music> getTopMusicList();

    void addUserArtist(UserArtist userArtist);

    List<Music> getAllRecommedList(int user);
}

