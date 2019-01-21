package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MusicButton extends MenuButton
{
    static Music music;
    String filename;
    String name;
    MusicButton(String title,String filename,String name) {
        super(title);
        this.filename=filename;
        this.name=name;
    }

    @Override
    TextButton getButton() {
        return super.getButton();
    }
    void loadMusic()
    {
        music= Gdx.audio.newMusic(Gdx.files.local(filename));
    }

    public static Music getMusic() {
        return music;
    }

    public static void setMusic(Music music) {
        MusicButton.music = music;
    }

}
