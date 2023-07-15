package ui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import model.Game;
import model.GameCharacter;
import model.Warrior;

import java.io.IOException;

public class TerminalGame {
    private Game game;
    private Screen screen;


    public void start() throws IOException, InterruptedException {
        Warrior player = new Warrior(false);

        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new Game((terminalSize.getColumns() - 1) / 2,
                terminalSize.getRows() - 2, player);

        beginTicks();
    }

    public void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded()) {
            tick();
            Thread.sleep(1000L / Game.TICKS_PER_SECOND);
        }
        System.exit(0);
    }

    public void tick() throws IOException {
        handleInput();

        game.tick();
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        render();
        screen.refresh();

        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
    }

    public void handleInput() throws IOException {
        KeyStroke stroke = screen.pollInput();

        if (stroke == null) {
            return;
        }

        if (stroke.getCharacter() != null) {
            return;
        }

        game.doAction(stroke.getKeyType());
    }

    private void render() {
        drawPlayer();
    }

    private void drawPlayer() {
        GameCharacter player = game.getPlayer();
        //'\u2588'
        drawPosition(player.getPosX(), player.getPosY(), TextColor.ANSI.GREEN, '\u263A');

//        for (Position pos : snake.getBody()) {
//            drawPosition(pos, TextColor.ANSI.GREEN, '\u2588', true);
//        }
    }

    private void drawPosition(int xpos, int ypos, TextColor color, char c) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(xpos * 2,ypos + 1, String.valueOf(c));
    }


}
