package com.project.java2048;
import java.util.ArrayList;
import java.util.Random;
/**
 * Klasa Game zawiera dane gry
 * W tej klasie tworzona jest tablica 4x4 oraz rozpisany jest
 * algorytm działania w przypadku przesuwania liczb w górę, dół, lewo lub prawo.
 * Dodatkowo znajdują się tutaj funkcje sprawdzające status gry.
 */
public class Game {

    private int[][] plansza;
    private Random r = new Random();
    private GameState status;
    private int score;
    public Game() {
        plansza = new int[4][4];
        dodajNumer();
        dodajNumer();
        status = GameState.KONTYNUACJA;
    }
    public int getScore(){
        return score;
    }
    public int[][] getGameBoard(){
        return plansza;
    }
    public GameState getState(){
        return  status;
    }

    /**Funkcja rysująca tablicę 4x4
     */
    public void printArray() {
        for (int[] x: plansza) {
            System.out.format("%6d%6d%6d%6d%n", x[0], x[1], x[2], x[3]);
        }
        System.out.format("%n");
    }
    /**Funkcja wypełniająca tablicę liczbami(dodaje liczbę 2 lub 4 po każdym wywołaniu
     */
    public void dodajNumer() {
        if (czyPlanszaPelna()) {
            return;
        }
        ArrayList<Integer> emptySpacesX = new ArrayList<Integer>();
        ArrayList<Integer> emptySpacesY = new ArrayList<Integer>();
        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++ ){
                if (plansza[x][y] == 0){
                    emptySpacesX.add(x);
                    emptySpacesY.add(y);
                }
            }
        }
        int choice = r.nextInt(emptySpacesX.size());
        int numberChooser = r.nextInt(10);
        int newNumber = 2;
        if (numberChooser == 0) {
            newNumber = 4;
        }
        int X = emptySpacesX.get(choice);
        int Y = emptySpacesY.get(choice);
        plansza[X][Y] = newNumber;
    }
    /**Funkcja odpowiedzialna za przesuwanie liczb w górę
     */
    public void wgore(){
        for (int y = 0; y < 4; y++){
            boolean[] alreadyCombined = {false, false, false, false};//do sprawdzania czy podczas ruchu nie zsumowały się już jakieś liczby
            for (int x = 1; x < 4; x++){
                if (plansza[x][y] != 0){
                    int value = plansza[x][y];
                    int X = x - 1;
                    while((X >= 0) && (plansza[X][y] == 0)){
                        X--;
                    }
                    //Liczba dojeżdża do góry planszy
                    if (X == -1) {
                        plansza[0][y] = value;
                        plansza[x][y] = 0;
                        //liczba napotyka po drodze zajete pole o innej wartosci ostaje umieszczona pole ponizej napotkanego zajetego pola
                    }
                    else if (plansza[X][y] != value){
                        plansza[x][y] = 0;
                        plansza[X+1][y] = value;
                    }
                    //liczba napotyka taka sama liczbe ale napotkana liczba została już podwojona w tym ruchu
                    else {
                        if (alreadyCombined[X]){
                            plansza[X+1][y] = value;
                            plansza[x][y] = 0;
                        }
                        //2 takie same liczby obok siebie, podwojenie liczby, która jest powyżej
                        else {
                            plansza[x][y] = 0;
                            plansza[X][y] *= 2;
                            score += plansza[X][y];
                            alreadyCombined[X] = true;

                        }
                    }
                }
            }
        }
    }
    /**Funkcja odpowiedzialna za przesuwanie liczb w dół
     */
    public void wdol(){
        for (int y = 0; y < 4; y++){
            boolean[] alreadyCombined = {false, false, false, false};
            for (int x = 2; x > -1; x--){
                if (plansza[x][y] != 0){
                    int value = plansza[x][y];
                    int X = x + 1;
                    while((X <= 3) && (plansza[X][y] == 0)){
                        X++;
                    }
                    //liczba dojezdza do dolu planszy:
                    if (X == 4) {
                        plansza[3][y] = value;
                        plansza[x][y] = 0;
                        //liczba napotyka po drodze zajete pole o innej wartosci, zostaje umieszczona pole powyzej napotkanego zajetego pola
                    }
                    else if (plansza[X][y] != value){
                        plansza[x][y] = 0;
                        plansza[X-1][y] = value;
                    }
                    //liczba napotyka taka sama liczbe ale napotkana liczba została już podwojona w tym ruchu
                    else {
                        if (alreadyCombined[X]){
                            plansza[X-1][y] = value;
                            plansza[x][y] = 0;
                        }
                        //2 takie same liczby obok siebie, podwojenie liczby, która jest poniżej
                        else {
                            plansza[x][y] = 0;
                            plansza[X][y] *= 2;
                            score += plansza[X][y];
                            alreadyCombined[X] = true;

                        }
                    }
                }
            }
        }
    }
    /**Funkcja odpowiedzialna za przesuwanie liczb w lewo
     */
    public void wlewo(){
        for (int x = 0; x < 4; x++){
            boolean[] alreadyCombined = {false, false, false, false};
            for (int y = 1; y < 4; y++){
                if (plansza[x][y] != 0){
                    int value = plansza[x][y];
                    int Y = y - 1;
                    while((Y >= 0) && (plansza[x][Y] == 0)){
                        Y--;
                    }
                    //liczba dojezdza do lewego brzegu planszy:
                    if (Y == -1) {
                        plansza[x][0] = value;
                        plansza[x][y] = 0;
                        //liczba napotyka po drodze zajete pole o innej wartosci, zostaje umieszczona pole obok napotkanego zajetego pola
                    }
                    else if (plansza[x][Y] != value){
                        plansza[x][y] = 0;
                        plansza[x][Y+1] = value;

                    }
                    //liczba napotyka taka sama liczbe ale napotkana liczba została już podwojona w tym ruchu
                    else {
                        if (alreadyCombined[Y]){
                            plansza[x][Y+1] = value;
                            plansza[x][y] = 0;
                        }
                        //2 takie same liczby obok siebie, podwojenie liczby, która jest lewej
                        else {
                            plansza[x][y] = 0;
                            plansza[x][Y] *= 2;
                            score += plansza[x][Y];
                            alreadyCombined[Y] = true;

                        }
                    }
                }
            }
        }
    }
    /**Funkcja odpowiedzialna za przesuwanie liczb w prawo
     */
    public void wprawo(){
        for (int x = 0; x < 4; x++){
            boolean[] alreadyCombined = {false, false, false, false};
            for (int y = 2; y  > -1; y--){
                if (plansza[x][y] != 0){
                    int value = plansza[x][y];
                    int Y = y + 1;
                    while((Y <= 3) && (plansza[x][Y] == 0)){
                        Y++;
                    }
                    //liczba dojezdza do prawego brzegu planszy:
                    if (Y == 4) {
                        plansza[x][3] = value;
                        plansza[x][y] = 0;
                        //liczba napotyka po drodze zajete pole o innej wartosci, zostaje umieszczona pole obok napotkanego zajetego pola
                    }
                    else if (plansza[x][Y] != value){
                        plansza[x][y] = 0;
                        plansza[x][Y-1] = value;

                    }
                    //liczba napotyka taka sama liczbe ale napotkana liczba została już podwojona w tym ruchu
                    else {
                        if (alreadyCombined[Y]){
                            plansza[x][y] = 0;
                            plansza[x][Y-1] = value;
                        }
                        //2 takie same liczby obok siebie, podwojenie liczby, która jest po prawej
                        else {
                            plansza[x][y] = 0;
                            plansza[x][Y] *= 2;
                            score += plansza[x][Y];
                            alreadyCombined[Y] = true;
                        }
                    }
                }
            }
        }
    }
    /**metody sprawdzające sytuację na tablicy:
    */
     //zwraca true jeżeli jest liczba 2048
    public boolean czy2048(){
        for (int x = 0; x<4; x++){
            for (int y = 0; y<4; y++){
                if (plansza[x][y] == 2048){
                    return true;
                }
            }
        }
        return false;
    }
    //zwraca true jeżeli plansza jest w całości wypełniona
    public boolean czyPlanszaPelna(){
        for (int x = 0; x<4; x++){
            for (int y = 0; y<4; y++){
                if (plansza[x][y] == 0){
                    return false;
                }
            }
        }
        return true;

    }
    //zwraca true jeżeli na planszy sa obok siebie jakies liczby, ktore mozna dodac
    public boolean czyJestRuch(){
        for (int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                if (x == 0){
                    if(y != 0){
                        if (plansza[x][y] == plansza[x][y-1]){
                            return true;
                        }
                    }
                }
                else {
                    if(y != 0){
                        if(plansza[x][y] == plansza[x][y-1]){
                            return true;
                        }
                    }
                    if (plansza[x][y] == plansza[x-1][y]){
                        return true;
                    }
                }
            }
        }
        return false;//gracz nie ma ruchów
    }

    /**fukcja sprawdzająca status gry (koniec, kontynuacja, zwyciestwo)
     */
    public  void stanGry(){
        if (czy2048()){
            status = GameState.WYGRANA;
        }
        else if (czyPlanszaPelna()){
            if (czyJestRuch()){
                status = GameState.KONTYNUACJA;
            }
            else {
                status = GameState.PRZEGRANA;
            }
        }
        else {
            status = GameState.KONTYNUACJA;
        }
    }
}