package com.mygdx.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class MyStage extends Stage {
    public MyStage() {
        super();
    }
    int lastx,lasty;
    boolean dragging;
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX,screenY,pointer,button);
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        System.out.println("TouchDown: " + screenX + " " + screenY + " " + pointer + " " + button);
        lastx = screenX;
        lasty = screenY;
        dragging = true;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX,screenY,pointer,button);
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        System.out.println("TouchUp: " + screenX + " " + screenY + " " + pointer + " " + button);
        dragging = false;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX,screenY,pointer);
        if (!dragging) return false;
        System.out.println("Dragged: " + screenX + " " + screenY + " " + pointer);
        this.getCamera().translate(0,screenY-lasty,0);
        lastx=screenX;
        lasty=screenY;
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return super.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {
        return super.scrolled(amount);
    }

    @Override
    public boolean keyDown(int keyCode) {
        return super.keyDown(keyCode);
    }

    @Override
    public boolean keyUp(int keyCode) {
        return super.keyUp(keyCode);
    }

    @Override
    public boolean keyTyped(char character) {
        return super.keyTyped(character);
    }
}
