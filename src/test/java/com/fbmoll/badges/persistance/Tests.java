package com.fbmoll.badges.persistance;


import com.fbmoll.badges.persistence.Main;
import com.fbmoll.badges.persistence.data.Catalog;
import com.fbmoll.badges.persistence.data.Data;
import com.fbmoll.badges.persistence.data.FileDataSingleton;
import com.fbmoll.badges.persistence.data.Game;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest(classes ={Main.class})
public class Tests {

    @Test
    void tryMarshalling(){
        Game game = new Game(1,"Doom","-12",21.3);
        ArrayList<Game> games = new ArrayList<Game>();
        games.add(game);
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();
        Catalog catalog = new Catalog();
        catalog.setGames(games);
        catalog.setName("catalog serialization test");
        String jsonContent = fileDataSingleton.marshall2JSON(catalog);
        System.out.println(jsonContent);
    }

    @Test
    void tryDataGames(){
        String url   = "https://www.instant-gaming.com/es/top50/";
        int quantity = 10;
        Data data = Data.getInstance();
        data.gamesData(url,quantity);
    }

    @Test
    void tryMarshallingGames(){
        String url   = "https://www.instant-gaming.com/es/top50/";
        int quantity = 10;
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();
        Catalog catalog = new Catalog();
        Data data = Data.getInstance();
        data.gamesData(url,quantity);
        catalog.setGames(data.gamesData(url,quantity));
        catalog.setName("catalog serialization test");
        String jsonContent = fileDataSingleton.marshall2JSON(catalog);
        System.out.println(jsonContent);
    }

    @Test
    void tryMarshallingXmlAndUnmarshalling(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
        String strDate = dateFormat.format(date);
        String url   = "https://www.instant-gaming.com/es/top50/";
        int quantity = 10;
        Data data = Data.getInstance();
        String fileName = String.format("%s\\topGames.xml", System.getProperty("user.home"));
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();
        Catalog catalog = new Catalog();
        catalog.setGames(data.gamesData(url,quantity));
        catalog.setName("catalog serialization test");
        File file = fileDataSingleton.marshallContent(fileName, catalog);
        Assert.notNull(file, "Example rules");
        Catalog loadedCatalog = fileDataSingleton.unmarshallContent(fileName, Catalog.class);
        Assert.notNull(loadedCatalog, "Example rules");
    }

    @Test
    void testDate(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
        String strDate = dateFormat.format(date);
        System.out.println("Converted String: " + strDate);
    }

    @Test
    void trySameData(){
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
        String strDate = dateFormat.format(date);
        String fileName = String.format("%s\\topGames.xml", System.getProperty("user.home"));
        String url   = "https://www.instant-gaming.com/es/top50/";
        int quantity = 10;
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();
        Catalog catalog = new Catalog();
        Data data = Data.getInstance();
        data.gamesData(url,quantity);
        catalog.setGames(data.gamesData(url,quantity));
        catalog.setName("catalog serialization test");
        Catalog loadedCatalog = fileDataSingleton.unmarshallContent(fileName, Catalog.class);
        ArrayList<Game> newGames = new ArrayList<Game>(loadedCatalog.getGames());
        boolean same = data.newData(data.gamesData(url,quantity),newGames,quantity);
        if(same){
            System.out.println("son iguales");
        } else {
            System.out.println("no son iguales");
            String newFileName = String.format("%s\\topGames(%s).xml", System.getProperty("user.home"),strDate);
            File newFile = fileDataSingleton.marshallContent(newFileName, catalog);
            File file = fileDataSingleton.marshallContent(fileName, catalog);
            Assert.notNull(newFile, "Example rules");
        }
    }


}
