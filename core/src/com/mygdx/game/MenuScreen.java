package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class MenuScreen implements Screen {

    Main game;
    Table table;
    Stage stage;
    Album album;
    Json json;

    public MenuScreen(final Main game) {
        this.game = game;

        json = new Json();
        album = json.fromJson(Album.class,Gdx.files.local("Settings.json").readString());

        stage = new MyStage();
        stage.setViewport(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),stage.getCamera()));
        stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);

        buildMenu(table);
        stage.addActor(table);
        //Gdx.input.setInputProcessor(new InputMultiplexer(stage,new MenuInputProcessor(stage)));
        Gdx.input.setInputProcessor(stage);
    }

    void buildMenu(Table table)
    {
        Music music;
        ArrayList <MusicButton> buttons = new ArrayList<MusicButton>();
        for(final Stories story : album.getStories())
        {
           buttons.add(new MusicButton(story.name,story.filename,story.name));
        }
        for(final MusicButton button : buttons)
        {
            button.getButton().addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    /*if(button.getMusic() != null && button.getMusic().isPlaying())
                    {
                        button.getMusic().stop();
                        button.getMusic().dispose();
                    }
                    button.loadMusic();
                    button.getMusic().play();*/
                    Stories temp = album.getStories().get(0);
                    for (int i = 0; i<album.getStories().size();i++)
                    {
                        if(album.getStories().get(i).name==button.name && album.getStories().get(i).filename==button.filename)
                        {
                            temp = album.getStories().get(i);
                            break;
                        }
                    }
                    game.setScreen(new PlayerScreen(game,album,temp,button));
                }
            });
            table.add(button.getButton()).height(200).spaceTop(30);
            table.row();
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta)
    {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        stage.getCamera().update();

        /*if(Gdx.input.isTouched())
        {
            stage.getCamera().position.set(new Vector3(stage.getCamera().position.x,stage.getCamera().position.y,
                    stage.getCamera().position.z));
        }*/
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
