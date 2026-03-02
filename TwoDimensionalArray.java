package proje;



public class TwoDimensionalArray {
    // Two-dimensional array to store ratings
    private int[][] ratings;

    // Initialize the ratings array with given rows and columns
    public TwoDimensionalArray(int rowCount, int colCount) {
        this.ratings = new int[rowCount][colCount];
    }

    // Return the ratings array
    public int[][] getRatings() {
        return ratings;
    }

    // Set the ratings array to a new array
    public void setRatings(int[][] ratings) {
        this.ratings = ratings;
    }
    
    // Set a specific row with customer ID and ratings
    public void setRatingsRow(int row, int[] rowData, int customerID) {
        // Validate the row index and data length
        if (row >= 0 && row < ratings.length && rowData.length + 1 == ratings[0].length) {
            ratings[row][0] = customerID;
            for (int i = 0; i < rowData.length; i++) {
                ratings[row][i + 1] = rowData[i];
            }
        } else {
            System.out.println("Invalid row index or row data length.");
        }
    }

    // Print specified rows of the array
    public static void printRatings(int[][] array, int rowCount) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println();
        }
    }
}


