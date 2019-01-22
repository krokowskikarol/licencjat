
package klasy;

/**
 *Klasa reprezentująca zbiór elementów klasy Punkt.
 * Zawiera w sobie 2 zmienne prywatne: tablicę Punktów oraz informację 
 * o rozmiarze zbioru.
 * Klasa zawiera 3 funkcje: 1 tworzącą zbiór oraz 2 pozwalające na pobranie jej zmiennych.
 * @author karol
 */
public class Zbior {
    private final int _rozmiar;
    private final Punkt[] _zbior;

    public Zbior(int _rozmiar) {
        this._rozmiar = _rozmiar;
        this._zbior = new Punkt[_rozmiar];
        StworzZbior();
    }
      
    /**
     *Tworzy zbiór punktów. 
     * Funkcja wywoływana w konstruktorze klasy Zbior
     * służąca do stworzenia zbioru punktów o rozmiarze
     * podanym w konstruktorze oraz nadania im nazw.
     */
    public void StworzZbior() {
        for(int i = 0; i < _rozmiar; i++){
            _zbior[i] = new Punkt();
            String n = "p" + i;
            _zbior[i].setNazwa(n);
        }
    }

    public int getRozmiar() {
        return _rozmiar;
    }

    public Punkt[] getZbior() {
        return _zbior;
    }
    
}
