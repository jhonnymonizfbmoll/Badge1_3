package com.fbmoll.badges.persistence.service;

import com.fbmoll.badges.persistence.data.Catalog;
import com.fbmoll.badges.persistence.data.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final  String URL = "https://www.instant-gaming.com/es/top50/";

    public Catalog getDataGames(int amount){
        try {
            Catalog catalog = new Catalog();
            Data data = Data.getInstance();
            data.gamesData(URL,amount);
            catalog.setGames(data.gamesData(URL,amount));
            catalog.setName("Top 10 Games");
            return catalog;
        } catch (Exception e) {
            log.error("Can't access data",e);
            return null;
        }

    }

}
