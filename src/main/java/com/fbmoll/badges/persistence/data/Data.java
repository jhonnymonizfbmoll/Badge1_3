package com.fbmoll.badges.persistence.data;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Data {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static Data instance = null;
    private static final String SELECT = "div.discount, div.price, div.name";
    private static final String FILE_NAME = String.format("%s\\topGames.xml", System.getProperty("user.home"));

    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private Data() {
    }


    public List<Game> gamesData(String url,int quantity) {
        int pos = 0;
        String name;
        String discount;
        Double price;
        ArrayList<Game> gamesList = new ArrayList<Game>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements games = doc.select(SELECT);
            for (int i = 0; i < (quantity*3); i+=3) {
                ++pos;
                discount = games.get(i).text();
                price = Double.valueOf(games.get(i+1).text().replace("€", StringUtils.EMPTY));
                name = games.get(i+2).text();
                Game game = new Game(pos,name,discount,price);
                gamesList.add(game);

            }
            return gamesList;
        } catch (Exception e) {
            log.error("Fail loading data", e);
            return null;
        }

    }

    public void persistence(int quantity, Catalog catalog){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
        String strDate = dateFormat.format(date);
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();
        Data data = Data.getInstance();
        try {
            File XMl = new File(FILE_NAME);
            if(XMl.exists()) {
                Catalog loadedCatalog = fileDataSingleton.unmarshallContent(FILE_NAME, Catalog.class);
                ArrayList<Game> newGames = new ArrayList<Game>(loadedCatalog.getGames());
                boolean same = data.newData(catalog.getGames(), newGames, quantity);
                if (!same) {
                    String newFileName = String.format("%s\\topGames(%s).xml", System.getProperty("user.home"), strDate);
                    File newFile = fileDataSingleton.marshallContent(newFileName, catalog);
                    File file = fileDataSingleton.marshallContent(FILE_NAME, catalog);
                } else {
                    log.info("No changes");
                }
            }else {
                File file = fileDataSingleton.marshallContent(FILE_NAME, catalog);
            }
        }catch (Exception e){
            log.error("Error",e);
        }



    }

    public Boolean newData(List<Game> newGames, List<Game> oldGames, int quantity){
        if (newGames.size() != oldGames.size()){
            return false;
        }
        for (int i = 0; i < quantity; i++) {
            if (!newGames.get(i).getName().equals(oldGames.get(i).getName())){
                return false;
            }else if (!newGames.get(i).getPrice().equals(oldGames.get(i).getPrice())){
                return false;
            }
        }
        return true;
    }
}
