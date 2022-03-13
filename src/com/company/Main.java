package com.company;

import javafx.util.Pair;

import java.util.*;


public class Main {
    static int COLS = 9;
    static int ROWS = 9;
    static int min_steps = Integer.MAX_VALUE;
    static HashMap<Pair<Integer, Integer>, Pair<Pair<Integer, Integer>, Integer>> backtracking_path;
    static int numberOfChars = 6;
    static Pair<Integer, Integer> F;
    static Pair<Integer, Integer> N;
    static Pair<Integer, Integer> C;
    static Pair<Integer, Integer> B;
    static Pair<Integer, Integer> E;
    static int size_ans = Integer.MAX_VALUE;
    static ArrayList<Pair<Integer, Integer>> ans_way;

    public static void main(String[] args) {

        System.out.println("Which scenario would you like to see: 1 or 2");
        Scanner in = new Scanner(System.in);
        int scenario = in.nextInt();
        while (scenario != 1 && scenario != 2) {
            System.out.println("Incorrect input!");
            scenario = in.nextInt();
        }
        System.out.println("Choose input format: Random(1) or Manually(2)");
        int[][] grid = new int[ROWS][COLS];
        int inputFormat = in.nextInt();

        while (inputFormat != 1 && inputFormat != 2) {
            System.out.println("Incorrect input!");
            inputFormat = in.nextInt();
        }

        in.nextLine();
        HashSet<Pair<Integer, Integer>> inspectors_zone = new HashSet<>();
        HashMap<Integer, Character> attributes = new HashMap<>();
        attributes.put(0, '-');
        attributes.put(1, 'P');
        attributes.put(2, 'F');
        attributes.put(3, 'N');
        attributes.put(4, 'B');
        attributes.put(5, 'C');
        attributes.put(6, 'E');

        if (inputFormat == 1) {
            grid[0][0] = 1;
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
            show(grid, attributes);
            HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            first_method(grid, inspect_1,scenario);
            second_method(grid, attributes, inspect_2,scenario);

        }

        if (inputFormat == 2) {
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
            ArrayList<Pair<Integer, Integer>> coords = new ArrayList<>();
            for (int i = 0; i < real_input.size() - 1; i += 2) {
                int x = Integer.parseInt(real_input.get(i));
                int y = Integer.parseInt(real_input.get(i + 1));
                Pair<Integer, Integer> pair = new Pair<>(x, y);
                coords.add(pair);
            }
            while (coords.size() != 6){
                System.out.println("The amount of coordinates are not equal to 6! Type it again:)");
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
                } else if (i == 3){
                    while(!check_next_node_1(x,y,inspectors_zone)){
                        System.out.println("Book is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    B = new Pair<>(x, y);
                }
                else if (i == 4) {
                    while(!check_next_node_1(x,y,inspectors_zone)){
                        System.out.println("Cloak is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    C = new Pair<>(x, y);
                }
                else if (i == 5){
                    while(!check_next_node_1(x,y,inspectors_zone)){
                        System.out.println("Exit is located in incorrect place! Please change his position (x,y) (Separate your input by Enter)");
                        x = in1.nextInt();
                        y = in1.nextInt();
                    }
                    E = new Pair<>(x, y);
                }
                grid[x][y] = i + 1;

            }
            show(grid, attributes);
            HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            first_method(grid, inspect_1, scenario);
            second_method(grid, attributes, inspect_2, scenario);

        }
    }

    public static void second_method(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone,int scenario) {
        System.out.println("----------------------BFS------------------------");
        HashSet<Pair<Integer, Integer>> small_inspectors_zone = new HashSet<>();
        small_inspectors_zone.add(N);
        small_inspectors_zone.add(F);
        HashMap<String, ArrayList<Pair<Integer, Integer>>> ways = new HashMap<>();
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> dest;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_start = new ArrayList<>();
        Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> bfs_bs = bfs(grid, start, 4, inspectors_zone, scenario);
        dest = bfs_bs.getValue();
        way_to_book_from_start = recovering_path_bfs(start, dest, bfs_bs.getKey());


        start = new Pair<>(0, 0);
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_start = new ArrayList<>();
        bfs_bs = bfs(grid, start, 5, inspectors_zone,scenario);
        dest = bfs_bs.getValue();
        way_to_cloak_from_start = recovering_path_bfs(start, dest, bfs_bs.getKey());

        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_cloak_from_book = new ArrayList<>();
        bfs_bs = bfs(grid, start, 5, inspectors_zone,scenario);
        dest = bfs_bs.getValue();
        way_to_cloak_from_book = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BC", way_to_cloak_from_book);


        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 4, small_inspectors_zone,scenario);
        dest = bfs_bs.getValue();
        way_to_book_from_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("CB", way_to_book_from_cloak);

        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wout_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, inspectors_zone,scenario);
        dest = bfs_bs.getValue();
        way_to_door_wout_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BE", way_to_door_wout_cloak);


        start = B;
        ArrayList<Pair<Integer, Integer>> way_to_door_wth_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, small_inspectors_zone,scenario);
        dest = bfs_bs.getValue();
        way_to_door_wth_cloak = recovering_path_bfs(start, dest, bfs_bs.getKey());
        ways.put("BE1", way_to_door_wth_cloak);


        if (way_to_door_wth_cloak == null && way_to_book_from_cloak == null && way_to_cloak_from_start == null) {
            System.out.println("Door can't be reached. You've lost:(");
            return;
        }

        start = C;
        ArrayList<Pair<Integer, Integer>> way_to_door_from_cloak = new ArrayList<>();
        bfs_bs = bfs(grid, start, 6, small_inspectors_zone,scenario);
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
        if (ans_way.contains(C)){
            if(ans_way.contains(F) || ans_way.contains(N)){
                System.out.println("Harry Potter was caught. You've lost:(");
            }
            else{
                show_ans(ans_way);
            }
        }
        else{
            boolean is_caught = false;
            for (Pair<Integer, Integer> s : inspectors_zone) {
                if (!is_caught) {
                    if (ans_way.contains(s)) {
                        System.out.println("Harry Potter was caught. You've lost:(");
                        is_caught = true;
                    }
                }
            }
            if(!is_caught){
                show_ans(ans_way);
            }
        }


        ans_way = new ArrayList<>();
        size_ans = Integer.MAX_VALUE;
    }

    public static void first_method(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone,int scenario) {
        System.out.println("-----------------Backtracking--------------------");

        HashSet<Pair<Integer, Integer>> small_inspectors_zone = new HashSet<>();
        small_inspectors_zone.add(N);
        small_inspectors_zone.add(F);
        backtracking_path = new HashMap<>();
        HashMap<String, ArrayList<Pair<Integer, Integer>>> ways = new HashMap<>();
        min_steps = Integer.MAX_VALUE;
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        Pair<Integer, Integer> dest = B;
        ArrayList<Pair<Integer, Integer>> way_to_book_from_start = new ArrayList<>();
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
        way_to_door_wth_cloak = recovering_path_backtrack(start, dest);
        ways.put("BE1", way_to_door_wth_cloak);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;

        if (way_to_door_wth_cloak == null && way_to_book_from_cloak == null && way_to_cloak_from_start == null) {
            System.out.println("Door can't be reached. You've lost:(");
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
        if (ans_way.contains(C)){
            if(ans_way.contains(F) || ans_way.contains(N)){
                System.out.println("Harry Potter was caught. You've lost:(");
            }
            else{
                show_ans(ans_way);
            }
        }
        else{
            boolean is_caught = false;
            for (Pair<Integer, Integer> s : inspectors_zone) {
                if (!is_caught) {
                    if (ans_way.contains(s)) {
                        System.out.println("Harry Potter was caught. You've lost:(");
                        is_caught = true;
                    }
                }
            }
            if(!is_caught){
                show_ans(ans_way);
            }
        }


        ans_way = new ArrayList<>();
        size_ans = Integer.MAX_VALUE;
    }

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

    public static void show_ans(ArrayList<Pair<Integer, Integer>> ans){
        System.out.println("Way to door:");
        for(int i = ans.size() - 1; i > -1;--i){
            if (i > 0) {
                System.out.print("[");
                System.out.print(ans.get(i).getKey());
                System.out.print(", ");
                System.out.print(ans.get(i).getValue());
                System.out.print("]");
                System.out.print(" -> ");
            }
            else {
                System.out.print("[");
                System.out.print(ans.get(i).getKey());
                System.out.print(", ");
                System.out.print(ans.get(i).getValue());
                System.out.println("]");
            }
        }
        System.out.println("You are winner!!!");
    }

    public static Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> bfs(int[][] grid, Pair<Integer, Integer> start, int idOfitem, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) {
        Queue<Pair<Integer, Integer>> queue = new LinkedList();

        queue.add(start);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> visited = new HashMap<>();
        Pair<Integer, Integer> dest = null;
        visited.put(start, start);
        while (!queue.isEmpty()) {
            Pair<Integer, Integer> cur_node = queue.poll();
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

    public static void backtracking(int[][] grid, Pair<Integer, Integer> node, int idOfitem, int steps, HashSet<Pair<Integer, Integer>> inspectors_zone, int scenario) {

        if (steps < min_steps) {
            if (grid[node.getKey()][node.getValue()] == idOfitem) {
                min_steps = steps;
                return;
            }
            ArrayList<Pair<Integer, Integer>> next_nodes = get_next_nodes(node, inspectors_zone, scenario);
            for (Pair<Integer, Integer> next_node : next_nodes) {
                if (backtracking_path.containsKey(next_node) && backtracking_path.get(next_node).getValue() >= steps + 1) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone,scenario);
                } else if (!backtracking_path.containsKey(next_node)) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone,scenario);
                }

            }
        }

    }

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
            for (int[] way : ways) {
                Pair<Integer, Integer> next_node = new Pair<>(way[0] + cur.getKey(), way[1] + cur.getValue());
                if (check_next_node_2(next_node.getKey(), next_node.getValue()))
                    next_nodes.add(next_node);
            }
        }
        return next_nodes;
    }

    public static boolean check_next_node_1(int x, int y, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        Pair<Integer, Integer> next_pos = new Pair<>(x, y);
        return x > -1 && x < ROWS && y > -1 && y < COLS && !inspectors_zone.contains(next_pos);
    }

    public static boolean check_next_node_2(int x, int y) {
        Pair<Integer, Integer> next_pos = new Pair<>(x, y);
        return x > -1 && x < ROWS && y > -1 && y < COLS;
    }


}
