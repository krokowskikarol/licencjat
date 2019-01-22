/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

import java.util.Random;

/**
 *Klasa reprezentująca Punkt.
 * Zawiera w sobie 3 zmienne prywatne: współrzędną x oraz y punktu
 * a także jego nazwę
 * Klasa zawiera 6 funkcji: 1 tworzącą Punkt w oparciu o z góry założony rozmiar
 * panelu na którym zostanie narysowany Comparator pozwalający na jednoznaczne
 * porównanie ich położenia, 3 pozwalające na pobranie jej zmiennych oraz 1 
 * pozwalającą na nadanie punktowi nazwy.
 * @author karol
 */
public class Punkt implements Comparable<Punkt>  {
    private Float _x;
    private Float _y;
    private String nazwa;
  

    public Punkt() {
            StworzPunkt();
    }
    /**
     *Służy do porównywania położenia punktów. 
     * Funkcja zwracająca int pozwalająca porównać 2 punkty pod 
     * wzgędem ich położenia w przestrzeni dwuwymiarowej
     */
    @Override
    public int compareTo(Punkt o) {
        if (this._x.equals(o._x)){
            return this._y.compareTo(o._y);
        }
        else{
            return this._x.compareTo(o._x);
        }
    }
    /**
     *Tworzy Punkt. 
     * Funkcja wywoływana w konstruktorze klasy Punkt
     * tworzy punkt o losowych współrzędnych typu float 
     * przy założeniu maksymalnej i minimalnej wielkości określonej 
     * sztywno dla panelu na którym punkt będzie reprezentowany. 
     */
    private void StworzPunkt(){
        int max = 250;
        int min = 2;
        Random r = new Random();
        Float random1 = min + r.nextFloat() * (max - min);
        Float random2 = min + r.nextFloat() * (max - min);
        this._x = random1;
        this._y = random2;
    }
    public float getX() {
        return _x;
    }

    public float getY() {
        return _y;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
    

    
    
    
    
}
