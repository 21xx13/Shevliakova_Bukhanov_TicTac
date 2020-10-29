package com.company;

import java.util.Scanner;

public class Player {
    public final String playerName;
    public final char playerChar;
    public final Boolean isBot;

    private final double[] attackWeights = new double[]{0.1, 2, 4, 6, 200}; //веса для определения хода бота
    private final double[] defenseWeights = new double[]{0.25, 5, 7, 100, 200};

    public Player(String name, char playerChar, Boolean isBot) {
        playerName = name;
        this.playerChar = playerChar;
        this.isBot = isBot;
    }

    public String toString() {
        return playerName;
    }

    //метод, описывающий ход...
    public int[] makeMove(Field field) {
        //...игрока
        if (!isBot) {
            Scanner input = new Scanner(System.in); //ввод координат хода игрока через пробел
            String[] move; //сначала вводится номер строки, потом номер столбца
            int[] result = new int[2];
            boolean flagDebug = true;
            while (flagDebug) {
                try {
                    move = input.nextLine().split(" ");
                    result = new int[]{Integer.parseInt(move[0]), Integer.parseInt(move[1])};
                    flagDebug = false;
                } catch (Exception e) {
                    System.out.println("Недопустимое значение");
                }
            }
            return result;
        }

        //...бота
        double[][] weights = new double[field.size][field.size];
        for (int x = 0; x < field.size; x++)
            for (int y = 0; y < field.size; y++)
                if (field.isCellEmpty(x, y))
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            weights[x][y] += getAttackWeights(x, y, dx, dy, field);
                            weights[x][y] += getDefenseWeights(x, y, dx, dy, field);
                        }
                    }

        // ----Вывод весов поля---- //

        // for(int i = 0; i<field.size; i++){
        //     for(int j = 0; j<field.size; j++){
        //         System.out.print(Double.toString(weights[i][j]));
        //         if(j != field.size-1) System.out.print("|");
        //     }
        //     System.out.println();
        // }

        //определение клетки с максимальным весом
        double maxWeight = 0.0;
        int resX = -1;
        int resY = -1;
        for (int x = 0; x < field.size; x++)
            for (int y = 0; y < field.size; y++) {
                if (weights[x][y] > maxWeight) {
                    maxWeight = weights[x][y];
                    resX = x;
                    resY = y;
                }
            }
        //в случае отсутствия подходящей клетки - выбираем случайную 
        if (resX == -1 || resY == -1) {
            while (!field.isCellEmpty(resX, resY)) {
                resX = (int) (Math.random() * field.size);
                resY = (int) (Math.random() * field.size);
            }
        }
        return new int[]{resX, resY};
    }

    //получение весов атаки по направлению (directionX, directionY)
    public double getAttackWeights(int x, int y, int directionX, int directionY, Field field) {
        double result = 0;
        for (int d = 1; d < field.winStrike; d++) {
            if (field.isCellEquals(x + d * directionX, y + d * directionY, playerChar)) {
                result += attackWeights[d];
            } else {
                if (field.isCellEmpty(x + d * directionX, y + d * directionY)) {
                    break;
                }
            }
        }
        return result;
    }

    //получение весов защиты по направлению (directionX, directionY)
    public double getDefenseWeights(int x, int y, int directionX, int directionY, Field field) {
        double result = 0;
        for (int d = 1; d < field.winStrike; d++) {
            if (!field.isCellInBounds(x + d * directionX, y + d * directionY)) return result;
            if (field._field[x + d * directionX][y + d * directionY] != playerChar && field._field[x + d * directionX][y + d * directionY] != ' ') {
                result += defenseWeights[d];
            } else {
                return result;
            }
        }
        return result;
    }
}