package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PlayerScreen implements Screen
{

    Main game;
    Album album;
    Stories story;
    MusicButton button;
    MenuButton nextbutton;
    MenuButton prevbutton;
    MenuButton playbutton;
    boolean isPlaying;
    Stage stage;
    Table table;
    Slider bar;
    boolean humandoing;
    PlayerScreen(Main game,Album album, Stories story,MusicButton button )
    {
        this.game=game;
        this.album=album;
        this.story=story;
        this.button=button;
        humandoing = false;
        isPlaying = true;

        bar = new Slider(0,story.length,1,false,game.skin);
        bar.setSize(500,50);
        bar.setValue(0);
        bar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(humandoing)
                {
                    MusicButton.music.pause();
                    MusicButton.music.setPosition(bar.getValue());
                    if(isPlaying)
                        MusicButton.music.play();
                }
                humandoing = true;
            }
        });
        stage = new Stage();
        stage.setViewport(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),stage.getCamera()));
        stage.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        table = new Table();
        table.setFillParent(true);
        //table.setDebug(true);

        buildPlayer(table);
        //stage.addActor(table);
        //Gdx.input.setInputProcessor(new InputMultiplexer(stage,new MenuInputProcessor(stage)));
        Gdx.input.setInputProcessor(stage);

        button.loadMusic();
        MusicButton.music.play();
        //Main.manager.Cantfade();
        this.button.getButton().setDisabled(true);
        this.button.getButton().setClip(false);
        this.button.getButton().setChecked(true);

    }
    void buildPlayer(Table table)
    {
        nextbutton = new MenuButton("Next");
        prevbutton = new MenuButton("Prev");
        playbutton = new MenuButton("Stop");
        nextbutton.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                nextSong();
            }
        });
        prevbutton.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                prevSong();
            }
        });
        playbutton.getButton().addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                startStopPlaying();
            }
        });
        Table table1 = new Table();
        table1.setFillParent(true);
        table1.add(button.getButton()).width(Gdx.graphics.getWidth()).height(200);
        table1.row();
        //table1.add(bar).spaceTop(100).width(600).height(200);
        //table1.row();
        table1.align(Align.top);
        stage.addActor(table1);
        table.add(bar).spaceTop(100).width(600).height(200).colspan(3);
        table.row();
        table.add(prevbutton.getButton()).width(200).height(100);
        table.add(playbutton.getButton()).width(200).height(100);
        table.add(nextbutton.getButton()).width(200).height(100);
        table.align(Align.bottom);
        stage.addActor(table);
    }
    void startStopPlaying()
    {
        if(isPlaying)
        {
            playbutton.getButton().setText("Start");
            MusicButton.music.pause();
            isPlaying = !isPlaying;
            //Main.manager.Canfade();
        }
        else
        {
            playbutton.getButton().setText("Stop");
            MusicButton.music.play();
            //Main.manager.Cantfade();
            isPlaying =!isPlaying;
        }
    }
    void nextSong()
    {
        MusicButton.music.dispose();
        for(int i = 0; i<album.getStories().size();i++)
        {
            if(album.getStories().get(i)==story)
            {
                story = album.getStories().get((i+1)%album.getStories().size());
                break;
            }
        }
        button.name = story.name;
        button.filename = story.filename;
        bar.setRange(0,story.length);
        button.getButton().setText(button.name);
        button.loadMusic();
        if(isPlaying)
            MusicButton.music.play();
    }
    void prevSong()
    {
        MusicButton.music.dispose();
        for(int i = 0; i<album.getStories().size();i++)
        {
            if(album.getStories().get(i)==story)
            {
                if(i==0)
                {
                    story = album.getStories().get(album.stories.size()-1);
                    break;
                }
                else
                {
                    story = album.getStories().get((i-1)%album.getStories().size());
                    break;
                }

            }
        }
        button.name = story.name;
        button.filename = story.filename;
        bar.setRange(0,story.length);
        button.getButton().setText(button.name);
        button.loadMusic();
        if(isPlaying)
            MusicButton.music.play();
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
        bar.setValue(MusicButton.music.getPosition());
        humandoing = false;
        if(!MusicButton.music.isPlaying())
        {
            //Main.manager.Canfade();
            playbutton.getButton().setText("Start");
            isPlaying = false;
        }

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //System.out.println(MusicButton.music.getPosition());
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

    }
}
