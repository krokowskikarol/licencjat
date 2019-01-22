/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorytmy;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import javax.swing.JPanel;
import klasy.Krawedz;
import klasy.Punkt;
import klasy.Zbior;

/**
 *Klasa reprezentująca Algorytm SlowConvexHull.
 * Klasa zawiera 3 pola reprezentujące czas działania algorytmu,
 * liczbę obrotów głównej pętli oraz panel na którym zostanie zaprezentowany.
 * Klasa zawiera 2 dopowiadające sobie werse algorymu w
 * wersji graficznej (działającej wolno i prezentującej kolejne kroki)
 * oraz wersji analitycznej działającej z pełną dostępną prędkością.
 * Ponadto klasa zawiera 2 funkcje StworzZbiorKrawedzi oraz PrzeniesListy 
 * niezbędne do działania algorytmu zgodnie z założeniami pracy,
 * oraz 5 funkcji obsługujących tryb graficzny.
 * @author karol
 */
public class SlowConvexHull {

    private long czas;
    private long obroty;
    private final JPanel panel;

    public SlowConvexHull(JPanel panel) {
        this.obroty = 0;
        this.czas = 0;
        this.panel = panel;
    }
    /**
     * Wersja Graficznej prezentacji Algorytmu 
     * @param _P zbior Punktow na podstawie których obliczana jest otoczka.
     * @return listę zawierającą wszystkie punkty otoczki.
     */
    public LinkedList<Punkt> AlgorytmSCH(Zbior _P) {
        
        LinkedList<Punkt> _L = new LinkedList<>();              //tworzenie głównej listy wynikowej zawierającej wierzchołki otoczki ułożone zgodnie z ruchem wskazówek zegara
        LinkedList<Krawedz> _E = new LinkedList<>();            //tworzenie listy  krawędzi
        Krawedz _PxP[] = StworzZbiorKrawedzi(_P);               // ze zbioru P punktów tworzymy zbior wszystkich możliwych krawędzi 
        FunkcjePomocnicze test = new FunkcjePomocnicze();       //tworzenie obiektu funkcji pomocniczych

        //poczatek obliczania algorytmu
        for (int j = 0; j < _PxP.length; j++) {                 //sprawdzanie czy punkt nie jestpoczątkiem lub końcem krawędzi
            Rysuj_SprawdzanaKrawedz(_PxP, j);
            for (int i = 0; i < _P.getRozmiar(); i++) {
                if (_P.getZbior()[i] != _PxP[j].getPoczatek() && _P.getZbior()[i] != _PxP[j].getKoniec()) {
                    RysujNazwyPunktow(_P, Color.BLACK);         //rysowanie nazw punktow
                    Ruysuj_SprawdzanePunkty(_P, i);              //kolorowanie przeszukiwanych punktow

                    if (test.SprawdzPolozenieWzgledemKrawedzi(_PxP[j], _P.getZbior()[i]) != 1) {    //sprawdzanie położenia punktu względem krawędzi
                        _PxP[j].setValid(false);
                    }
                }
            }
            Usun_NieaktualnaKrawedz(_PxP, j);        //usuwanie krawedzi usuwanie kolorow punktow otoczki przy opuszczaniu krawedzi

            if (_PxP[j].isValid()) {
                _E.add(_PxP[j]);

                Rysuj_ZweryfikowaneKrawedzie(_E, Color.GREEN.darker());    //po kazdym obrocie dodaje juz zweryfikowane krawedzie
            }
        }
        PrzeniesListy(_E, _L);

        return _L;
    }
    /**
     * Wersja Analityczna Algorytmu przeznaczona gla ogromnej ilości punktów.
     * @param _P zbior punktow na podstawie których będzie obliczana otoczka.
     * @return listę zawierającą wszystkie punkty otoczki.
     */
    public LinkedList<Punkt> AlgorytmSCH_Big(Zbior _P) {
        long startTime = System.currentTimeMillis();
        
        LinkedList<Punkt> _L = new LinkedList<>();              //tworzenie głównej listy wynikowej zawierającej wierzchołki otoczki ułożone zgodnie z ruchem wskazówek zegara
        LinkedList<Krawedz> _E = new LinkedList<>();            //tworzenie listy  krawędzi
        Krawedz _PxP[] = StworzZbiorKrawedzi(_P);               // ze zbioru P punktów tworzymy zbior wszystkich możliwych krawędzi 
        FunkcjePomocnicze test = new FunkcjePomocnicze();       //tworzenie obiektu funkcji pomocniczych
        for (Krawedz _PxP1 : _PxP) {
            //sprawdzanie czy punkt nie jestpoczątkiem lub końcem krawędzi
            for (int i = 0; i < _P.getRozmiar(); i++) {
                if (_P.getZbior()[i] != _PxP1.getPoczatek() && _P.getZbior()[i] != _PxP1.getKoniec()) {
                    this.obroty++;
                    if (test.SprawdzPolozenieWzgledemKrawedzi(_PxP1, _P.getZbior()[i]) != 1) {
                        //sprawdzanie położenia punktu względem krawędzi
                        _PxP1.setValid(false);
                    }
                }
            }
            if (_PxP1.isValid()) {
                _E.add(_PxP1);
            }
        }
        PrzeniesListy(_E, _L);

        long endTime = System.currentTimeMillis();
        czas = endTime - startTime;
        
        return _L;
    }
    /**
     * 
     * @param _P zbior punktow na podstawie których będzie tworzony zbiór wszystkich możliwych krawedzi.
     * @return tablicę wszystkich istniejących wewnątrz zbioru krawedzi. 
     */
    public Krawedz[] StworzZbiorKrawedzi(Zbior _P) {
        int pozycja = 0;
        Krawedz _S[] = new Krawedz[(_P.getRozmiar() * _P.getRozmiar()) - _P.getRozmiar()];
        for (int i = 0; i < _P.getRozmiar(); i++) {
            for (int j = 0; j < _P.getRozmiar(); j++) {
                if (i != j) {
                    _S[pozycja] = new Krawedz(_P.getZbior()[i], _P.getZbior()[j]);
                    pozycja++;
                }
            }
        }
        return _S;
    }
    /**
     * Funkcja tworżca na podstawie listy zawierającej krawędzie
     * listę wynikową z punktami otoczki.
     *
     * @param _E Lista zawierająca krawędzie z któwych pozyskane będą punkty otoczki
     * @param _L lista wynikowa zawierająca obliczone w toku działania algorytmu 
     *           punkty otoczki.
     */
    public void PrzeniesListy(LinkedList<Krawedz> _E, LinkedList<Punkt> _L) {
        _L.add(_E.getFirst().getPoczatek());
        while (_E.isEmpty() == false) {
            for (int i = 0; i < _E.size(); i++) {
                if (_E.get(i).getPoczatek() == _L.getLast()) {
                    _L.add(_E.get(i).getKoniec());
                    _E.remove(_E.get(i));
                }
            }
        }
        _L.removeLast();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // funkcje graficzne//
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 *Funkcja rysująca nazwy poszczegolnych punktow zbioru
 * @param _P zbiór zawirający elementy typu Punkt.
 * @param kolor kolor rysowania elementu
 */
    public void RysujNazwyPunktow(Zbior _P, Color kolor) {
        //rysuje nazwy punktow
        Graphics g = panel.getGraphics();
        g.setColor(kolor);

        for (int i = 0; i < _P.getRozmiar(); i++) {
            g.drawChars(_P.getZbior()[i].getNazwa().toCharArray(), 0, 2, Math.round(_P.getZbior()[i].getX() + 23), Math.round(_P.getZbior()[i].getY() + 19));
            //
        }
    }
/**
 * Funkcja kolorująca punkt którego położenie obecnie sprawdzamy.
 * @param _P zbiór zawirający elementy typu Punkt.
 * @param iterator icznik określający który punkt bierzemu pod uwagę w damym momęcie
 */
    public void Ruysuj_SprawdzanePunkty(Zbior _P, int iterator) {
        //kolorowanie przeszukiwanych punktow
        Graphics g = panel.getGraphics();
        g.setColor(Color.blue);
        g.fillRect(Math.round(_P.getZbior()[iterator].getX() + 23), Math.round(_P.getZbior()[iterator].getY() + 29), 12, 12);
        try {
            Thread.sleep(100);                 //100 milliseconds is one second.
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        g.setColor(Color.red);
        g.fillRect(Math.round(_P.getZbior()[iterator].getX() + 23), Math.round(_P.getZbior()[iterator].getY() + 29), 12, 12);
        //
    }
    /**
     * Funkcja rysująca krawędz względem której będziemy sprawdzać położenie kolejnych punktów.
     * @param _PxP tablica zawierająca wszystkie występujące w zbiorze krawędzie.
     * @param iterator icznik określający którą krawędź bierzemu pod uwagę w damym momęcie
     */
    public void Rysuj_SprawdzanaKrawedz(Krawedz _PxP[], int iterator) {
        //ToDo zwolnienie i kolorki
        Graphics g = panel.getGraphics();
        FunkcjePomocnicze test = new FunkcjePomocnicze();
        test.Rysuj_Krawedz(_PxP[iterator].getPoczatek(), _PxP[iterator].getKoniec(), 2, Color.blue, panel);           //rysowanie krawedzi
        g.setColor(Color.green);
        g.fillRect(Math.round(_PxP[iterator].getPoczatek().getX() + 25), Math.round(_PxP[iterator].getPoczatek().getY() + 31), 8, 8);
        g.fillRect(Math.round(_PxP[iterator].getKoniec().getX() + 23), Math.round(_PxP[iterator].getKoniec().getY() + 29), 12, 12);
        /// koniec kolorowania wstepnego punktow tworzacych krawedz
    }
/**
 * Funkcja rysująca na już sprawdzone krawędzie
 * @param _E lista zawierająca zweryfikowane krawędzie
 * @param kolor kolor rysowania elementu
 */
    public void Rysuj_ZweryfikowaneKrawedzie(LinkedList<Krawedz> _E, Color kolor) {
        FunkcjePomocnicze test = new FunkcjePomocnicze();
        _E.forEach((krawedz) -> {
            test.Rysuj_Krawedz(krawedz.getPoczatek(), krawedz.getKoniec(), 3, kolor, panel);
        });
    }
    /**
     * Funkcja której zadaniem jest zamalowywanie nieaktualnych krawedzi kolorem tła.
     * @param _PxP tablica zawierająca wszystkie występujące w zbiorze krawędzie.
     * @param iterator icznik określający którą krawędź bierzemu pod uwagę w damym momęcie
     */
    public void Usun_NieaktualnaKrawedz(Krawedz _PxP[], int iterator) {
        //usuwanie krawedzi
        Graphics g = panel.getGraphics();
        FunkcjePomocnicze test = new FunkcjePomocnicze();
        Color usun = new Color(238, 238, 238);
        test.Rysuj_Krawedz(_PxP[iterator].getPoczatek(), _PxP[iterator].getKoniec(), 2, usun, panel);
        //
        //usuwanie kolorow punktow otoczki przy opuszczaniu krawedzi
        g.setColor(Color.red);
        g.fillRect(Math.round(_PxP[iterator].getPoczatek().getX() + 23), Math.round(_PxP[iterator].getPoczatek().getY() + 29), 12, 12);
        g.fillRect(Math.round(_PxP[iterator].getKoniec().getX() + 23), Math.round(_PxP[iterator].getKoniec().getY() + 29), 12, 12);
    }
    


    public long getObroty() {
        return obroty;
    }

    public long getCzas() {
        return czas;
    }
}
