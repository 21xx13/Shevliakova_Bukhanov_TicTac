package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Начало игры");

        int sizeField = 1;
        boolean flagDebug = true;
        //считывание размера поля
        while (flagDebug) {
            try {
                System.out.println("Размер поля : ");
                sizeField = Integer.parseInt(input.next());
                flagDebug = false;
            } catch (Exception e) {
                System.out.println("Недопустимое значение");
            }
        }
        //определение количества клеток для победы
        int winStrike = 3;
        if (sizeField <= 3) {
            System.out.println("Количество клеток для победы - " + sizeField);
            winStrike = sizeField;
        } else if (sizeField <= 5) {
            System.out.println("Количество клеток для победы - " + 3);
        } else {
            System.out.println("Количество клеток для победы - " + 5);
            winStrike = 5;
        }
        //инициализация поля и игроков
        Field field = new Field(sizeField, winStrike);
        System.out.println("Имя первого игрока : ");
        String player1Name = input.next();
        Player p1 = new Player(player1Name, 'X', false);
        System.out.println("Имя второго игрока : ");
        String player2Name = input.next();
        Player p2 = new Player(player2Name, 'O', true);
        Player playerNow = p1;

        int movesCount = sizeField * sizeField; //максимальное количество ходов за всю игру
        //цикл всех ходов игры
        while (true) {
            System.out.println("\nХодит: " + playerNow.toString());
            field.display();
            //получение хода от игрока
            int[] turn = playerNow.makeMove(field);
            //проверка хода
            if (field.isCellEmpty(turn[0], turn[1])) {
                field.makeMove(turn[0], turn[1], playerNow);
                if (field.isWin(playerNow.playerChar)) {
                    field.display();
                    System.out.print("Выиграл игрок: " + playerNow.toString());
                    break;
                }
                if (playerNow == p1) {
                    playerNow = p2;
                } else playerNow = p1;

                movesCount--;
            } else {
                System.out.println("Недопустимый ход");
                continue;
            }
            //Ничья
            if (movesCount <= 0) {
                System.out.println("Ничья");
                break;
            }
        }
    }
}
