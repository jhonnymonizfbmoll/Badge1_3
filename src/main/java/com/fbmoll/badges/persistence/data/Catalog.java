package com.fbmoll.badges.persistence.data;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlRootElement(name = "catalog")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog extends MarshallingWrapper{

    @XmlElement(name = "name")
    private String name;

    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    private List<Game> games;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }


}
