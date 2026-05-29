package game;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreTree {

    private ScoreNode root;

    public void insert(int score, int level) {
        root = insert(root, score, level);
    }

    private ScoreNode insert(ScoreNode node, int score, int level) {
        // TODO (Phase 4): Insert a new score into the BST recursively.
        //
        // Follow the insert() pattern from the Phase 4 guide, adapting it to
        // pass the level parameter when constructing a new ScoreNode.
            if (node == null) return new ScoreNode(score, level);
            if (score < node.score) {
                node.left = insert(node.left, score, level);//changes left side by adding it if smaller
            } else {
                node.right = insert(node.right, score, level);//higher goes on right
            }
            return node;//return once no scores to add

        //return node; // placeholder — replace this
    }

    // Reverse in-order (right → node → left) yields descending order
    public List<ScoreNode> getTopScores(int n) {
        List<ScoreNode> result = new ArrayList<>();
        collectDescending(root, result, n);
        return result;
    }

    private void collectDescending(ScoreNode node, List<ScoreNode> result, int n) {
        // TODO (Phase 4): Collect the top n scores in descending order.
        //
        // The Phase 4 guide shows printInOrder() which visits left → node → right.
        // Adapt that pattern to visit right → node → left instead (larger scores
        // live in the right subtree), and stop once result has n items.{
            if (node == null) return;
            if (result.size() >= n) return;

            collectDescending(node.right, result, n);//goes right and adds if higher then left
            if (result.size() < n)
            {
                result.add(node);
            }
            collectDescending(node.left, result, n);



    }

    public boolean isEmpty() {
        return root == null;
    }

    // -----------------------------------------------------------------------
    // Persistence — scores survive between sessions via a plain text file.
    // Each line: "<score> <level>"
    // -----------------------------------------------------------------------

    public void saveToFile(Path path) {
        // TODO (Phase 3): Write all scores to the file, one per line.
        // 1. Get the formatted lines by calling collectInOrder(root, lines).
        // 2. Write each line using BufferedWriter — see the Phase 3 guide for the pattern.
        List<String> lines = new ArrayList<>();//makes an arraylist to check scores.txt
        collectInOrder(root, lines);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {//writes to the file scores.txt
            for (String line : lines) {
                writer.write(line);
                writer.newLine();   // writes a line break
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void collectInOrder(ScoreNode node, List<String> lines) {
        if (node == null) return;
        collectInOrder(node.left, lines);
        lines.add(node.score + " " + node.level);
        collectInOrder(node.right, lines);
    }

    public void loadFromFile(Path path) {
        // TODO (Phase 3): Read scores back from the file written by saveToFile().
        // 1. Return early if the file does not exist (Files.exists(path)).
        // 2. Read all lines with Files.readAllLines(path) — wrap in try/catch.
        // 3. Use a HashSet to skip duplicate lines — see the Phase 3 guide for
        //    how HashSet works and why add() is useful here.
        // 4. For each unique line, split on " ", parse the two integers,
        //    and call insert().

        if (!Files.exists(path))
        {
            return;
        }

            Set<String> seen = new HashSet<>();//seen helps prevent from duplicates
            try {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    if (seen.add(line)) {   // false if already seen — skip it
                    // parse each line here
                        String[] split = line.split(" ");//splits score and level
                        int score = Integer.parseInt(split[0]);//checks left word
                        int level = Integer.parseInt(split[1]);// then right

                        // process line
                        insert(score, level);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public boolean search(int score) {//follows same format as insert in the sense that it has a helper method
        if (score <= 0){
            return false;
        }
        return search(root, score);
    }
    private boolean search(ScoreNode node, int score) {
        if (node == null) return false;
        if (score == node.score) {//return true if found
            return true;
        }
        if (score < node.score) {// checks left side first
            return search(node.left, score);
        }
        else{
            return search(node.right, score);//then right if not
        }
    }

    public int max()
    {
        if (root == null)
        {
            return 0;//returns 0 no max
        }
        ScoreNode current = root;//use local variable to track
        while(current.right != null)
        {
            current = current.right;// if the node keeps going right then it will be replaced by the next one
        }
        return current.score;//returns the value where it stopped
    }
}
