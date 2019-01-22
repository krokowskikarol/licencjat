/**
 * Zawiera algorytmy SlowConvexHull, FastConvexHull oraz FunkcjePomocnicze
 */
package Algorytmy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JPanel;
import klasy.Punkt;
import klasy.Zbior;

/**
 * Klasa reprezentująca Algorytm FastConvexHull. Klasa zawiera 3 pola
 * reprezentujące czas działania algorytmu, liczbę obrotów głównej pętli oraz
 * panel na którym zostanie zaprezentowany. Klasa zawiera 2 dopowiadające sobie
 * werse algorymu w wersji graficznej (działającej wolno i prezentującej kolejne
 * kroki) oraz wersji analitycznej działającej z pełną dostępną prędkością.
 * Ponadto klasa zawiera 2 funkcje SortojZbior oraz PolaczListy niezbędne do
 * działania algorytmu zgodnie z założeniami pracy, oraz 7 funkcji obsługujących
 * tryb graficzny.
 *
 * @author karol
 */
public class FastConvexHull {

    private long czas;
    private int obroty;
    private final JPanel panel;

    public FastConvexHull(JPanel panel) {
        this.panel = panel;
    }

    /**
     * Wersja Graficznej prezentacji Algorytmu
     *
     * @param _P zbior Punktow na podstawie których obliczana jest otoczka.
     * @return listę zawierającą wszystkie punkty otoczki.
     */
    public LinkedList<Punkt> AlgorytmWydajnyCH(Zbior _P) {

        SortojZbior(_P);                                        //sortowanie punktow wzgledem wsp. x
        LinkedList<Punkt> _L;                                    //tworzenie list
        LinkedList<Punkt> _Lupper = new LinkedList<>();
        LinkedList<Punkt> _Llower = new LinkedList<>();
        FunkcjePomocnicze test = new FunkcjePomocnicze();

        Rysuj_NazwyPunktow(_P, Color.black);
        //    obliczanie górnej części otoczki
        _Lupper.add(_P.getZbior()[0]);
        _Lupper.add(_P.getZbior()[1]);

        for (int i = 2; i < _P.getRozmiar(); i++) {
            Usun_NieaktualnePunkty(_Lupper);

            _Lupper.add(_P.getZbior()[i]);

            Punkt _koniec = _Lupper.getLast();
            int _ostatni = _Lupper.indexOf(_koniec);
            Punkt _srodek = _Lupper.get(_ostatni - 1);
            Punkt _poczatek = _Lupper.get(_ostatni - 2);

            Rysuj_PrzebiegObliczen(_poczatek, _srodek, _koniec);

            while (_Lupper.size() > 2 && test.SprawdzCzyZachodziSkretWPrawo(_poczatek, _srodek, _koniec) != 1) {
                _Lupper.remove(_srodek);

                //przywrocenie oryginalnego koloru i usuniecie krewedzi, poprawienie nazw punktow
                Usun_Krawedz(_poczatek, _srodek, _koniec);
                Rysuj_NazwyPunktow(_P, Color.black);
                //
                if (_Lupper.size() > 2) {
                    _ostatni = _Lupper.indexOf(_koniec);
                    _srodek = _Lupper.get(_ostatni - 1);
                    _poczatek = _Lupper.get(_ostatni - 2);

                }
            }Rysuj_ObliczoneKrawedzie(_Lupper);
        }
        

//    Obliczanie dolnej części otoczki
        _Llower.add(_P.getZbior()[_P.getRozmiar() - 1]);
        _Llower.add(_P.getZbior()[_P.getRozmiar() - 2]);

        for (int j = _P.getRozmiar() - 3; j >= 0; j--) {
            Usun_NieaktualnePunkty(_Llower);

            _Llower.add(_P.getZbior()[j]);

            Punkt _koniec = _Llower.getLast();
            int _ostatni = _Llower.indexOf(_koniec);
            Punkt _srodek = _Llower.get(_ostatni - 1);
            Punkt _poczatek = _Llower.get(_ostatni - 2);

            Rysuj_PrzebiegObliczen(_poczatek, _srodek, _koniec);

            while (_Llower.size() > 2 && test.SprawdzCzyZachodziSkretWPrawo(_poczatek, _srodek, _koniec) != 1) {
                _Llower.remove(_srodek);

                //przywrocenie oryginalnego koloru i usuniecie krewedzi, poprawienie nazw punktow
                Usun_Krawedz(_poczatek, _srodek, _koniec);
                Rysuj_NazwyPunktow(_P, Color.black);
                //
                if (_Llower.size() > 2) {
                    _ostatni = _Llower.indexOf(_koniec);
                    _srodek = _Llower.get(_ostatni - 1);
                    _poczatek = _Llower.get(_ostatni - 2);
                }
            }Rysuj_ObliczoneKrawedzie(_Llower);
        }
        
        _L = PolaczListy(_Lupper, _Llower);
        Usun_NieaktualnePunkty(_L);
        return _L;
    }

    /**
     * Wersja Analityczna Algorytmu przeznaczona gla ogromnej ilości punktów.
     *
     * @param _P zbior punktow na podstawie których będzie obliczana otoczka.
     * @return listę zawierającą wszystkie punkty otoczki.
     */
    public LinkedList<Punkt> AlgorytmWydajnyCH_Big(Zbior _P) {
        long startTime = System.currentTimeMillis();

        SortojZbior(_P);                                        //sortowanie punktow wzgledem wsp. x
        LinkedList<Punkt> _L;                                    //tworzenie list
        LinkedList<Punkt> _Lupper = new LinkedList<>();
        LinkedList<Punkt> _Llower = new LinkedList<>();
        FunkcjePomocnicze test = new FunkcjePomocnicze();

        //    obliczanie górnej części otoczki
        _Lupper.add(_P.getZbior()[0]);
        _Lupper.add(_P.getZbior()[1]);

        for (int i = 2; i < _P.getRozmiar(); i++) {

            _Lupper.add(_P.getZbior()[i]);

            Punkt _koniec = _Lupper.getLast();
            int _ostatni = _Lupper.indexOf(_koniec);
            Punkt _srodek = _Lupper.get(_ostatni - 1);
            Punkt _poczatek = _Lupper.get(_ostatni - 2);

            obroty++;
            while (_Lupper.size() > 2 && test.SprawdzCzyZachodziSkretWPrawo(_poczatek, _srodek, _koniec) != 1) {
                _Lupper.remove(_srodek);

                obroty++;
                if (_Lupper.size() > 2) {
                    _ostatni = _Lupper.indexOf(_koniec);
                    _srodek = _Lupper.get(_ostatni - 1);
                    _poczatek = _Lupper.get(_ostatni - 2);

                }
            }
        }

//    Obliczanie dolnej części otoczki
        _Llower.add(_P.getZbior()[_P.getRozmiar() - 1]);
        _Llower.add(_P.getZbior()[_P.getRozmiar() - 2]);

        for (int j = _P.getRozmiar() - 3; j >= 0; j--) {

            _Llower.add(_P.getZbior()[j]);

            Punkt _koniec = _Llower.getLast();
            int _ostatni = _Llower.indexOf(_koniec);
            Punkt _srodek = _Llower.get(_ostatni - 1);
            Punkt _poczatek = _Llower.get(_ostatni - 2);

            obroty++;
            while (_Llower.size() > 2 && test.SprawdzCzyZachodziSkretWPrawo(_poczatek, _srodek, _koniec) != 1) {
                _Llower.remove(_srodek);
                obroty++;

                if (_Llower.size() > 2) {
                    _ostatni = _Llower.indexOf(_koniec);
                    _srodek = _Llower.get(_ostatni - 1);
                    _poczatek = _Llower.get(_ostatni - 2);
                }
            }
        }
        _L = PolaczListy(_Lupper, _Llower);

        //mierzenie czasu
        long endTime = System.currentTimeMillis();
        czas = endTime - startTime;

        return _L;
    }

    /**
     * funkcja której zadaniem jest sortowanie punktów w podanym zbiorze zgodnie
     * ze stworzonym dla danych elementów comparatorem
     *
     * @param _P zbiór punktów
     */
    public void SortojZbior(Zbior _P) {
        Arrays.sort(_P.getZbior());
        for (int i = 0; i < _P.getRozmiar(); i++) {
        }
    }

    /**
     * @author karol
     * @param _Lgorna lista reprezentujaca gorne punkty otoczki
     * @param _Ldolna lista reprezentujaca dolne punkty otoczki
     * @return połączona lista funkcja mająca na celu połączenie dwóch list w
     * jedną listę wynikową zawierającą w sobie wszystkie punkty otoczki
     */
    public LinkedList PolaczListy(LinkedList<Punkt> _Lgorna, LinkedList<Punkt> _Ldolna) {
        _Ldolna.removeFirst();
        _Ldolna.removeLast();
        for (int i = 0; i < _Ldolna.size(); i++) {
            _Lgorna.add(_Ldolna.get(i));
        }
        return _Lgorna;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //funkcje graficzne//
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Funkcja rysująca nazwy poszczegolnych punktow zbioru
     *
     * @param _P zbiór zawirający elementy typu Punkt.
     * @param kolor kolor rysowania elementu
     */
    public void Rysuj_NazwyPunktow(Zbior _P, Color kolor) {
        //rysuje nazwy punktow
        Graphics g = panel.getGraphics();
        g.setColor(kolor);

        for (int i = 0; i < _P.getRozmiar(); i++) {
            g.drawChars(_P.getZbior()[i].getNazwa().toCharArray(), 0, 2, Math.round(_P.getZbior()[i].getX() + 23), Math.round(_P.getZbior()[i].getY() + 19));
            //
        }
    }

    /**
     * Funkcja rysująca przebieg obliczeń (sprawdzania czy pomiędzy kolejnymi 3
     * punktami zachodzi skręt w prawo)
     *
     * @param _poczatek początkowy punkt dla którego sprawdzamy kierunek skrętu
     * @param _srodek środkowy punkt dla którego sprawdzamy kierunek skrętu
     * @param _koniec końcowy punkt dla którego sprawdzamy kierunek skrętu
     */
    public void Rysuj_PrzebiegObliczen(Punkt _poczatek, Punkt _srodek, Punkt _koniec) {
        FunkcjePomocnicze test = new FunkcjePomocnicze();
        test.Rysuj_Krawedz(_poczatek, _srodek, 2, Color.blue, panel);
        test.Rysuj_Krawedz(_srodek, _koniec, 2, Color.blue, panel);
        Graphics g = panel.getGraphics();
        PoprawPunkty(_poczatek, _srodek, _koniec);
        Rysuj_ObliczanePunkty(_poczatek, _srodek, _koniec, 2000);
    }

    /**
     * Funkca przywracająca oryginalny kolor punktów ktore nie są już obliczane.
     *
     * @param lista lista na podstawie ktorej wybierane są punkty do rysowania
     */
    public void Usun_NieaktualnePunkty(LinkedList<Punkt> lista) {
        //usuniecie kolorow nieaktualnych punktow
        Graphics g = panel.getGraphics();
        if (lista.size() > 2) {
            g.setColor(Color.red);
            g.fillRect(Math.round(lista.getFirst().getX() + 23), Math.round(lista.getFirst().getY() + 29), 12, 12);
            g.fillRect(Math.round(lista.get(lista.indexOf(lista.getLast()) - 2).getX() + 23), Math.round(lista.get(lista.indexOf(lista.getLast()) - 2).getY() + 29), 12, 12);
            g.fillRect(Math.round(lista.getLast().getX() + 23), Math.round(lista.getLast().getY() + 29), 12, 12);
        }
        //  
    }

    /**
     * * Funkcja której zadaniem jest zamalowywanie nieaktualnych krawedzi
     * kolorem tła.
     *
     * @param _p początkowy punkt rysowania krawędzi.
     * @param _s środkowy punkt rysowania krawędzi.
     * @param _k końcowy punkt rysowania krawędzi.
     */
    public void Usun_Krawedz(Punkt _p, Punkt _s, Punkt _k) {
        //przywrocenie oryginalnego koloru i usuniecie krewedzi
        Color usun = new Color(238, 238, 238);
        FunkcjePomocnicze t = new FunkcjePomocnicze();
        Graphics g = panel.getGraphics();
        t.Rysuj_Krawedz(_p, _s, 2, usun, panel);
        t.Rysuj_Krawedz(_s, _k, 2, usun, panel);
        g.setColor(Color.red);
        g.fillRect(Math.round(_s.getX() + 23), Math.round(_s.getY() + 29), 12, 12);
        //

    }

    /**
     * Funkca przywracająca oryginalny kolor punktów ktore nie są już obliczane.
     *
     * @param _p pierwszy punkt.
     * @param _s kolejny punkt.
     * @param _k ostatni punkt.
     */
    public void PoprawPunkty(Punkt _p, Punkt _s, Punkt _k) {
        //poprawianie startowych punktow
        Graphics g = panel.getGraphics();
        g.setColor(Color.red);
        g.fillRect(Math.round(_p.getX() + 23), Math.round(_p.getY() + 29), 12, 12);
        g.fillRect(Math.round(_k.getX() + 23), Math.round(_k.getY() + 29), 12, 12);
        g.fillRect(Math.round(_s.getX() + 23), Math.round(_s.getY() + 29), 12, 12);
        //
    }

    /**
     * Funkcja kororująca zgodnie z założeniami obliczane punkty
     *
     * @param _poczatek początkowy punkt dla którego sprawdzamy kierunek skrętu
     * @param _srodek środkowy punkt dla którego sprawdzamy kierunek skrętu
     * @param _koniec końcowy punkt dla którego sprawdzamy kierunek skrętu
     * @param _sleepTime czas na który zatrzymuje się algorytm pozwalając
     * zobaczyć dan krok obliczen.
     */
    public void Rysuj_ObliczanePunkty(Punkt _poczatek, Punkt _srodek, Punkt _koniec, int _sleepTime) {
        //oznaczanie odpowiednich punktow
        Graphics g = panel.getGraphics();
        g.setColor(Color.green);
        g.fillRect(Math.round(_poczatek.getX() + 25), Math.round(_poczatek.getY() + 31), 8, 8);
        g.fillRect(Math.round(_koniec.getX() + 23), Math.round(_koniec.getY() + 29), 12, 12);
        g.setColor(Color.yellow);
        g.fillRect(Math.round(_srodek.getX() + 26), Math.round(_srodek.getY() + 32), 6, 6);
        //pauza
        try {
            Thread.sleep(_sleepTime);                 //1000 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        //koniec rysowania
    }

    /**
     * Funkcja rysująca na już sprawdzone krawędzie
     *
     * @param _L lista na podstawie któwej obliczane są krawędzie do narysowania
     */
    public void Rysuj_ObliczoneKrawedzie(LinkedList<Punkt> _L) {
        FunkcjePomocnicze t = new FunkcjePomocnicze();
        for (int i = 0; i < _L.size() - 1; i++) {
            t.Rysuj_Krawedz(_L.get(i), _L.get(i + 1), 2, Color.GREEN.darker(), panel);
        }
    }

    public long getCzas() {
        return czas;
    }

    public int getObroty() {
        return obroty;
    }

}
