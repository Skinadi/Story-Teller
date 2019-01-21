package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuButton {

    private TextButton button;

    MenuButton(String title)
    {

        TextureRegion region = new TextureRegion(new Texture("Menu_Button.png"));
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = new TextureRegionDrawable(region);
        style.down = new TextureRegionDrawable(region);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(4);
        style.font = font;
        //button = new TextButton(title,style);
        button = new TextButton(title,new Skin(Gdx.files.internal("skin/glassy-ui.json")));
    }
    TextButton getButton()
    {
        return button;
    }


}
