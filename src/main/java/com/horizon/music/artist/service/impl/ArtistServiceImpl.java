package com.horizon.music.artist.service.impl;

import com.horizon.music.artist.dao.ArtistDAO;
import com.horizon.music.artist.service.IArtistService;
import com.horizon.music.artist.sql.IMusicSqlTemplate;
import com.horizon.music.artist.vo.Last;
import com.horizon.music.artist.vo.Music;
import com.horizon.music.artist.vo.RattingData;
import com.horizon.music.artist.vo.UserArtist;
import com.horizon.sm.user.vo.User;
import groovy.sql.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("artistService")
public class ArtistServiceImpl implements IArtistService {
    @Autowired
    private ArtistDAO artistDAO;

    public void setArtistDAO(ArtistDAO artistDAO) {
        this.artistDAO = artistDAO;
    }

    @Override
    public List<Music> getAllMusicList() {
        return (List<Music>) artistDAO.findByVO(new Music(), IMusicSqlTemplate.GET_ALL_MUSICLIST, Music.class);
    }


    @Override
    public List<Music> getAllRecommedList(int id) {
        return (List<Music>)artistDAO.findByVO(new Music(), IMusicSqlTemplate.GET_ALL_RECOMMENDLIST
                +" WHERE A.USER_ID ="+id, Music.class);
    }

    @Override
    public void addUserArtist(UserArtist userArtist) {
        artistDAO.save(IMusicSqlTemplate.SAVE_USER_ARTIST, userArtist);
    }

}
