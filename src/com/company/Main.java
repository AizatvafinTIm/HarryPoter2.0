package com.company;

import javafx.util.Pair;

import java.util.*;


public class Main {
    static int COLS = 9;
    static int ROWS = 9;
    static int min_steps = Integer.MAX_VALUE;
    static boolean cloak_back = false;
    static HashMap<Pair<Integer, Integer>, Pair<Pair<Integer, Integer>, Integer>> backtracking_path;
    static int numberOfChars = 6;
    static Pair<Integer, Integer> F;
    static Pair<Integer, Integer> N;
    static Pair<Integer, Integer> C;
    static Pair<Integer, Integer> B;
    static Pair<Integer, Integer> E;

    public static void main(String[] args) {

        System.out.println("Choose input format: Random(1) or Manually(2)");
        Scanner in = new Scanner(System.in);
        int[][] grid = new int[ROWS][COLS];
        int inputFormat = in.nextInt();

        while (inputFormat != 1 && inputFormat != 2) {
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

                if (i == 2) {
                    F = new Pair<>(x, y);
                    for (int j = x - 2; j < x + 3; ++j) {
                        for (int k = y - 2; k < y + 3; ++k) {
                            if (j > -1 && j < ROWS && k > -1 && k < COLS) {
                                Pair<Integer, Integer> pair = new Pair<>(j, k);
                                inspectors_zone.add(pair);
                            }
                        }
                    }
                } else if (i == 3) {
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
                grid[x][y] = i;

            }
            show(grid, attributes);
            ArrayList<Pair<Integer, Integer>> normal_path_3 = first_approach(grid, attributes, inspectors_zone);
            ArrayList<Pair<Integer, Integer>> normal_path_2 = second_approach(grid, attributes, inspectors_zone);
            ArrayList<Pair<Integer, Integer>> normal_path = new ArrayList<>();
            boolean cloak = false;
            if (normal_path_3 == null && normal_path_2 == null) {
                System.out.println("Impossible reach the book!");
                return;
            }
            if (normal_path_3 != null && normal_path_2 != null) {
                if (normal_path_3.size() < normal_path_2.size()) {
                    normal_path = normal_path_3;
                } else {
                    cloak = true;
                    normal_path = normal_path_2;
                }
            }
            if (normal_path_3 != null && normal_path_2 == null) {
                normal_path = normal_path_3;
            }
            if (normal_path_2 != null && normal_path_3 == null) {
                cloak = true;
                normal_path = normal_path_2;
            }

            System.out.println("Way to book");
            System.out.println(normal_path);
            Pair<Integer, Integer> dest = normal_path.get(0);
            System.out.println("Way to door");
            ArrayList<Pair<Integer, Integer>> way_to_door = optimal_way_to_door(grid, attributes, inspectors_zone, dest, cloak);
            System.out.println(way_to_door);
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
                } else if (i == 3) B = new Pair<>(x, y);
                else if (i == 4) C = new Pair<>(x, y);
                else if (i == 5) E = new Pair<>(x, y);
                grid[x][y] = i + 1;

            }
            show(grid, attributes);

            HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
            first_method(grid, inspect_1);
            second_method(grid,attributes,inspect_2);

        }
    }

    public static void second_method(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        System.out.println("----------------------BFS------------------------");
        HashSet<Pair<Integer, Integer>> inspect_1 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
        HashSet<Pair<Integer, Integer>> inspect_2 = (HashSet<Pair<Integer, Integer>>) inspectors_zone.clone();
        ArrayList<Pair<Integer, Integer>> normal_path_3 = first_approach(grid, attributes, inspect_1);

        ArrayList<Pair<Integer, Integer>> normal_path_2 = second_approach(grid, attributes, inspect_2);

        ArrayList<Pair<Integer, Integer>> normal_path = new ArrayList<>();
        boolean cloak = false;
        if (normal_path_2 != null && normal_path_3 != null){
            if (normal_path_2.size() < normal_path_3.size()){
                normal_path = normal_path_2;
                cloak = (inspect_2.size() == 2);
                inspectors_zone = inspect_2;
            }
            else {
                normal_path = normal_path_3;
                cloak = (inspect_1.size() == 2);
                inspectors_zone = inspect_1;
            }
        }
        if (normal_path_2 == null && normal_path_3 != null){
            normal_path = normal_path_3;
            cloak = (inspect_1.size() == 2);
            inspectors_zone = inspect_1;
        }
        if (normal_path_2 != null && normal_path_3 == null){
            normal_path = normal_path_2;
            cloak = (inspect_2.size() == 2);
            inspectors_zone = inspect_2;
        }
        else if (normal_path.size() == 0){
            System.out.println("Impossible reach book");
            return;
        }
        System.out.println("Way to book");
        System.out.println(normal_path);
        Pair<Integer, Integer> dest = normal_path.get(0);
        System.out.println("Way to door");
        ArrayList<Pair<Integer, Integer>> way_to_door = optimal_way_to_door(grid, attributes, inspectors_zone, dest, cloak);
        System.out.println(way_to_door);


    }

    public static void first_method(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        System.out.println("-----------------Backtracking--------------------");
        backtracking_path = new HashMap<>();
        ArrayList<Pair<Integer, Integer>> backtrack_book = backtrack_to_book(grid, inspectors_zone);
        if (backtrack_book == null) {
            System.out.println("Impossible reach the book!");
            return;
        }
        System.out.println("Way to book");
        System.out.println(backtrack_book);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        ArrayList<Pair<Integer, Integer>> backtrack_door = backtrack_to_door(grid, inspectors_zone);
        if (backtrack_door == null) {
            System.out.println("Impossible reach the door!");
            return;
        }
        System.out.println("Way to door");
        System.out.println(backtrack_door);


    }

    public static ArrayList<Pair<Integer, Integer>> backtrack_to_door(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone) {

        // Trying to find way to door straight forward
        Pair<Integer, Integer> start = B;
        backtracking(grid, start, 6, 0, inspectors_zone);
        Pair<Integer, Integer> dest_dfs = E;
        ArrayList<Pair<Integer, Integer>> dfs_path_to_door = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> dfs_path_to_door_1 = new ArrayList<>();
        Pair<Integer, Integer> cur_node = dest_dfs;
        boolean straight = true;
        if (backtracking_path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                dfs_path_to_door.add(cur_node);
                cur_node = backtracking_path.get(cur_node).getKey();
            }
            dfs_path_to_door.add(cur_node);
        } else {
            straight = false;
        }
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        if (!cloak_back) {
            start = B;
            backtracking(grid, start, 5, 0, inspectors_zone);
            dest_dfs = C;
            ArrayList<Pair<Integer, Integer>> dfs_path_to_cloak = new ArrayList<>();
            cur_node = dest_dfs;
            if (backtracking_path.containsKey(cur_node)) {

                while (!cur_node.equals(start)) {
                    dfs_path_to_cloak.add(cur_node);
                    cur_node = backtracking_path.get(cur_node).getKey();
                }
                backtracking_path.clear();
                min_steps = Integer.MAX_VALUE;
                dfs_path_to_cloak.add(cur_node);
                start = C;
                backtracking(grid, start, 6, 0, inspectors_zone);
                dest_dfs = E;
                dfs_path_to_door_1 = new ArrayList<>();
                cur_node = dest_dfs;
                while (!cur_node.equals(start)) {
                    dfs_path_to_door_1.add(cur_node);
                    cur_node = backtracking_path.get(cur_node).getKey();
                }
                dfs_path_to_door_1.addAll(dfs_path_to_cloak);
            } else {
                return null;
            }
        }
        if (straight) {
            if (dfs_path_to_door.size() < dfs_path_to_door_1.size()) {
                return dfs_path_to_door;
            } else return dfs_path_to_door_1;
        }
        return dfs_path_to_door_1;
    }

    public static ArrayList<Pair<Integer, Integer>> backtrack_to_book(int[][] grid, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        // Trying to find book straight forward
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        backtracking(grid, start, 4, 0, inspectors_zone);
        Pair<Integer, Integer> dest_dfs = B;
        ArrayList<Pair<Integer, Integer>> dfs_path_to_book = new ArrayList<>();
        Pair<Integer, Integer> cur_node = dest_dfs;
        boolean straight = true;
        if (backtracking_path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                dfs_path_to_book.add(cur_node);
                cur_node = backtracking_path.get(cur_node).getKey();
            }
            dfs_path_to_book.add(cur_node);


        } else {
            straight = false;
        }
        // Trying to find book via cloak
        start = new Pair<>(0, 0);
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        backtracking(grid, start, 5, 0, inspectors_zone);
        ArrayList<Pair<Integer, Integer>> dfs_path_to_book_1 = new ArrayList<>();
        dest_dfs = C;
        cur_node = dest_dfs;
        if (backtracking_path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                dfs_path_to_book_1.add(cur_node);
                cur_node = backtracking_path.get(cur_node).getKey();
            }
            dfs_path_to_book_1.add(cur_node);
        } else {
            return null;
        }
        ArrayList<Pair<Integer, Integer>> dfs_path_to_book_2 = new ArrayList<>();
        start = dest_dfs;
        backtracking_path.clear();
        min_steps = Integer.MAX_VALUE;
        inspectors_zone.clear();
        inspectors_zone.add(N);
        inspectors_zone.add(F);
        backtracking(grid, start, 4, 0, inspectors_zone);
        dest_dfs = B;
        cur_node = dest_dfs;
        if (backtracking_path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                dfs_path_to_book_2.add(cur_node);
                cur_node = backtracking_path.get(cur_node).getKey();
            }
            dfs_path_to_book_2.addAll(dfs_path_to_book_1);
        } else {
            return null;
        }
        if (straight) {
            if (dfs_path_to_book_2.size() < dfs_path_to_book.size()) {
                cloak_back = true;
                return dfs_path_to_book_2;
            }
            else{
                return dfs_path_to_book;
            }
        }

        return dfs_path_to_book_2;

    }

    public static ArrayList<Pair<Integer, Integer>> optimal_way_to_door(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone, Pair<Integer, Integer> dest, boolean cloak) {
        Pair<Integer, Integer> start = dest;
        // trying to find door straight forward
        Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> ans = bfs(grid, start, 6, inspectors_zone);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path_1 = ans.getKey();
        dest = ans.getValue();
        ArrayList<Pair<Integer, Integer>> normal_path = new ArrayList<>();
        ArrayList<Pair<Integer, Integer>> normal_path_2 = new ArrayList<>();
        Pair<Integer, Integer> cur_node = dest;
        while (!cur_node.equals(start)) {
            normal_path.add(cur_node);
            cur_node = path_1.get(cur_node);
        }
        normal_path.add(cur_node);
        if (!cloak) {

            // trying to find door via cloak
            Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> ans_1 = bfs(grid, start, 5, inspectors_zone);
            HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path_2 = ans_1.getKey();
            dest = ans_1.getValue();
            ArrayList<Pair<Integer, Integer>> normal_path_1 = new ArrayList<>();
            cur_node = dest;
            while (!cur_node.equals(start)) {
                normal_path_1.add(cur_node);
                cur_node = path_2.get(cur_node);
            }
            inspectors_zone.clear();
            inspectors_zone.add(N);
            inspectors_zone.add(F);
            normal_path_1.add(cur_node);
            Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> ans_2 = bfs(grid, dest, 6, inspectors_zone);
            HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path_3 = ans_2.getKey();
            start = dest;
            dest = ans_2.getValue();

            cur_node = dest;
            while (!cur_node.equals(start)) {
                normal_path_2.add(cur_node);
                cur_node = path_3.get(cur_node);
            }
            normal_path_2.addAll(normal_path_1);

        }
        if (ans.getValue() == null) {
            return normal_path_2;
        }
        if (cloak) return normal_path;

        else{
            if (normal_path.size() < normal_path_2.size()){
                return normal_path;

            }
            else {
                return normal_path_2;
            }
        }


    }

    public static ArrayList<Pair<Integer, Integer>> first_approach(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone) {


        Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> ans = bfs(grid, new Pair<>(0, 0), 4, inspectors_zone);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path = ans.getKey();
        ArrayList<Pair<Integer, Integer>> normal_path = new ArrayList<>();


        Pair<Integer, Integer> dest = ans.getValue();
        Pair<Integer, Integer> cur_node = dest;
        if (dest == null) {
            ans = bfs(grid, new Pair<>(0, 0), 5, inspectors_zone);
            path = ans.getKey();
            dest = ans.getValue();
            cur_node = dest;
            if (dest == null) {

                return null;
            }
            inspectors_zone.clear();
            inspectors_zone.add(N);
            inspectors_zone.add(F);

            Pair<Integer, Integer> start = new Pair<>(0, 0);
            if (path.containsKey(cur_node)) {
                while (!cur_node.equals(start)) {
                    normal_path.add(cur_node);
                    cur_node = path.get(cur_node);
                }
                normal_path.add(cur_node);
                //            normal_path.add(cur_node);
                //            System.out.println("Way to cloak");
                //            System.out.println(normal_path);
                ArrayList<Pair<Integer, Integer>> normal_path_with_cloak = new ArrayList<>();
                ans = bfs(grid, dest, 4, inspectors_zone);
                start = dest;
                path = ans.getKey();
                dest = ans.getValue();
                cur_node = dest;

                while (!cur_node.equals(start)) {
                    normal_path_with_cloak.add(cur_node);
                    cur_node = path.get(cur_node);
                }
                normal_path_with_cloak.addAll(normal_path);
                normal_path = normal_path_with_cloak;
            }
            else{
                return null;
            }

        }
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        if (normal_path.isEmpty()) {
            if (path.containsKey(cur_node)) {
                normal_path = new ArrayList<>();
                while (!cur_node.equals(start)) {
                    normal_path.add(cur_node);
                    cur_node = path.get(cur_node);
                }
                normal_path.add(cur_node);
            }
            else {
                return null;
            }
        }


        return normal_path;
    }

    public static ArrayList<Pair<Integer, Integer>> second_approach(int[][] grid, HashMap<Integer, Character> attributes, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        // Trying to find cloak
        Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> ans = bfs(grid, new Pair<>(0, 0), 5, inspectors_zone);
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> path = ans.getKey();
        ArrayList<Pair<Integer, Integer>> normal_path = new ArrayList<>();
        Pair<Integer, Integer> dest = ans.getValue();
        Pair<Integer, Integer> cur_node = dest;
        if (dest == null) {
            return null;
        }
        Pair<Integer, Integer> start = new Pair<>(0, 0);
        if (path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                normal_path.add(cur_node);
                cur_node = path.get(cur_node);
            }
        }
        else {
            return null;
        }
        normal_path.add(cur_node);
        inspectors_zone.clear();
        inspectors_zone.add(N);
        inspectors_zone.add(F);
        start = dest;
        // Trying to find book
        ans = bfs(grid, start, 4, inspectors_zone);
        path = ans.getKey();
        dest = ans.getValue();
        cur_node = dest;
        if (dest == null) {
            return null;
        }
        ArrayList<Pair<Integer, Integer>> normal_path_1 = new ArrayList<>();
        if (path.containsKey(cur_node)) {
            while (!cur_node.equals(start)) {
                normal_path_1.add(cur_node);
                cur_node = path.get(cur_node);
            }
        }
        else {
            return null;
        }

        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        list.addAll(normal_path_1);
        list.addAll(normal_path);
        return list;


    }

    public static void show(int[][] grid, HashMap<Integer, Character> attributes) {

        for (int i = 0; i < ROWS; ++i) {
            for (int j = 0; j < COLS; ++j) {
                System.out.print(attributes.get(grid[i][j]));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    public static Pair<HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>>, Pair<Integer, Integer>> bfs(int[][] grid, Pair<Integer, Integer> start, int idOfitem, HashSet<Pair<Integer, Integer>> inspectors_zone) {
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
            next_nodes = get_next_nodes(cur_node, inspectors_zone);
            for (Pair<Integer, Integer> next_node : next_nodes) {
                if (!visited.containsKey(next_node)) {
                    queue.add(next_node);
                    visited.put(next_node, cur_node);
                }

            }
        }
        return new Pair<>(visited, dest);
    }

    public static void backtracking(int[][] grid, Pair<Integer, Integer> node, int idOfitem, int steps, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        if (steps < min_steps) {
            if (grid[node.getKey()][node.getValue()] == idOfitem) {
                min_steps = steps;
                return;
            }
            ArrayList<Pair<Integer, Integer>> next_nodes = get_next_nodes(node, inspectors_zone);
            for (Pair<Integer, Integer> next_node : next_nodes) {
                if (backtracking_path.containsKey(next_node) && backtracking_path.get(next_node).getValue() >= steps + 1) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone);
                } else if (!backtracking_path.containsKey(next_node)) {
                    backtracking_path.put(next_node, new Pair<>(node, steps + 1));
                    backtracking(grid, next_node, idOfitem, steps + 1, inspectors_zone);
                }

            }
        }

    }

    public static ArrayList<Pair<Integer, Integer>> get_next_nodes(Pair<Integer, Integer> cur, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        int[][] ways = {{-1, 0},
                {0, -1},
                {1, 0},
                {0, 1},
                {-1, -1},
                {-1, 1},
                {1, -1},
                {1, 1},
        };
        ArrayList<Pair<Integer, Integer>> next_nodes = new ArrayList<>();
        for (int[] way : ways) {
            Pair<Integer, Integer> next_node = new Pair<>(way[0] + cur.getKey(), way[1] + cur.getValue());
            if (check_next_node(next_node.getKey(), next_node.getValue(), inspectors_zone)) next_nodes.add(next_node);
        }
        return next_nodes;
    }

    public static boolean check_next_node(int x, int y, HashSet<Pair<Integer, Integer>> inspectors_zone) {
        Pair<Integer, Integer> next_pos = new Pair<>(x, y);
        return x > -1 && x < ROWS && y > -1 && y < COLS && !inspectors_zone.contains(next_pos);
    }


}
