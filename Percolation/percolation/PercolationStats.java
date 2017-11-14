import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import java.lang.Math;

public class PercolationStats {
    private double[] testResults;
    private double trials;
    private double mean;
    private double stddev;
    
    public PercolationStats(int n, int trials){
        if( n <= 0 || trials <= 0 ) {
            throw new IllegalArgumentException("Argument does not fit within range. n=" + n + " trials=" + trials);
        }
        
        int randomRow = StdRandom.uniform(n)+1; // random row
        int randomCol = StdRandom.uniform(n)+1; // random col
        
        testResults = new double[trials];
        this.trials = trials;
        
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException("Argument is less than or equal to 0.");
        }
        else{
            for(int i = 0; i < trials; i++){
                Percolation test = new Percolation(n);
                while(!test.percolates()){
                    while(test.isOpen(randomRow, randomCol)){
                        randomRow = StdRandom.uniform(n)+1;
                        randomCol = StdRandom.uniform(n)+1;
                    }
                    test.open(randomRow, randomCol);
                }
                testResults[i] = (double)test.numberOfOpenSites() / (double)(n*n);
            }
            
            mean = StdStats.mean(testResults);
            stddev = StdStats.stddev( testResults );
        }
    }// perform trials independent experiments on an n-by-n grid
    
    public double mean(){
        return mean;
    }// sample mean of percolation threshold
    
    public double stddev(){
        return stddev;
    }// sample standard deviation of percolation threshold
    
    public double confidenceLo(){
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }// low  endpoint of 95% confidence interval
    
    public double confidenceHi(){
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }// high endpoint of 95% confidence interval

    public static void main(String[] args){
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
                                                            
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev()) ;
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }// test client (described below)
}