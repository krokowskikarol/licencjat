/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorytmy;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import javax.swing.JPanel;
import klasy.Krawedz;
import klasy.Punkt;
import klasy.Zbior;

/**
 * Klasa zawierająca funkcje pomocnicze i powtarzające się w innych klasach.
 *
 * @author karol
 */
public class FunkcjePomocnicze {

    public FunkcjePomocnicze() {

    }

    /**
     * funkcja mająca na celu połączenie dwóch list w jedną listę wynikową
     * zawierającą w sobie wszystkie punkty otoczki
     *
     * @param _AB obiekt typu Krawędź reprezenujący krawedź skierowaną.
     * @param _p obiekt typu Punkt reprezenujący punkt sprawdzany względem
     * krawędzi.
     * @return 1 dla punktu leżącego na prawo, 0 dla punktó współliniowego, -1
     * dla punktu leżacego na lewo.
     */
    public int SprawdzPolozenieWzgledemKrawedzi(Krawedz _AB, Punkt _p) {
        return (int) Math.signum(((_AB.getKoniec().getX() - _AB.getPoczatek().getX()) * (_p.getY() - _AB.getPoczatek().getY()))
                - ((_AB.getKoniec().getY() - _AB.getPoczatek().getY()) * (_p.getX() - _AB.getPoczatek().getX())));
    }

    /**
     * funkcja mająca na celu połączenie dwóch list w jedną listę wynikową
     * zawierającą w sobie wszystkie punkty otoczki
     *
     * @param _A obiekt typu Punkt reprezenujący pierwszy punkt od którego
     * rozpatrujemy skręt.
     * @param _B obiekt typu Punkt reprezenujący środkowy punkt od którego
     * rozpatrujemy skręt.
     * @param _p obiekt typu Punkt reprezenujący ostatni punkt od którego
     * rozpatrujemy skręt.
     * @return 1 jeżeli zachodzi skręt w prawo, 0 dla punktóW współliniowych, -1
     * dla skrętu w lewo.
     */
    public int SprawdzCzyZachodziSkretWPrawo(Punkt _A, Punkt _B, Punkt _p) {
        return (int) Math.signum(((_B.getX() - _A.getX()) * (_p.getY() - _A.getY()))
                - ((_B.getY() - _A.getY()) * (_p.getX() - _A.getX())));
    }

    /**
     * Funkcja tworząca reprezentację graficzną punktów po ich losowaniu. Tworzy
     * ona tablicę zawierającą panele o współrzędnych odpowiadających punktom
     * zbioru z którym zstała wywołana.
     *
     * @param S obiekt klasy Zbior na podstawie którego tworzone są
     * odpowiadające im panele.
     * @return Tablicę elementów typu JPanel gotowych do dodania na wybranym
     * panelu.
     */
    public JPanel[] Stworz_ReprezentacjePunktow(Zbior S) {

        JPanel[] punkty = new JPanel[S.getRozmiar()];
        for (int i = 0; i < S.getRozmiar(); i++) {

            punkty[i] = new JPanel();
            punkty[i].setSize(10, 10);
            punkty[i].setLocation((int) (S.getZbior()[i].getX() + 25), (int) (S.getZbior()[i].getY() + 30));
            punkty[i].setBackground(Color.red);
            punkty[i].setVisible(true);

        }
        return punkty;
    }

    /**
     * Funkcja stworzona w celu rysowania lini o określonym kolorze i grubości
     *
     * @param start obiekt typu Punkt reprezentujący początek linii.
     * @param koniec obiekt typu Punkt reprezentujący koniec linii.
     * @param grubosc obiekt typu int określający grubość lini w pixelach.
     * @param kolor obiekt typu Color określający kolor linii.
     * @param p obiekt typu JPanel określający na którym panelu odbędzie się
     * rysownie czy też bardziej precyzyjnie z którego pobana będzie grafika
     * (getGraphics())
     */
    public void Rysuj_Krawedz(Punkt start, Punkt koniec, int grubosc, Color kolor, JPanel p) {
        Graphics2D g = (Graphics2D) p.getGraphics();
        BasicStroke s = new BasicStroke((float) grubosc);
        g.setStroke(s);
        g.setColor(kolor);
        g.drawLine((int) (start.getX() + 30), (int) (start.getY() + 35), (int) (koniec.getX() + 30), (int) (koniec.getY() + 35));

        //proba stworzenia strzałki
    }

    /**
     * Funkcja stworzona do rysowania reprezentacji krawędzi wewnątrz legendy
     *
     * @param x obiekt typu int reprezentujący początek linii.
     * @param y obiekt typu int reprezentujący koniec linii.
     * @param grubosc obiekt typu int określający grubość lini w pixelach.
     * @param kolor obiekt typu Color określający kolor linii.
     * @param p obiekt typu JPanel określający na którym panelu odbędzie się
     * rysownie czy też bardziej precyzyjnie z którego pobana będzie grafika
     * (getGraphics())
     */
    public void Rysuj_KrawedzLegendy(int x, int y, int grubosc, Color kolor, JPanel p) {
        Graphics2D g = (Graphics2D) p.getGraphics();
        BasicStroke s = new BasicStroke((float) grubosc);
        g.setStroke(s);
        g.setColor(kolor);
        g.drawLine(x, y, x + 25, y - 10);

        //proba stworzenia strzałki
    }
    /**
     * Funkcja której zadaniem jest rysowanie krawędzi obliczonej otoczki.
     * @param _L Lista na podstawie której wybierane są kolejne punkty między którymi rysowana jest krawędź
     * @param kolor obiekt typu Color określający kolor linii.
     * @param p obiekt typu JPanel określający na którym panelu odbędzie się
     * rysownie czy też bardziej precyzyjnie z którego pobana będzie grafika
     * (getGraphics())
     */
    public void Rysuj_KrawedzieOtoczki(LinkedList<Punkt> _L, Color kolor, JPanel p) {
        Graphics2D g = (Graphics2D) p.getGraphics();
        BasicStroke s = new BasicStroke((float) 3);
        g.setStroke(s);
        g.setColor(kolor);
        for (int i = 0; i < _L.size(); i++) {
            if (i == _L.size() - 1) {
                g.drawLine((int) (_L.getLast().getX() + 30), (int) (_L.getLast().getY() + 35), (int) (_L.getFirst().getX() + 30), (int) (_L.getFirst().getY() + 35));
            } else {
                g.drawLine((int) (_L.get(i).getX() + 30), (int) (_L.get(i).getY() + 35), (int) (_L.get(i + 1).getX() + 30), (int) (_L.get(i + 1).getY() + 35));
            }
        }

    }
    /**
     * Funkcja której zadaniem jest rysowanie legendy będącej szybką
     * podpowiedzią co oznaczają elementy pojawiające się podczas prezentacji
     * działania algorytmu
     * @param jpLegenda  biekt typu JPanel określający na którym panelu odbędzie się
     * rysownie czy też bardziej precyzyjnie z którego pobana będzie grafika
     * (getGraphics())
     */
    public void RysujLegende(JPanel jpLegenda) {
        FunkcjePomocnicze test = new FunkcjePomocnicze();

        int x_rys = 10;
        Graphics grafika = jpLegenda.getGraphics();
        grafika.setColor(Color.red);
        grafika.fillRect(x_rys, 15, 12, 12);
        grafika.fillRect(x_rys, 35, 12, 12);
        grafika.fillRect(x_rys, 55, 12, 12);

        grafika.setColor(Color.green);
        grafika.fillRect(x_rys + 2, 37, 8, 8);
        grafika.fillRect(x_rys, 75, 12, 12);

        grafika.setColor(Color.blue);
        grafika.fillRect(x_rys, 95, 12, 12);

        grafika.setColor(Color.yellow);
        grafika.fillRect(x_rys + 2, 57, 8, 8);

        test.Rysuj_KrawedzLegendy(x_rys, 130, 2, Color.blue, jpLegenda);
        
        test.Rysuj_KrawedzLegendy(x_rys, 150, 2, Color.green.darker(), jpLegenda);

        // opisy 
        int x_napis = 30;
        grafika.setColor(Color.black);
        grafika.drawChars("Punkt".toCharArray(), 0, 5, x_napis, 25);
        grafika.drawChars("Punkt startowy".toCharArray(), 0, 14, x_napis, 45);
        grafika.drawChars("Punkt środkowy".toCharArray(), 0, 14, x_napis, 65);
        grafika.drawChars("Punkt końcowy".toCharArray(), 0, 13, x_napis, 85);
        grafika.drawChars("Punkt sprawdzany".toCharArray(), 0, 16, x_napis, 105);
        grafika.drawChars("Krawedź".toCharArray(), 0, 7, x_napis + 10, 125);
        grafika.drawChars("Zweryfikowana".toCharArray(), 0, 13, x_napis + 10, 145);
        grafika.drawChars("krawedź".toCharArray(), 0, 7, x_napis + 10, 157);

    }
}
