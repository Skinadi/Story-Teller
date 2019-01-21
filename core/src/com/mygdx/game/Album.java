package com.mygdx.game;

import java.util.ArrayList;

public class Album
{
    Album()
    {
        stories = new ArrayList<Stories>();
    }
    public ArrayList<Stories> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Stories> stories) {
        this.stories = stories;
    }

    ArrayList <Stories> stories;
}
