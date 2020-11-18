import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    static final String url = "jdbc:mysql://db:3306/";;
    static final String db_name = "database";
    static final String userPass = "?user=sd&password=123456";
    static final String userName = "sd";
    static final String password = "123456";

    /**
     * @throws java.sql.SQLException
     */

    public static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws SQLException, InterruptedException {

        System.out.println("Poczekaj 10 sekund, bazka się ładuje.");

        int selection;
        String nazwa_tabeli = "people";
        long milis = 2000;

        String new_table = "CREATE TABLE IF NOT EXISTS " + nazwa_tabeli + " ("
                + "ID INT(4) NOT NULL AUTO_INCREMENT,"
                + "NAME VARCHAR(30),"
                + "SURNAME VARCHAR(30),"
                + "PESEL VARCHAR(11),"
                + "PRIMARY KEY (ID))";

        Connection conn = null;

        try
        {
            Class.forName ("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url + db_name, userName, password);

            Statement st = conn.createStatement();
            st.executeUpdate(new_table);

            System.out.println ("\nDatabase Connection Established...");
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            System.err.println ("Cannot connect to database server");
        }

        while(true) {

            //Menu główne apki

            System.out.println("\n\n\nMENU GLOWNE");
            System.out.println("1: Wyswietl zawartosc tabeli.");
            System.out.println("2. Dodaj do tabeli.");
            System.out.println("3. Usun z tabeli.");
            System.out.println("0. Wyjscie");

            System.out.println("Wybierz opcje: ");
            selection = scan.nextInt();
            scan.nextLine();

            switch(selection) {
                case 1: { //WYświetlenie zawartości tabeli

                    Statement st = (Statement) conn.createStatement();
                    String query = "SELECT * FROM " + nazwa_tabeli;
                    ResultSet rs =  st.executeQuery(query);
                    System.out.println("\n\nID | NAME | SURNAME | PESEL");

                    while(rs.next()) {
                        int id = rs.getInt("id");
                        String name = rs.getString("Name");
                        String surname = rs.getString("Surname");
                        String pesel = rs.getString("pesel");

                        System.out.println(id + " | " + name + " | " + surname + " | " + pesel);
                    }
                    break;
                }
                case 2: { //Dodanie rekordów do tabeli (ograniczone maksimum)

                    System.out.println("\nIle rekordow chcesz dodac (od 1 do 10)");
                    int count = scan.nextInt();

                    if(count > 10 || count == 0){
                        System.out.println("\nNiepoprawna wartość. Wpisz liczbę z zakresu od 1 do 10: ");
                        count = scan.nextInt();
                    }
                    while(count > 0){

                        Thread.sleep(100);
                        System.out.println("Podaj imie: ");
                        String name = scan.next();

                        System.out.println("Podaj nazwisko: ");
                        String surname = scan.next();

                        System.out.println("Podaj pesel: ");
                        String pesel = scan.next();

                        String query = "INSERT INTO " + nazwa_tabeli + " ( NAME, SURNAME, PESEL) VALUES (?, ?, ?)";
                        PreparedStatement prpStmt = conn.prepareStatement(query);

                        prpStmt.setString(1, name);
                        prpStmt.setString(2, surname);
                        prpStmt.setString(3, pesel);

                        prpStmt.execute();

                        System.out.println("\nDodano rekord.\n");

                        count--;

                    }
                    break;
                }
                case 3: { //Usunięcie rekordu o zadanym ID
                    System.out.println("\nWybierz rekord do usuniecia: ");
                    int id = scan.nextInt();


                    String query = "DELETE FROM " + nazwa_tabeli + " WHERE ID = ?";
                    PreparedStatement prpStmt = conn.prepareStatement(query);

                    prpStmt.setInt(1, id);

                    prpStmt.execute();

                    System.out.println("Usunięto rekord.");
                    break;
                }
                case 0:
                default:
                    System.out.println("Do zobaczenia!");
                    Thread.sleep(milis);
                    System.exit(0);

            }
        }

    }

}