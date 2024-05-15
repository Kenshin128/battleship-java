import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

public class Main {
    //=======================================================[Game Setup]=======================================================//
    public static String[][] generate_board_p(String[] letters, String[] numbers) {
        String[][] board = new String[10][10];
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                board[i][j] = letters[i] + numbers[j];
            }
        }
        return board;
    }

    public static String[][] generate_board_c(String[] ship_array, Hashtable<String, Integer> ship_table, String[] letters, String[] numbers) {
        String[][] board = new String[10][10];
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                board[i][j] = letters[i] + numbers[j];
            }
        }
        for (int i = 0; i < ship_array.length; i++) {
            for (int j = 0; j < ship_table.get(ship_array[i]); j++) {
                if ((j + 10) % 10 < ship_array[i].length()) {
                    board[i][j] = "[]";
                }
            }
        }
        return board;
    }

    public static void print_board(String[][] arr) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    //=======================================================[Game Logic]=======================================================//
    public static void place_ships(HashMap<String, List<String>> ship_locations, Hashtable<String, Integer> ship_table, String[][] board, String[] ship_array) {
        Scanner scan = new Scanner(System.in);
        for (String s : ship_array) {
            List<String> ship_coordinates = new ArrayList<>();
            System.out.println("Where would you like to place your " + s + "?\n(Enter " + ship_table.get(s) + " adjacent coordinates)");
            for (int j = 0; j < ship_table.get(s); j++) {
                String coordinate = scan.nextLine().toUpperCase();
                char letter = coordinate.charAt(0);
                int number = Integer.parseInt(coordinate.substring(1));
                int row_index = letter - 'A';
                int column_index = number - 1;
                if (j > 0) {
                    String prevCoordinate = ship_coordinates.get(j - 1);
                    char prevLetter = prevCoordinate.charAt(0);
                    int prevNumber = Integer.parseInt(prevCoordinate.substring(1));
                    int prevRow = prevLetter - 'A';
                    int prevCol = prevNumber - 1;
                    if (Math.abs(row_index - prevRow) > 1 || Math.abs(column_index - prevCol) > 1) {
                        System.out.print("Please enter an adjacent coordinate!");
                        j--;
                        continue;
                    }
                }
                board[row_index][column_index] = "[]";
                ship_coordinates.add(coordinate);
            }
            ship_locations.put(s, ship_coordinates);
        }
        scan.close();
        print_board(board);
    }


    //=======================================================[Main Function]=======================================================//
    public static void main(String[] args) {
        Hashtable<String, Integer> ship_table = new Hashtable<>();
        ship_table.put("Carrier", 5);
        ship_table.put("Battleship", 4);
        ship_table.put("Destroyer", 3);
        ship_table.put("Submarine", 3);
        ship_table.put("Patrol Boat", 2);
        HashMap<String, List<String>> ship_locations_p = new HashMap<>();
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",};
        String[] ship_array = {"Carrier", "Battleship", "Destroyer", "Submarine", "Patrol Boat"};
        String[][] player_board = generate_board_p(letters, numbers);
        String[][] cpu_board = generate_board_c(ship_array, ship_table, letters, numbers);
        print_board(cpu_board);
        place_ships(ship_locations_p, ship_table, player_board, ship_array);
    }
}