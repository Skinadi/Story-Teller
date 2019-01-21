package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;


public class LoadingScreen implements Screen
{
    final Main game;
    boolean done;
    TextField textField;
    boolean updateable;
    ProgressBar bar;
    public LoadingScreen(final Main game)
    {

        updateable = true;
        done = false;
        this.game=game;
        textField = new TextField("Loading",game.skin);
        //textField.toFront();
        textField.setSize(200,100);
        textField.setScale(5);
        textField.setAlignment(Align.center);
        textField.setPosition((Gdx.graphics.getWidth()-textField.getWidth())/2,(Gdx.graphics.getHeight()-textField.getHeight())/2);
        //makeJSON();
        bar = new ProgressBar(0,2,1,false,game.skin);
        if(!updateable)
            done = true;
        else
            updateJSON();


    }
    void updateJSON()
    {
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("https://www.dropbox.com/s/zeo1llvsfavf8qe/Settings.json?dl=1");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse (Net.HttpResponse httpResponse) {
                FileHandle tmpFile = Gdx.files.local("Settings.json");
                tmpFile.write(httpResponse.getResultAsStream(), false);
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run () {
                                System.out.println("JSON Downloaded");
                    }
                });
            }

            @Override
            public void failed (Throwable t) {
                Gdx.app.error("Test", "something went wrong", t);
            }

            @Override
            public void cancelled () {
                Gdx.app.log("Test", "cancelled");
            }
        });
        updateStories();
    }
    void updateStories()
    {
        Json json = new Json();
        FileHandle file = Gdx.files.internal("Settings.json");
        final Album album = json.fromJson(Album.class,file.readString());
        bar.setRange(0,album.getStories().size());
        bar.setSize(500,50);
        bar.setPosition((Gdx.graphics.getWidth()-bar.getWidth())/2,(Gdx.graphics.getHeight()-bar.getHeight())/2-100);
        bar.setValue(1);
        for(final Stories story : album.getStories())
        {
            if(!Gdx.files.local(story.filename).exists())
            {
                Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
                request.setUrl(story.URL);
                Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                    @Override
                    public void handleHttpResponse (Net.HttpResponse httpResponse) {
                        FileHandle tmpFile = Gdx.files.local(story.filename);
                        System.out.println("Downloading: " + story.name);
                        tmpFile.write(httpResponse.getResultAsStream(), false);
                    Gdx.app.postRunnable(new Runnable() {
                    @Override
                        public void run ()
                        {
                            done=true;
                            for(Stories story1 : album.getStories())
                            {
                                if(!Gdx.files.local(story1.filename).exists())
                                {
                                    done=false;
                                    break;
                                }
                            }
                            System.out.println(bar.getValue());
                            if(bar.getValue()+1<=bar.getMaxValue())
                            bar.setValue(bar.getValue()+1);
                        }
                    });

                    }
                    @Override
                    public void failed (Throwable t) {
                        Gdx.app.error("Test", "something went wrong", t);
                    }

                    @Override
                    public void cancelled () {
                        Gdx.app.log("Test", "cancelled");
                    }
                });
            }
            else
                bar.setValue(bar.getValue()+1);
        }
        done=true;
        for(Stories story1 : album.getStories())
        {
            if(!Gdx.files.local(story1.filename).exists())
            {
                done=false;
                break;
            }
        }
        //String test = new String();
        //test = "trolo \n lolo";
        //System.out.println(test);
        //done = false;
    }
    void makeJSON()
    {
        Album album = new Album();
        Stories story = new Stories();
        story.name = "Skarbonka";
        story.URL = "https://www.dropbox.com/s/qb5380zn6ryanpv/Skarbonka.mp3?dl=1";
        story.filename = "Skarbonka.mp3";
        story.length = 413f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Ksiezniczka na \n ziarnku grochu";
        story.URL = "https://www.dropbox.com/s/hy4hm25ex84dkvl/ksiezniczka_na_ziarnku_grochu.mp3?dl=1";
        story.filename = "ksiezniczka_na_ziarnku_grochu.mp3";
        story.length= 168f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Motylek";
        story.URL = "https://www.dropbox.com/s/riwsuq1mfcgmo3a/motylek.mp3?dl=1";
        story.filename = "motylek.mp3";
        story.length = 370f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Kogucik podworzowy i \nkogucik na dachu";
        story.URL = "https://www.dropbox.com/s/akxnaf6t5sa3k5h/kogucik_podworzowy_i_kogucik_na_dachu.mp3?dl=1";
        story.filename = "kogucik_podworzowy_i_kogucik_na_dachu.mp3";
        story.length = 380f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Pioro i Kalamarz";
        story.URL = "https://www.dropbox.com/s/vb7pr5qqsmwryxx/pioro_i_kalamarz.mp3?dl=1";
        story.filename = "pioro_i_kalamarz.mp3";
        story.length = 369f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Skoczki";
        story.URL = "https://www.dropbox.com/s/093s9f49e9kdje6/skoczki.mp3?dl=1";
        story.filename = "skoczki.mp3";
        story.length = 260f;
        album.getStories().add(story);
        story = new Stories();
        story.name = "Slimak i Krzak Rozy";
        story.URL = "https://www.dropbox.com/s/2jy1uyr70nyjeh5/slimak_i_krzak_rozy.mp3?dl=1";
        story.filename = "slimak_i_krzak_rozy.mp3";
        story.length = 360f;
        album.getStories().add(story);
        Json json = new Json();
        FileHandle tmpFile = Gdx.files.local("Settings.json");
        tmpFile.writeString(json.toJson(album), false);
        FileHandle file  = Gdx.files.local("Settings.json");
        Album album1 = json.fromJson(Album.class,file.readString());
        System.out.println(album1.getStories().size());
        for(int i = 0; i<album1.getStories().size();i++)
        {
            System.out.println(album1.getStories().get(i).name);
        }
        //System.out.println(json.toJson(album));
        //System.out.println("try");


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if(done)
            game.setScreen(new MenuScreen(game));
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        textField.draw(game.batch,1);
        bar.draw(game.batch,1);
        bar.act(Gdx.graphics.getDeltaTime());
        game.batch.end();


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
