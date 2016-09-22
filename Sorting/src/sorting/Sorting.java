package sorting;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.*;
import javax.sound.sampled.*;

/**
 * The main class for the graphical sorting algorithm demonstration. This class
 * manages the sorting algorithms and deals with the graphical side.
 */
public class Sorting extends JFrame {

    // Screen size
    private static int SCREEN_WIDTH = 800;
    private static int SCREEN_HEIGHT = 600;

    // The size of the data sample
    private static final int DATA_SIZE = 2000;

    private int[] data;				// The data to be sorted. The algorithm will change this array
    private int[] origData;			// The original data for copying purposes
    private Color[] dataColor;		// The color each data bar should be, to show what operations the algorithms are doing
    private boolean isChecking;		// If the algorithm is finished and we're checking its work
    private int lastCheck;			// The last checked index for when we're checking the algorithm's work

    private ArrayList<SortingAlgorithm> algorithms;		// List of algorithms
    private ArrayList<DataAccessor> results;			// List of results from the algorithms
    private int currResult;								// The current algorithm results being displayed

    private int accessCnt;	// How many accesses to the array the current algorithm has done
    private int setCnt;     // How many edits to the array the current algorithm has done

    private double delay;	// The artificial delay between algorithm operations
    private int delayMultiple; // The number of times past 1 that delay has gone.  

    // Listens for key presses
    public class SortingKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            // Unused, but we need it here to implement KeyListener
        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                // Increase the artificial delay
                if (delay < 1) {
                    delay += .01;
                    delayMultiple -= 10;
                } else {
                    delay += 10;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP) {
                // Decrease the artificial delay
                if (delay > 1) {
                    delay -= 10;
                } else if (delay < 2 && delay > 0.019) {
                    delay -= .01;
                    delayMultiple += 10;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                // Switch to the next algorithm
                if (currResult + 1 < results.size()) {
                    currResult++;
                    // Reset state
                    accessCnt = 0;
                    setCnt = 0;
                    isChecking = false;
                    lastCheck = 0;
                    results.get(currResult).reset();
                    System.arraycopy(origData, 0, data, 0, DATA_SIZE);
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                // Switch to the previous algorithm
                if (currResult > 0) {
                    currResult--;
                    // Reset state
                    accessCnt = 0;
                    setCnt = 0;
                    isChecking = false;
                    lastCheck = 0;
                    results.get(currResult).reset();
                    System.arraycopy(origData, 0, data, 0, DATA_SIZE);
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            // Unused, but we need it here to implement KeyListener
        }
    }

    public Sorting() throws LineUnavailableException {
        Sound.init();

        // Create our data set
        data = new int[DATA_SIZE];
        dataColor = new Color[DATA_SIZE];
        for (int i = 0; i < DATA_SIZE; i++) {
            data[i] = i;
            dataColor[i] = Color.WHITE;
        }
        shuffle(data);
        origData = new int[DATA_SIZE];
        System.arraycopy(data, 0, origData, 0, DATA_SIZE);	// Make a copy of the original so it can be edited later

        delay = 101;	// Default artificial delay

        // Put new algorithms here in the order we want to present them
        algorithms = new ArrayList<>();
        algorithms.add(new RadixLSD());
        algorithms.add(new BubbleSort());
        algorithms.add(new InsertionSort());
        algorithms.add(new GnomeSort());
        algorithms.add(new SelectionSort());
        algorithms.add(new MergeSort());
        algorithms.add(new CocktailSort());
        algorithms.add(new ShellSortTokuda());
        algorithms.add(new ShellSortCiura());
        algorithms.add(new HeapSort());
        algorithms.add(new QuickSortLomuto());
        algorithms.add(new QuickSortHoare());
        algorithms.add(new RadixLSD());
        algorithms.add(new RadixMSD());
        algorithms.add(new ShotGunSort());

        // Run and store the results of the algorithms
        results = new ArrayList<>(algorithms.size());
        for (int i = 0; i < algorithms.size(); i++) {
            results.add(new DataAccessor(data));
            algorithms.get(i).run(results.get(i));
        }
        currResult = 0;	// Start at the first algorithm

        // Basic Swing window defaults
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(true);
        setFocusable(true);
        createBufferStrategy(2);

        // Set up to listen for key presses
        KeyListener keyListener = new SortingKeyListener();
        addKeyListener(keyListener);

        // Start the main loop
        mainLoop();
    }

    /**
     * Shuffles the given array using the Fisher-Yates shuffling algorithm.
     */
    public static void shuffle(int[] array) {
        Random r = new Random();
        for (int i = 1; i < DATA_SIZE; i++) {
            int j = r.nextInt(i);
            int temp = array[j];
            array[j] = array[i];
            array[i] = temp;
        }
    }

    /**
     * Entry point of the program. We need to create a new instance of the main
     * class for Swing.
     */
    public static void main(String[] args) throws LineUnavailableException {
        new Sorting();
    }

    /**
     * Check the next data element to make sure it appears to be sorted
     * correctly.
     */
    public void check() {
        if (lastCheck == 0) {
            // Assume the first element is sorted correctly
            dataColor[lastCheck] = Color.GREEN;
            lastCheck++;
            return;
        } else if (lastCheck >= dataColor.length - 1) {
            // Assume the last element is sorted correctly
            dataColor[lastCheck] = Color.GREEN;
            return;
        } else if (data[lastCheck] < data[lastCheck + 1]) {
            // Check that the current element is less than the next one
            dataColor[lastCheck] = Color.GREEN;
            Sound.play(300 + (int) (100 * ((double) data[lastCheck] / (double) DATA_SIZE)));
            lastCheck++;
        } else {
            // The data is not sorted correctly. Make everything red.
            for (int i = 0; i < DATA_SIZE; i++) {
                dataColor[i] = Color.RED;
                isChecking = false;
            }
        }
    }

    /**
     * Run a single algorithm action.
     */
    public void processMovement() {
        for (int i = 0; i < DATA_SIZE; i++) {
            dataColor[i] = Color.WHITE;
        }

        // Get the next action
        DataAccessor.Action action = results.get(currResult).nextAction();
        if (action != null) {
            if (action.type == DataAccessor.ActionType.SET) {
                // The algorithm set an element to a value
                data[action.index] = action.data;
                dataColor[action.index] = Color.RED;
                setCnt++;
            } else if (action.type == DataAccessor.ActionType.GET) {
                Sound.play(300 + (int) (1000 * ((double) data[action.index] / (double) DATA_SIZE)));
                // The algorithm got an element from the data
                dataColor[action.index] = Color.BLUE;
                accessCnt++;
            } else if (action.type == DataAccessor.ActionType.DONE) {
                // The algorithm claims the data has been sorted. Time to check its work
                isChecking = true;
            }
        }
    }

    /**
     * Main execution loop.
     */
    public void mainLoop() {
        long lastSort = System.currentTimeMillis();
        long lastFrame = System.currentTimeMillis();
        int frameCnt = 0;
        while (true) {
            draw();	// Draw to the screen. We do this as fast as possible
            // Check if the artificial delay has passed and it's time to do an algorithm step
            if (System.currentTimeMillis() - lastSort >= delay) {
                if (isChecking) {
                    check();	// If we're checking, check the data
                } else {
                    processMovement(); // Otherwise, run an algorithm step
                    if (delay < 1) {
                        for (int i = 0; i < delayMultiple; i++) {
                            processMovement();
                        }
                    }
                }
                lastSort = System.currentTimeMillis();
            }

            frameCnt++;
            if (System.currentTimeMillis() - lastFrame >= 1000) {
                System.out.println(frameCnt);
                frameCnt = 0;
                lastFrame = System.currentTimeMillis();
            }
        }
    }

    /**
     * Draws a single frame and puts it on screen.
     */
    public void draw() {
        // We're changing things on screen very fast, so it's important that we
        // prepare a frame first before showing it. Swing defaults to showing
        // the frame every time something is drawn, but that results in really
        // poor performance. Using a buffer strategy fixes that, because we're
        // buffering the frame before showing it.
        BufferStrategy bf = getBufferStrategy();
        // Get the graphics context
        Graphics g = bf.getDrawGraphics();

        // Clear the screen before drawing
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        SCREEN_WIDTH = this.getSize().width;
        SCREEN_HEIGHT = this.getSize().height;

        // Draw every data point
        for (int i = 0; i < DATA_SIZE; i++) {
            // Every data point bar is the same size, but we move them down
            // the smaller the data point is. This can be effectively thought of
            // as changing the bar's height like a histogram, but changing
            // position is easier with Swing's coordinate system.
            int x = (int) ((double) SCREEN_WIDTH * ((double) i / (double) DATA_SIZE));
            int y = SCREEN_HEIGHT - (int) ((double) (SCREEN_HEIGHT - 10) * ((double) data[i] / (double) DATA_SIZE));
            int width = (int) Math.ceil((double) SCREEN_WIDTH / (double) DATA_SIZE);
            int height = SCREEN_HEIGHT - 10;

            // Draw the bar and a black outline
            g.setColor(dataColor[i]);
            g.fillRect(x, y, width, height);
//			g.setColor(Color.BLACK);
//			g.drawRect(x, y, width, height);
        }

        // Draw the text containing the algorithm name and some stats
        // Buble is not counting correctly
        g.setColor(Color.GREEN);
        g.setFont(new Font("default", Font.BOLD, 14));
        g.drawString(algorithms.get(currResult).getName(), 10, 50);
        g.drawString("Access #: " + accessCnt, 200, 50);
        g.drawString("Total Access #: " + results.get(currResult).getAccess(), 200, 65);
        g.drawString("Set #: " + setCnt, 400, 50);
        g.drawString("Total Set #: " + results.get(currResult).getSet(), 400, 65);
        g.drawString("Delay: " + String.format("%.2f", delay) + "ms", 600, 50);

        // Flip the buffers. In other words, we've finished the frame and are
        // ready to show it.
        bf.show();

        // Notify Swing that we want the screen redrawn with the new frame
        Toolkit.getDefaultToolkit().sync();
    }

}
