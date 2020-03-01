package com.horizon.music.artist.service;

import com.horizon.music.artist.vo.Music;
import com.horizon.music.artist.vo.RattingData;
import com.horizon.music.artist.vo.UserArtist;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;

public interface IArtistService {

    List<Music> getAllMusicList();

    SqlRowSet getRattingData();
    void addUserArtist(UserArtist userArtist);




}
