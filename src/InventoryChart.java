// This class is used to display the bar graph. The attributes of this class are String title, int[] stats.
// The displayGraph method makes the GUI visible. It is a single screen UI with a bar chart and two bars of different colors.
// One bar depicts the number of available fiction books and the other bar depicts the number of available non-fiction books.

import javax.swing.*;
import java.awt.*;

public class InventoryChart {
    // Essential attributes
    private String title;
    private int[] stats;

    // Created a constructor
    public InventoryChart(String title, int[] stats) {
        this.title = title;
        this.stats = stats;
    }

    public void displayGraph() {
        // Create a new JFrame with the title
        JFrame frame = new JFrame(title);

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the size of the frame
        frame.setSize(700, 900);

        // Create a new JPanel with a GridLayout of 1 row and 2 columns
        JPanel chartPanel = new JPanel(new GridLayout(1, 2));

        // Create a new JComponent for the fiction books bar chart
        JComponent fiction = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Calculate the height of the bar based on the stats array
                int height = (int) ((double) stats[0] / (double) (stats[0]+stats[1]) * getHeight());

                // Set the color to magenta
                g.setColor(Color.MAGENTA);

                // Draw the bar with a width of 100 and the calculated height
                g.fillRect(60, getHeight()-height, 100, height);

                // Set the color to black
                g.setColor(Color.BLACK);

                // Draw the label for the bar with the number of fiction books
                g.drawString("Fiction Books: " + stats[0], 170, getHeight() - 5);
            }
        };


        // Create a new JComponent for the non-fiction books bar chart
        JComponent nonfiction = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Set the color to green
                g.setColor(Color.GREEN);

                // Calculate the height of the bar based on the stats array
                int height = (int) ((double) stats[1] / (double) (stats[0]+stats[1]) * getHeight());

                // Draw the bar with a width of 100 and the calculated height
                g.fillRect(0, getHeight()-height, 100, height);

                // Set the color to black
                g.setColor(Color.BLACK);

                // Draw the label for the bar with the number of non-fiction books
                g.drawString("Nonfiction Books: " + stats[1], 115, getHeight() - 5);
            }
        };


        // Add the two JComponents to the JPanel
        chartPanel.add(fiction);
        chartPanel.add(nonfiction);
        // Add the JPanel to the content pane of the JFrame
        frame.getContentPane().add(chartPanel);
        // Make the JFrame visible
        frame.setVisible(true);
    }

    // Setter and Getter methods

    public int[] getStats() {
        return stats;
    }

    public void setStats(int[] stats) {
        this.stats = stats;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}





