package com.fbmoll.badges.persistence.controller;

import com.fbmoll.badges.persistence.data.Catalog;
import com.fbmoll.badges.persistence.data.Data;
import com.fbmoll.badges.persistence.service.GameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final GameService gameService;

    public MyRestController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/getGames")
    public Catalog getGames(@RequestParam(value = "amount", defaultValue = "10")
                         Integer amount){
        try {
            Data data = Data.getInstance();
            data.persistence(amount,gameService.getDataGames(amount));
            return gameService.getDataGames(amount);
        } catch (Exception e){
            log.error("Service error",e);
            return null;
        }

    }

}
