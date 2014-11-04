package tanks.client.battlefield;

import tanks.client.battlefield.objects.*;

import java.awt.Graphics;

import java.util.Random;

public class BattleField {
    private static final int bfWidth = 576;
    private static final int bfHeight = 576;


    private String[][] strBattleField = {
            {"", "K", "K", "K", "", "", "", "", ""},
            {"", "K", "", "", "K", "", "", "", ""},
            {"", "", "", "", "", "", "K", "", ""},
            {"K", "", "", "", "K", "", "", "", ""},
            {"", "", "K", "", "", "", "", "K", ""},
            {"", "", "K", "", "K", "", "", "", ""},
            {"", "", "", "", "", "", "", "R", ""},
            {"K", "K", "", "", "", "", "", "", ""},
            {"", "", "", "", "E", "", "", "", ""}};

    private Dirt[][] battleField;

    public BattleField() {
        battleField = new Dirt[this.getDimensionY()][this.getDimensionX()];

        for (int v = 0; v < this.getDimensionY(); v++) {
            for (int h = 0; h < this.getDimensionX(); h++) {
                if (strBattleField[v][h].trim().isEmpty()) {
                    battleField[v][h] = new Dirt(v, h);
                } else if (strBattleField[v][h].equals("W")) {
                    battleField[v][h] = new Water(v, h);
                } else if (strBattleField[v][h].equals("K")) {
                    battleField[v][h] = new Brick(v, h, this);
                } else if (strBattleField[v][h].equals("E")) {
                    battleField[v][h] = new Eagle(v, h, this);
                    //this.eagleLocation = x + "_" + y;
                } else if (strBattleField[v][h].equals("R")) {
                    battleField[v][h] = new Rock(v, h);
                }
            }
        }
    }

    public void draw(Graphics g) {
        for (int v = 0; v < getDimensionY(); v++) {
            for (int h = 0; h < getDimensionX(); h++) {
                if (!(this.scanQuadrant(h, v) instanceof Water)) {
                    if (battleField[v][h] != null) {
                        battleField[v][h].draw(g);
                    } else {
                        System.err.println("Undefined quadrant in strBattleField[" + v + "_" + h);
                    }
                }
            }
        }
    }

    public void drawWater(Graphics g) {
        for (int v = 0; v < getDimensionY(); v++) {
            for (int h = 0; h < getDimensionX(); h++) {
                if (this.scanQuadrant(h, v) instanceof Water) {
                    battleField[v][h].draw(g);
                }
            }
        }
    }

    public String getAgressorLocation() {
        Random r = new Random();
        int i = r.nextInt(1);
        if (i == 0) {
            return "0_0";
        } else {
            return "576_576";
        }
    }

    public String getEagleLocation() {
        for (int i = 0; i < battleField.length; i++) {
            for (int k = 0; k < battleField[i].length; k++) {
                if (battleField[i][k] instanceof Eagle) {
                    return k + "_" + i;
                }
            }
        }
        return null;
    }


    public static int getBfWidth() {
        return bfWidth;
    }

    public static int getBfHeight() {
        return bfHeight;
    }

    public int getDimensionY() {
        return strBattleField.length;
    }

    public int getDimensionX() {
        return strBattleField[0].length;
    }

    public Dirt scanQuadrant(int x, int y) {
        if (y >= 0 && y < battleField.length) {
            if (x >= 0 && x < battleField[y].length) {
                return battleField[y][x];
            }
        }
        return null;
    }

    public void updateQuadrant(int h, int v) {
        battleField[v][h] = new Dirt(v, h);
    }

    public int getVerticalLength() {
        return battleField.length;
    }

    public int getHorizontalLength() {
        return battleField[0].length;
    }

}
