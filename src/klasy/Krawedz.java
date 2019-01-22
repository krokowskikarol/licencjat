/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klasy;

/**
 Klasa reprezentująca krawędź pomiędzy dwoma Punktami.
 * Zawiera w sobie 3 zmienne prywatne: Punkt początkowy i końcowy krawędzi 
 * oraz zmienną _valid reprezentującą czy dana krawędź
 * jest krawędzią otoczki czy też nie.
 * Klasa zawiera 4 funkcje: 3 pozwalające na pobranie jej zmiennych 
 * oraz jedną pozwalająca na zmianę wartości zmiennej _valid.
 * @author karol
 */
public class Krawedz {
    private final Punkt _poczatek;
    private final Punkt _koniec;
    private boolean _valid;

    public Krawedz(Punkt poczatek, Punkt koniec) {
        this._poczatek = poczatek;
        this._koniec = koniec;
        this._valid = true;
    }

    public Punkt getKoniec() {
        return _koniec;
    }

    public Punkt getPoczatek() {
        return _poczatek;
    }

    public boolean isValid() {
        return _valid;
    }

    public void setValid(boolean _valid) {
        this._valid = _valid;
    }
    

    
    
}
