package com.company;

import javafx.util.Pair;

import java.util.*;


/**
 * The type Main.
 */
public class TimurAizatvafin {
    /**
     * Number of columns.
     */
    static int COLS = 9;
    /**
     * Number of columns.
     */
    static int ROWS = 9;
    /**
     * The attributes that will be replaced by them id on the grid.
     */
    static HashMap<Integer, Character> attributes;
    /**
     * The Min steps for backtracking to count how many steps Potter did to reach some cell.
     */
    static int min_steps = Integer.MAX_VALUE;
    /**
     * The Backtracking path.
     */
    static HashMap<Pair<Integer, Integer>, Pair<Pair<Integer, Integer>, Integer>> backtracking_path;
    /**
     * The F position of Filch are needed to recover all condition the map(Algorithms don't use this info).
     */
    static Pair<Integer, Integer> F;
    /**
     * The N position of Norris are needed to recover all condition the map(Algorithms don't use this info).
     */
    static Pair<Integer, Integer> N;
    /**
     * The C to recover some part of Potter path.
     */
    static Pair<Integer, Integer> C;
    /**
     * The B to recover some part of Potter path.
     */
    static Pair<Integer, Integer> B;
    /**
     * The E to recover some part of Potter path.
     */
    static Pair<Integer, Integer> E;
    /**
     * The Size ans.
     */
    static int size_ans = Integer.MAX_VALUE;
    /**
     * The minimum size of way that will be choosing.
     */
    static ArrayList<Pair<Integer, Integer>> ans_way ;
    /**
     * The Number of steps are needed to count how many steps algo need to accomplish his task.
     */
    static int number_of_steps = 0;
    /**
     * The Time.
     */
    static long time = 0;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws InterruptedException the interrupted exception
     */
    public static void main(String[] args) throws InterruptedException {

        int inputFormat;
        int scenario;
        int[][] grid = new int[ROWS][COLS];
        HashSet<Pair<Integer, Integer>> inspectors_zone = new HashSet<>();

        Pair<Integer, Integer> general_data = general_input();
        inputFormat = general_data.getKey();
        scenario = general_data.getValue();


        if (inputFormat == 1) {
            Pair<int[][], HashSet<Pair<Integer, Integer>>> conditions = random_input(grid, inspectors_zone);
            show(grid, attributes);
            if (inspectors_zone.contains(new Pair<>(0, 0))) {
                System.out.println("Potter was caught immediately");
                return;
            }
            HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            first_method(grid, inspect_1, scenario);
            second_method(grid, attributes, inspect_2, scenario);

        }

        if (inputFormat == 2) {
            Pair<int[][], HashSet<Pair<Integer, Integer>>> conditions = manual_input(grid, inspectors_zone);
            grid = conditions.getKey();
            inspectors_zone = conditions.getValue();
            show(grid, attributes);
            if (inspectors_zone.contains(new Pair<>(0, 0))) {
                System.out.println("Potter was caught immediately");
                return;
            }
            HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            first_method(grid, inspect_1, scenario);
            second_method(grid, attributes, inspect_2, scenario);

        }
    }

    /**
     * General input pair. There user choose which scenario and input format(Random or manual) would like to see.
     *
     * @return the pair
     */
    public static Pair<Integer, Integer> general_input() {
        System.out.println("Which scenario would you like to see: 1 or 2");
        Scanner in = new Scanner(System.in);
        int scenario = 0;
        int[][] grid = new int[ROWS][COLS];
        int inputFormat = 0;
        try {
            scenario = in.nextInt();
            while (scenario != 1 && scenario != 2) {
                System.out.println("Incorrect input!");
                scenario = in.nextInt();
            }
            System.out.println("Choose input format: Random(1) or Manually(2)");

            inputFormat = in.nextInt();

            while (inputFormat != 1 && inputFormat != 2) {
                System.out.println("Incorrect input!");
                inputFormat = in.nextInt();
            }
        }
        catch (Exception exception){
            System.out.println("Incorrect type of input! Try again");
            Pair<Integer, Integer> pair = general_input();
            inputFormat = pair.getKey();
            scenario = pair.getValue();
        }

        in.nextLine();
        attributes = new HashMap<>();
        attributes.put(0, '-');
        attributes.put(1, 'P');
        attributes.put(2, 'F');
        attributes.put(3, 'N');
        attributes.put(4, 'B');
        attributes.put(5, 'C');
        attributes.put(6, 'E');
        attributes.put(7, '*');
        return new Pair<>(inputFormat, scenario);
    }

    /**
     * Manual input pair.
     *
     * @param grid            the grid
     * @param inspectors_zone the inspectors zone
     * @return the pair
     */
    public static Pair<int[][], HashSet<Pair<Integer, Integer>>> manual_input(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        grid[0][0] = 1;
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                if (i == 0 && j == 0) continue;
                grid[i][j] = 0;

            }
        }

        String[] input;
        Scanner in1 = new Scanner(System.in);
        input = in1.nextLine().split("[\\Q[] ,\\E]");

        ArrayList<String> real_input = new ArrayList<>();
        for (String s : input) {
            if (!s.equals("")) {
                real_input.add(s);
            }
        }
        try {
            ArrayList<Pair<Integer, Integer>> coords = new ArrayList<>();
            for (int i = 0; i < real_input.size() - 1; i += 2) {
                int x = Integer.parseInt(real_input.get(i));
                int y = Integer.parseInt(real_input.get(i + 1));
                Pair<Integer, Integer> pair = new Pair<>(x, y);
                coords.add(pair);
            }
            while (coords.size() != 6) {
                System.out.println("The amount of coordinates are not equal to 6 or type incorrect! Type it again:)");
                input = in1.nextLine().split("[\\Q[] ,\\E]");
                real_input = new ArrayList<>();
                for (String s : input) {
                    if (!s.equals("")) {
                        real_input.add(s);
                    }
                }
                coords = new ArrayList<>();
                for (int i = 0; i < real_input.size() - 1; i += 2) {
                    int x = Integer.parseInt(real_input.get(i));
                    int y = Integer.parseInt(real_input.get(i + 1));
                    Pair<Integer, Integer> pair = new Pair<>(x, y);
                    coords.add(pair);
                }
            }


            for (int i = 1; i < 6; ++i) {
                int x = coords.get(i).getKey();
                int y = coords.get(i).getValue();
                if (i == 1) {
                    F = new Pair<>(x, y);
                    for (int j = x - 2; j < x + 3; ++j) {
                        for (int k = y - 2; k < y + 3; ++k) {
                            if (j > -1 && j < ROWS && k > -1 && k < COLS) {
                                Pair<Integer, Integer> pair = new Pair<>(j, k);
                                inspectors_zone.add(pair);
                            }
                        }
                    }
                } else if (i == 2) {
                    N = new Pair<>(x, y);
                    for (int j = x - 1; j < x + 2; ++j) {
                        for (int k = y - 1; k < y + 2; ++k) {
                            if (j > -1 && j < ROWS && k > -1 && k < COLS) {
                                Pair<Integer, Integer> pair = new Pair<>(j, k);
                                inspectors_zone.add(pair);
                            }
                        }
                    }
                } else if (i == 3) {
                    while (!check_next_node_1(x, y, inspectors_zone)) {
                        System.out.println("Book is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    B = new Pair<>(x, y);
                } else if (i == 4) {
                    while (!check_next_node_1(x, y, inspectors_zone)) {
                        System.out.println("Cloak is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    C = new Pair<>(x, y);
                } else if (i == 5) {
                    while (!check_next_node_1(x, y, inspectors_zone)) {
                        System.out.println("Exit is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    E = new Pair<>(x, y);
                }
                grid[x][y] = i + 1;

            }
        }
        catch (Exception e){
            System.out.println("Incorrect type of input! Please try again");
            Pair<int[][],  HashSet<Pair<Integer, Integer>>> pair = manual_input(grid,inspectors_zone);
            grid = pair.getKey();
            inspectors_zone = pair.getValue();
        }
        return new Pair<>(grid, inspectors_zone);

    }

    /**
     * Random input pair.
     *
     * @param grid            the grid
     * @param inspectors_zone the inspectors zone
     * @return the pair
     */
    public static Pair<int[][], HashSet<Pair<Integer, Integer>>> random_input(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        grid[0][0] = 1;
        ArrayList<Pair<Integer, Integer>> coords = new ArrayList<>();
        coords.add(new Pair<>(0, 0));
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                if (i == 0 && j == 0) continue;
                grid[i][j] = 0;

            }
        }
        for (int i = 1; i < 6; ++i) {
            int x = (int) (Math.random() * 9);
            int y = (int) (Math.random() * 9);
            while (grid[x][y] != 0) {
                x = (int) (Math.random() * 9);
                y = (int) (Math.random() * 9);
            }

            if (i == 1) {
                F = new Pair<>(x, y);
                for (int j = x - 2; j < x + 3; ++j) {
                    for (int k = y - 2; k < y + 3; ++k) {
                        if (j > -1 && j < ROWS && k > -1 && k < COLS) {
                            Pair<Integer, Integer> pair = new Pair<>(j, k);
                            inspectors_zone.add(pair);
                        }
                    }
                }

            } else if (i == 2) {
                N = new Pair<>(x, y);
                for (int j = x - 1; j < x + 2; ++j) {
                    for (int k = y - 1; k < y + 2; ++k) {
                        if (j > -1 && j < ROWS && k > -1 && k < COLS) {
                            Pair<Integer, Integer> pair = new Pair<>(j, k);
                            inspectors_zone.add(pair);
                        }
                    }
                }
            } else {
                Pair<Integer, Integer> pair = new Pair<>(x, y);
                while (grid[x][y] != 0 || inspectors_zone.contains(pair)) {
                    x = (int) (Math.random() * 9);
                    y = (int) (Math.random() * 9);
                    pair = new Pair<>(x, y);
                }

            }
            if (i == 3) B = new Pair<>(x, y);
            if (i == 4) C = new Pair<>(x, y);
            if (i == 5) E = new Pair<>(x, y);
            grid[x][y] = i + 1;


        }
        coords.add(F);
        coords.add(N);
        coords.add(B);
        coords.add(C);
        coords.add(E);
        return new Pair<>(grid, inspectors_zone);
    }

    /**
     * Second method.There was implemented whole logic of finding correct path by bfs.
     *
     * @param grid            the grid
     * @param attributes      the attributes
     * @param inspectors_zone the inspectors zone
     * @param scenario        the scenario
     * @throws InterruptedException the interrupted exception
     */
    public static void second_method(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) throws InterruptedException {
        HashSet<Pair<Integer, Integer>> small_inspectors_zone = new HashSet<>();
        small_inspectors_zone.add(N);
        small_inspectors_zone.add(F);
        HashMap<String, ArrayList<Pair<Integer, Integer>>> ways = new HashMap<>();
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> dest;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_start = new ArrayList<>();
        long start_time = System.nanoTime();

        Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> bfs_bs = bfs(grid, start, 4, inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_book_from_start = recovering_path_bfs(start, dest, bfs_bs.getKey());


        start = new Pair<>(0, 0);
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_start = new ArrayList<>();
        bfs_bs = bfs(grid, start, 5, inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_cloak_from_start = recovering_path_bfs(start, dest, bfs_bs.getKey());

        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_book = new ArrayList<>();
        bfs_bs = bfs(grid, start, 5, inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_cloak_from_book = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BC", way_to_cloak_from_book);


        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 4, small_inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_book_from_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("CB", way_to_book_from_cloak);

        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wout_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_door_wout_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BE", way_to_door_wout_cloak);


        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wth_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, small_inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_door_wth_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BE1", way_to_door_wth_cloak);


        if ((way_to_cloak_from_start == null && way_to_book_from_start == null) || (way_to_door_wth_cloak == null)) {
            System.out.println("Door or Book can't be reached. You've lost:(");
            return;
        }

        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_door_from_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, small_inspectors_zone, scenario);
        Thread.sleep(0);
        long finish = System.nanoTime();
        long elapsed = finish - start_time;
        time = elapsed;
        dest = bfs_bs.getValue();
        way_to_door_from_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("CE", way_to_door_from_cloak);

        ways.values().removeIf(Objects::isNull);

        HashSet<String> path = new HashSet<>();
        if (way_to_book_from_start != null) {
            choosing_the_best(ways, way_to_book_from_start, "SB");
        }
        if (way_to_cloak_from_start != null) {
            choosing_the_best(ways, way_to_cloak_from_start, "SC");
        }
        if(ans_way == null){
            System.out.println("This algorithm cannot provide a way to door");
        }
        else if (ans_way.contains(C)) {
            if (ans_way.contains(F) || ans_way.contains(N)) {
                System.out.println("Harry Potter was caught. You've lost:(");
            } else {
                System.out.println("----------------------BFS------------------------");
                show_ans(grid, ans_way);
            }
        } else {
            boolean is_caught = false;
            for (Pair<Integer, Integer> s : inspectors_zone) {
                if (!is_caught) {
                    if (ans_way.contains(s)) {
                        System.out.println("Harry Potter was caught. You've lost:(");
                        is_caught = true;
                    }
                }
            }
            if (!is_caught) {
                System.out.println("----------------------BFS------------------------");
                show_ans(grid, ans_way);
            }
        }


        ans_way = new ArrayList<>();
        size_ans = Integer.MAX_VALUE;
    }

    /**
     * First method.There was implemented whole logic of finding correct path by backtracking.
     *
     * @param grid            the grid
     * @param inspectors_zone the inspectors zone
     * @param scenario        the scenario
     * @throws InterruptedException the interrupted exception
     */
    public static void first_method(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) throws InterruptedException {


        HashSet<Pair<Integer, Integer>> small_inspectors_zone = new HashSet<>();
        small_inspectors_zone.add(N);
        small_inspectors_zone.add(F);
        backtracking_path = new HashMap<>();
        HashMap<String, ArrayList<Pair<Integer, Integer>>> ways = new HashMap<>();
        min_steps = Integer.MAX_VALUE;
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> dest = B;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_start = new ArrayList<>();
        long start_time = System.nanoTime();
        backtracking(grid, start, 4, 0, inspectors_zone, scenario);
        way_to_book_from_start = recovering_path_backtrack(start, dest);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;

        dest = C;
        start = new Pair<>(0, 0);
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_start = new ArrayList<>();
        backtracking(grid, start, 5, 0, inspectors_zone, scenario);
        way_to_cloak_from_start = recovering_path_backtrack(start, dest);
        //ways.put("SC", way_to_cloak_from_start);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;

        dest = C;
        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_book = new ArrayList<>();
        backtracking(grid, start, 5, 0, inspectors_zone, scenario);
        way_to_cloak_from_book = recovering_path_backtrack(start, dest);
        ways.put("BC", way_to_cloak_from_book);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;

        dest = B;
        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_cloak = new ArrayList<>();
        backtracking(grid, start, 4, 0, small_inspectors_zone, scenario);
        way_to_book_from_cloak = recovering_path_backtrack(start, dest);
        ways.put("CB", way_to_book_from_cloak);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        dest = E;
        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wout_cloak = new ArrayList<>();
        backtracking(grid, start, 6, 0, inspectors_zone, scenario);
        way_to_door_wout_cloak = recovering_path_backtrack(start, dest);
        ways.put("BE", way_to_door_wout_cloak);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        dest = E;
        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wth_cloak = new ArrayList<>();
        backtracking(grid, start, 6, 0, small_inspectors_zone, scenario);
        Thread.sleep(0);
        long finish = System.nanoTime();
        long elapsed = finish - start_time;
        time = elapsed;
        way_to_door_wth_cloak = recovering_path_backtrack(start, dest);
        ways.put("BE1", way_to_door_wth_cloak);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;

        if ((way_to_cloak_from_start == null && way_to_book_from_start == null) || (way_to_door_wth_cloak == null)) {
            System.out.println("Door or Book can't be reached. You've lost:(");
            return;
        }
        dest = E;
        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_door_from_cloak = new ArrayList<>();
        backtracking(grid, start, 6, 0, small_inspectors_zone, scenario);
        way_to_door_from_cloak = recovering_path_backtrack(start, dest);
        ways.put("CE", way_to_door_from_cloak);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        ways.values().removeIf(Objects::isNull);


        if (way_to_book_from_start != null) {
            choosing_the_best(ways, way_to_book_from_start, "SB");
        }
        if (way_to_cloak_from_start != null) {
            choosing_the_best(ways, way_to_cloak_from_start, "SC");
        }
        if(ans_way == null){
            System.out.println("This algorithm cannot provide a way to door");
        }
        else if (ans_way.contains(C)) {
            if (ans_way.contains(F) || ans_way.contains(N)) {
                System.out.println("Harry Potter was caught. You've lost:(");
            } else {
                System.out.println("-----------------Backtracking--------------------");
                show_ans(grid, ans_way);
            }
        } else {
            boolean is_caught = false;
            for (Pair<Integer, Integer> s : inspectors_zone) {
                if (!is_caught) {
                    if (ans_way.contains(s)) {
                        System.out.println("Harry Potter was caught. You've lost:(");
                        is_caught = true;
                    }
                }
            }
            if (!is_caught) {
                System.out.println("-----------------Backtracking--------------------");
                show_ans(grid, ans_way);
            }
        }


        ans_way = null;
        size_ans = Integer.MAX_VALUE;
    }

    /**
     * Choosing the best. Basically, we have more than one way to reach the door(for example, Cloak - Book - Door or Book - Door  ) and we need to choose the best one.
     *
     * @param ways        the ways
     * @param ans         the ans
     * @param current_pos the current pos
     */
    public static void choosing_the_best(HashMap<String, ArrayList<Pair<Integer, Integer>>> ways, ArrayList<Pair<Integer, Integer>> ans, String current_pos) {
        if (current_pos.length() == 2 && current_pos.substring(current_pos.length() - 1).equals("E") && ans.size() < size_ans) {
            ans_way = ans;
            size_ans = ans.size();
        }
        if (current_pos.length() == 3 && ans.size() < size_ans) {
            ans_way = ans;
            size_ans = ans.size();
        } else {
            for (String key : ways.keySet()) {
                if (key.length() == 2 && current_pos.length() == 2) {
                    String reverse_key = new StringBuilder(key).reverse().toString();
                    if (!current_pos.equals("SC") || !key.equals("CE")) {
                        if (!current_pos.equals(reverse_key) && key.substring(0, 1).equals(current_pos.substring(current_pos.length() - 1))) {
                            ArrayList<Pair<Integer, Integer>> new_ans = (ArrayList<Pair<Integer, Integer>>) ways.get(key).clone();
                            ArrayList<Pair<Integer, Integer>> temp_ans = (ArrayList<Pair<Integer, Integer>>) ans.clone();
                            temp_ans.remove(0);
                            new_ans.addAll(temp_ans);
                            choosing_the_best(ways, new_ans, key);
                        }
                    }
                } else if (key.length() == 3 && current_pos.charAt(current_pos.length() - 1) == 'B') {
                    if (!current_pos.equals("SB") || !key.equals("BE1")) {
                        ArrayList<Pair<Integer, Integer>> new_ans = (ArrayList<Pair<Integer, Integer>>) ways.get(key).clone();
                        ArrayList<Pair<Integer, Integer>> temp_ans = (ArrayList<Pair<Integer, Integer>>) ans.clone();
                        temp_ans.remove(0);
                        new_ans.addAll(temp_ans);
                        choosing_the_best(ways, new_ans, key);
                    }
                }

            }
        }
    }

    /**
     * Recovering path backtrack array list.
     *
     * @param start the start
     * @param dest  the dest
     * @return the array list
     */
    public static ArrayList<Pair<Integer, Integer>> recovering_path_backtrack(Pair<Integer, Integer> start, Pair<Integer, Integer> dest) {
        ArrayList<Pair<Integer, Integer>> answer = new ArrayList<>();
        if (backtracking_path.containsKey(dest)) {
            Pair<Integer, Integer> cur_node = dest;
            while (!cur_node.equals(start)) {
                answer.add(cur_node);
                cur_node = backtracking_path.get(cur_node).getKey();
            }
            answer.add(cur_node);
        } else {
            return null;
        }
        return answer;

    }

    /**
     * Recovering path bfs array list.
     *
     * @param start the start
     * @param dest  the dest
     * @param path  the path
     * @return the array list
     */
    public static ArrayList<Pair<Integer, Integer>> recovering_path_bfs(Pair<Integer, Integer> start, Pair<Integer, Integer> dest, HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path) {
        ArrayList<Pair<Integer, Integer>> ans = new ArrayList<>();
        if (dest != null) {
            Pair<Integer, Integer> cur_node = dest;
            while (!cur_node.equals(start)) {
                ans.add(cur_node);
                cur_node = path.get(cur_node);
            }
            ans.add(cur_node);
        } else return null;
        return ans;
    }

    /**
     * Showing the map of Actors with objects.
     *
     * @param grid       the grid
     * @param attributes the attributes
     */
    public static void show(int[][] grid, HashMap<Integer, Character> attributes) {

        for (int i = 0; i < ROWS; ++i) {
            if (i == 0) {
                System.out.print(' ');
                System.out.print(' ');
                for (int j = 0; j < COLS; ++j) {
                    System.out.print(j);
                    System.out.print(' ');
                }
                System.out.println();
            }
            System.out.print(i);
            System.out.print(' ');
            for (int j = 0; j < COLS; ++j) {

                System.out.print(attributes.get(grid[i][j]));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    /**
     * Showing the correct path from start to door.
     *
     * @param grid the grid
     * @param ans  the ans
     */
    public static void show_ans(int[][] grid, ArrayList<Pair<Integer, Integer>> ans) {
        int[][] updated_grid = new int[ROWS][COLS];
        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; j++) {
                updated_grid[i][j] = grid[i][j];
            }
        }
        for (Pair<Integer, Integer> an : ans) {
            int x = an.getKey();
            int y = an.getValue();
            if (updated_grid[x][y] == 0) {
                updated_grid[x][y] = 7;
            }
        }
        show(updated_grid, attributes);
        System.out.print("Number of steps: ");
        System.out.println(number_of_steps);
        System.out.print("Time: ");
        System.out.print((float) time / 1000000000);
        System.out.println(" sec.");
        number_of_steps = 0;
        System.out.println("Way to door:");
        for (int i = ans.size() - 1; i > -1; --i) {
            if (i > 0) {
                System.out.print("[");
                System.out.print(ans.get(i).getKey());
                System.out.print(", ");
                System.out.print(ans.get(i).getValue());
                System.out.print("]");
                System.out.print(" -> ");
            } else {
                System.out.print("[");
                System.out.print(ans.get(i).getKey());
                System.out.print(", ");
                System.out.print(ans.get(i).getValue());
                System.out.println("]");
            }
        }
        System.out.println("You are winner!!!");
    }

    /**
     * Bfs algorithm.
     *
     * @param grid            the grid
     * @param start           the start
     * @param idOfitem        the id ofitem
     * @param inspectors_zone the inspectors zone
     * @param scenario        the scenario
     * @return the pair of path and destination point
     */
    public static Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> bfs(int[][] grid, Pair<Integer, Integer> start, int idOfitem, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList();

        queue.add(start);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> visited = new HashMap<>();
        Pair<Integer, Integer> dest = null;
        visited.put(start, start);
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> cur_node = queue.poll();
            number_of_steps++;
            if (grid[cur_node.getKey()][cur_node.getValue()] == idOfitem) {
                dest = new Pair<>(cur_node.getKey(), cur_node.getValue());
                break;
            }

            ArrayList<Pair<Integer, Integer>> next_nodes;
            next_nodes = get_next_nodes(cur_node, inspectors_zone, scenario);
            for (Pair<Integer, Integer> next_node : next_nodes) {
                if (!visited.containsKey(next_node)) {
                    queue.add(next_node);
                    visited.put(next_node, cur_node);
                }

            }
        }
        return new Pair<>(visited, dest);
    }

    /**
     * Backtracking algorithm. It changes the global variables like backtracking_path
     *
     * @param grid            the grid
     * @param node            the node
     * @param idOfitem        the id ofi tem
     * @param steps           the steps
     * @param inspectors_zone the inspectors zone
     * @param scenario        the scenario
     */
    public static void backtracking(int[][] grid, Pair<Integer, Integer> node, int idOfitem, int steps, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) {

        if (steps < min_steps) {
            if (grid[node.getKey()][node.getValue()] == idOfitem) {
                min_steps = steps;
                return;
            }

            ArrayList<Pair<Integer, Integer>> next_nodes = get_next_nodes(node, inspectors_zone, scenario);
            number_of_steps++;
            for (Pair<Integer, Integer> next_node : next_nodes) {
                if (backtracking_path.containsKey(next_node) && backtracking_path.get(next_node).getValue() >= steps + 1) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone, scenario);
                } else if (!backtracking_path.containsKey(next_node)) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone, scenario);
                }

            }
        }

    }

    /**
     * Gets next nodes. Returns all available adjacent cells where Potter con move on.
     *
     * @param cur             the cur
     * @param inspectors_zone the inspectors zone
     * @param scenario        the scenario
     * @return the next nodes
     */
    public static ArrayList<Pair<Integer, Integer>> get_next_nodes(Pair<Integer, Integer> cur, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) {
        ArrayList<Pair<Integer, Integer>> next_nodes = new ArrayList<>();
        int[][] ways = {{-1, 0},
                {0, -1},
                {1, 0},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1},
        };
        if (scenario == 1) {
            for (int[] way : ways) {
                Pair<Integer, Integer> next_node = new Pair<>(way[0] + cur.getKey(), way[1] + cur.getValue());
                if (check_next_node_1(next_node.getKey(), next_node.getValue(), inspectors_zone))
                    next_nodes.add(next_node);
            }
        } else {
            int[][] ways_2 = {{-1, -1},
                    {-1, 0},
                    {-1, 1},
                    {0, 1},
                    {1, 1},
                    {1, 0},
                    {1, -1},
                    {0, -1},
            };
            int side = 0;
            for (int[] way : ways_2) {
                Pair<Integer, Integer> next_node = new Pair<>(way[0] + cur.getKey(), way[1] + cur.getValue());
                if (check_next_node_2(next_node.getKey(), next_node.getValue(), side, inspectors_zone))
                    next_nodes.add(next_node);
                side++;
            }
        }
        return next_nodes;
    }

    /**
     * Check next node 1 boolean. Returns adjacent cells taking into account first scenario.
     *
     * @param x               the x
     * @param y               the y
     * @param inspectors_zone the inspectors zone
     * @return the boolean
     */
    public static boolean check_next_node_1(int x, int y, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        Pair<Integer, Integer> next_pos = new Pair<>(x, y);
        return x > -1 && x < ROWS && y > -1 && y < COLS && !inspectors_zone.contains(next_pos);
    }

    /**
     * Check next node 2 boolean.  Returns adjacent cells taking into account second scenario.
     *
     * @param x the x
     * @param y the y
     * @return the boolean
     */
    public static boolean check_next_node_2(int x, int y, int side, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        if( x > -1 && x < ROWS && y > -1 && y < COLS){
            Pair<Integer,Integer> pos_1;
            Pair<Integer,Integer> pos_2;
            Pair<Integer,Integer> pos_3;
            if (side == 0){
                pos_1 = new Pair<>(x, y - 1);
                pos_2 = new Pair<>(x - 1, y);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2);
            }
            if (side == 1){
                pos_1 = new Pair<>(x - 1, y - 1);
                pos_2 = new Pair<>(x - 1, y);
                pos_3 = new Pair<>(x - 1, y + 1);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2) && !inspectors_zone.contains(pos_3);
            }
            if (side == 2){
                pos_1 = new Pair<>(x - 1, y);
                pos_2 = new Pair<>(x, y + 1);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2);
            }
            if (side == 3){
                pos_1 = new Pair<>(x - 1, y + 1);
                pos_2 = new Pair<>(x, y + 1);
                pos_3 = new Pair<>(x + 1, y + 1);

                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2) && !inspectors_zone.contains(pos_3);
            }
            if (side == 4){
                pos_1 = new Pair<>(x, y + 1);
                pos_2 = new Pair<>(x + 1, y);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2);
            }
            if (side == 5){
                pos_1 = new Pair<>(x + 1, y + 1);
                pos_2 = new Pair<>(x + 1, y);
                pos_3 = new Pair<>(x + 1, y - 1);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2) && !inspectors_zone.contains(pos_3);
            }
            if (side == 6){
                pos_1 = new Pair<>(x + 1, y);
                pos_2 = new Pair<>(x, y - 1);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2);
            }
            if (side == 7){
                pos_1 = new Pair<>(x + 1, y - 1);
                pos_2 = new Pair<>(x, y - 1);
                pos_3 = new Pair<>(x - 1, y - 1);
                return !inspectors_zone.contains(pos_1) && !inspectors_zone.contains(pos_2) && !inspectors_zone.contains(pos_3);
            }

        }
        return false;
    }


}
