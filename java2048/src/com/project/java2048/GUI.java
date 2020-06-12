package com.project.java2048;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import javax.swing.*;
/**
 * Klasa GUI odpowiada za interakcję gry z graczem
 * Zawiera kod tworzący poszczególne ramki oraz komponenty w nich zawarte
 */
public class GUI {

    int rozmiarplanszy = 300;
    int szerokoscramki = 336;
    int margines = 18;
    int wysokoscramki = 394;

    Color kolortla = new Color(100,255,150);
    Font duza = new Font("SansSerif", 0, 20);
    Font mala = new Font("SansSerif", 0, 12);
    Game gra;

    JLabel wynik;
    Map grafika;
    GameBoard plansza;
    MyFrame ramka;
    public GUI(){
        gra = new Game();
        ramka = new MyFrame();
        ramka.setFocusable(true);
        ramka.addKeyListener(new MyFrame());
        ramka.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadNumberGrafs();
        plansza = new GameBoard();

        //Panel górny
        JPanel gornyPanel = new JPanel();
        gornyPanel.setLayout(new GridLayout());
        gornyPanel.setPreferredSize(new Dimension(szerokoscramki, 82));

        JLabel nazwaGry = new JLabel("2048", SwingConstants.CENTER);
        nazwaGry.setFont(new Font("Serif", Font.BOLD, 20));
        gornyPanel.add(nazwaGry);
        wynik = new JLabel("Twój wynik: 0", SwingConstants.CENTER);
        gornyPanel.add(wynik);
        gornyPanel.setBackground(kolortla);

        //Panel dolny
        JPanel dolnyPanel = new JPanel();
        dolnyPanel.setPreferredSize(new Dimension(szerokoscramki, margines));
        dolnyPanel.setBackground(kolortla);

        //Panel lewy
        JPanel lewyPanel = new JPanel();
        lewyPanel.setPreferredSize(new Dimension(margines, rozmiarplanszy));
        lewyPanel.setBackground(kolortla);

        //Panel prawy
        JPanel prawyPanel = new JPanel();
        prawyPanel.setPreferredSize(new Dimension(margines, rozmiarplanszy));
        prawyPanel.setBackground(kolortla);


        //dodanie powyższych paneli do ramki
        ramka.getContentPane().add(gornyPanel, BorderLayout.NORTH);
        ramka.getContentPane().add(dolnyPanel, BorderLayout.SOUTH);
        ramka.getContentPane().add(lewyPanel, BorderLayout.WEST);
        ramka.getContentPane().add(prawyPanel, BorderLayout.EAST);
        ramka.getContentPane().add(plansza, BorderLayout.CENTER);

        ramka.getContentPane().setPreferredSize(new Dimension(szerokoscramki, wysokoscramki));
        ramka.pack();
        ramka.setVisible(true);

    }

    /**Funkcja odpowiedzialna za przypisanie odpowiedniej grafiki do poszczególnych wartości pól
     */
    private void loadNumberGrafs(){
        grafika = new Hashtable();
        ClassLoader cldr = this.getClass().getClassLoader();
        URL url0000 = cldr.getResource("images/graf00_64.png");
        URL url0002 = cldr.getResource("images/graf02_64.png");
        URL url0004 = cldr.getResource("images/graf04_64.png");
        URL url0008 = cldr.getResource("images/graf08_64.png");
        URL url0016 = cldr.getResource("images/graf16_64.png");
        URL url0032 = cldr.getResource("images/graf32_64.png");
        URL url0064 = cldr.getResource("images/graf64_64.png");
        URL url0128 = cldr.getResource("images/graf128_64.png");
        URL url0256 = cldr.getResource("images/graf256_64.png");
        URL url0512 = cldr.getResource("images/graf512_64.png");
        URL url1024 = cldr.getResource("images/graf1024_64.png");
        URL url2048 = cldr.getResource("images/graf2048_64.png");
        grafika.put(0, new ImageIcon((url0000)));
        grafika.put(2, new ImageIcon((url0002)));
        grafika.put(4, new ImageIcon((url0004)));
        grafika.put(8, new ImageIcon((url0008)));
        grafika.put(16, new ImageIcon((url0016)));
        grafika.put(32, new ImageIcon((url0032)));
        grafika.put(64, new ImageIcon((url0064)));
        grafika.put(128, new ImageIcon((url0128)));
        grafika.put(256, new ImageIcon((url0256)));
        grafika.put(512, new ImageIcon((url0512)));
        grafika.put(1024, new ImageIcon((url1024)));
        grafika.put(2048, new ImageIcon((url2048)));

    }

    /**Klasa opisująca planszę z przypisanymi do wartości pól liczbami
     *
     */
    class GameBoard extends JPanel{
        @Override protected void paintComponent(Graphics g){
            g.setColor(new Color(20, 20, 20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            int[][] board = gra.getGameBoard();
            for (int y = 1; y <5; y++){
                for (int x = 1; x<5; x++){
                    int X = (8 * x) + (64*(x-1));
                    int Y = (8 * y) + (64*(y-1));
                    int thisNumber = board[y-1][x-1];

                    if (grafika.containsKey(thisNumber)) {
                        ImageIcon thisGraf = (ImageIcon)grafika.get(thisNumber);
                        thisGraf.paintIcon(this, g, X, Y);
                    }
                }
            }
        }


    }

    /**Klasa opisująca ramkę z napisem po wygraniu
     */
    class Winning extends  JPanel{
        @Override
        protected void paintComponent(Graphics g){
            g.setColor(new Color(20,20,20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(duza);
            g.setColor(new Color(0, 80, 0));
            g.drawString("Good job!", 25, 40);
            g.setFont(mala);
            g.setColor(new Color(255, 255, 255));
            g.drawString("Wciśnij ENTER aby zagrać ponownie", 25, 75);
        }
    }
    /**Klasa opisująca ramkę z napisem po przegraniu
     */
    class Loosing extends  JPanel{
        @Override
        protected void paintComponent(Graphics g){
            g.setColor(new Color(20,20,20));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            g.setFont(duza);
            g.setColor(new Color(200, 0, 0));
            g.drawString(":( Spróbuj jeszcze raz.", 25, 40);
            g.setFont(mala);
            g.setColor(new Color(255, 255, 255));
            g.drawString("Wciśnij ENTER aby zagrać ponownie", 25, 75);
        }
    }

    /**Klasa implementująca interfejs KeyListener, który umożliwia wykonanie czynności po wciśnięciu oodpowiedniego klawisza
     */
    class MyFrame extends JFrame implements KeyListener{
        @Override
        public void keyPressed(KeyEvent e){}
        @Override
        public void keyReleased(KeyEvent e){
            int key = e.getKeyCode();
            if (gra.getState() == GameState.KONTYNUACJA) {
                if (key == KeyEvent.VK_UP) {
                    gra.wgore();
                    gra.dodajNumer();
                    gra.stanGry();
                    plansza.repaint();
                    wynikUpdate();
                } else if (key == KeyEvent.VK_DOWN) {
                    gra.wdol();
                    gra.dodajNumer();
                    gra.stanGry();
                    plansza.repaint();
                    wynikUpdate();
                } else if (key == KeyEvent.VK_LEFT) {
                    gra.wlewo();
                    gra.dodajNumer();
                    gra.stanGry();
                    plansza.repaint();
                    wynikUpdate();
                } else if (key == KeyEvent.VK_RIGHT) {
                    gra.wprawo();
                    gra.dodajNumer();
                    gra.stanGry();
                    plansza.repaint();
                    wynikUpdate();
                }
                //wyswietlanie opcji po zakończeniu gry(wygrana, przegrana)
                GameState gs = gra.getState();
                if (gs == GameState.PRZEGRANA){
                    ramka.getContentPane().remove(plansza);
                    ramka.getContentPane().add(new Loosing(), BorderLayout.CENTER);
                    ramka.getContentPane().invalidate();
                    ramka.getContentPane().validate();
                }
                else if (gs == GameState.WYGRANA){
                    ramka.getContentPane().remove(plansza);
                    ramka.getContentPane().add(new Winning(), BorderLayout.CENTER);
                    ramka.getContentPane().invalidate();
                    ramka.getContentPane().validate();
                }
                //tworzenie nowej gry po wciśnięciu ENTERa
            } else {
                if (key == KeyEvent.VK_ENTER){
                    gra = new Game();
                    ramka.getContentPane().remove(((BorderLayout)getLayout()).getLayoutComponent(BorderLayout.CENTER));
                    ramka.getContentPane().add(plansza);
                    plansza.repaint();
                    ramka.getContentPane().invalidate();
                    ramka.getContentPane().validate();
                    wynik.setText("Twój wynik: 0");
                }
            }

        }
        @Override
        public void keyTyped(KeyEvent e){}
    }

    /**metoda aktualizująca wynik(wywołanie po każdym wciśnięciu klawisza)
     */
    public void wynikUpdate() {
        wynik.setText("Twój wynik: " + gra.getScore());
    }
}
