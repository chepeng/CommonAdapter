package com.frank.sample.bean;

import java.util.List;

public class GameTypeBean {

    private String name;
    private List<GameBean> gameBeanList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GameBean> getGameBeanList() {
        return gameBeanList;
    }

    public void setGameBeanList(List<GameBean> gameBeanList) {
        this.gameBeanList = gameBeanList;
    }
}
