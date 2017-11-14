import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private boolean[][] percModel; // if 1 open if 0 closed
    private int n;
    private WeightedQuickUnionUF percolate; // used to find if the system percolates
    private int numOpen;
    
    public Percolation(int n){
        if( n <= 0 ) {
            throw new IllegalArgumentException("Argument does not fit within range. n=" + n);
        }
        
        this.n = n;
        
        this.numOpen = 0;
        
        percolate = new WeightedQuickUnionUF(n*n + 2); // if n=5, 0-24 is percModel, 25 is top, 26 is bottom
     
        percModel = new boolean[n+1][n+1];
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= n; j++){
                percModel[i][j] = false; 
            }
        }
    }// create n-by-n grid, with all sites blocked
    
    public void open(int row, int col) {
        if (row > n || row <= 0 || col > n || col <= 0){
            throw new IllegalArgumentException("Argument does not fit within range. row=" + row + " col=" + col );
        }
        else if ( !percModel[row][col] ) { // can assume not out of bounds
            percModel[row][col] = true;
            numOpen++;
            
            if (row == 1){ // if first row then add it to the component with the top
                percolate.union((col-1) + ((row-1) * n), n*n);
            }
            if (row == n){ // if bottom row then add it to the component with the bottom
                percolate.union((col-1) + ((row-1) * n), (n*n)+1);
            }
            
            if (row-1 > 0 && isOpen(row-1, col)){
                percolate.union((col-1) + ((row-1) * n), (col-1) + ((row-2) * n));
            }
            if ( row+1 <= n && isOpen( row+1, col ) ) {
                percolate.union((col-1) + ((row-1) * n), (col-1) + ((row) * n));
            }
            if (col-1 > 0 && isOpen(row, col-1)){
                percolate.union((col-1) + ((row-1) * n), (col-2) + ((row-1) * n));
            }
            if (col+1 <= n && isOpen(row, col+1)){
                percolate.union((col-1) + ((row-1) * n), (col) + ((row-1) * n));
            }
        }
    } // open site (row, col) if it is not open already
    
    public boolean isOpen(int row, int col){
        if (row > n || row <= 0 || col > n || col <= 0){
            throw new IllegalArgumentException("Argument does not fit within range. row=" + row + " col=" + col );
        }
        else {
            return percModel[row][col];
        }
    } // is site (row, col) open?
    
    public boolean isFull(int row, int col){
        if (row > n || row <= 0 || col > n || col <= 0){
            throw new IllegalArgumentException("Argument does not fit within range. row=" + row + " col=" + col );
        }
        else {
            return percolate.connected(n*n, (col-1) + ((row-1) * n));
        }
    } // is site (row, col) full?
    
    public int numberOfOpenSites(){
        return numOpen;
    }      // number of open sites
    
    public boolean percolates(){
        return percolate.connected(n*n, (n*n)+1);
    }             // does the system percolate?

    public static void main(String[] args){
        Percolation test = new Percolation(5);
        StdOut.println(test.percolates());
        
        
        test.open(1,1);
        test.open(2,1);
        test.open(3,1);
        test.open(4,1);
        test.open(5,1);
        
        StdOut.println(test.numberOfOpenSites());
        StdOut.println(test.isFull(1,2));
        StdOut.println(test.isFull(2,1));
        
        StdOut.println(test.percolates());
    }  // test client (optional)
}