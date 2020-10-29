package com.company;

public class Field {
    public char[][] _field;
    public final int size;
    public final int winStrike;

    public Field(int size, int winStrike) {
        this.size = size;
        this.winStrike = winStrike;
        _field = new char[size][size];
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                _field[j][i] = ' ';
            }
        }
    }

    //Метод, печатающий игровое поле
    public void display() {
        for (int i = 0; i < 2 * size - 1; i++)
            System.out.print("-");
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(Character.toString(_field[i][j]));
                if (j != size - 1) System.out.print("|");
            }
            System.out.println();
        }
        for (int i = 0; i < 2 * size - 1; i++)
            System.out.print("-");
        System.out.println();
    }

    //метод, изменяющий состояние поля
    public void makeMove(int x, int y, Player player) {
        if (isCellEmpty(x, y)) {
            _field[x][y] = player.playerChar;
        }
    }

    //метод, определяющий наличие победителя
    public Boolean isWin(char playerChar) {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (_field[x][y] == playerChar) {
                    //счётчики для одинаковых клеток подряд по направлениям
                    int countUR = 1;    //Up-Right
                    int countR = 1;     //Right
                    int countDR = 1;    //Down-Right
                    int countD = 1;     //Down

                    for (int i = 1; i < winStrike; i++) {
                        if (isCellInBounds(x + i, y + i) && _field[x + i][y + i] == playerChar) countUR++;
                        if (isCellInBounds(x + i, y) && _field[x + i][y] == playerChar) countR++;
                        if (isCellInBounds(x + i, y - i) && _field[x + i][y - i] == playerChar) countDR++;
                        if (isCellInBounds(x, y - i) && _field[x][y - i] == playerChar) countD++;
                    }
                    //если набралось нужное количество клеток для победы - выдаем результат
                    if (countUR >= winStrike ||
                            countR >= winStrike ||
                            countDR >= winStrike ||
                            countD >= winStrike) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //проверка принадлежности клетки полю
    public Boolean isCellInBounds(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    //проверка клетки на пустоту
    public Boolean isCellEmpty(int x, int y) {
        if (!isCellInBounds(x, y)) return false;
        return _field[x][y] == ' ';
    }

    //сравнение клетки и currentChar
    public Boolean isCellEquals(int x, int y, char currentChar) {
        if (!isCellInBounds(x, y)) return false;
        return _field[x][y] == currentChar;
    }
}