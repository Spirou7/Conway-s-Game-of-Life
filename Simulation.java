public class Simulation {
  public boolean[][] grid;

  public Simulation(int width, int height) {
    grid = new boolean[width][height];
  }

  // Density should be between 0 and 1
  public void randomPopulate(double density) {
    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        // Based on the density, randomly assign it to either true or false
        double chance = Math.random();

        if (chance <= density) {
          grid[i][j] = true;
        } else {
          grid[i][j] = false;
        }
      }
    }
  }

  public void Iterate() {

    boolean[][] newGrid = new boolean[grid.length][grid[0].length];

    for (int i = 0; i < grid.length; i++) {
      for (int j = 0; j < grid[i].length; j++) {
        newGrid[i][j] = grid[i][j];
      }
    }
    for (int i = 1; i < newGrid.length - 1; i++) {
      for (int j = 1; j < newGrid[i].length - 1; j++) {
        boolean isAlive = newGrid[i][j];

        // Count number of surroundings

        int count = 0;
        for (int x = -1; x < 2; x++) {
          for (int y = -1; y < 2; y++) {
            if (!(x == 0 && y == 0)) {
              if (grid[i + x][j + y]) {
                count++;
              }
            }
          }
        }

        // Check conditions
        if (isAlive) {
          if (count < 2)
            newGrid[i][j] = false;
          else if (count == 2 || count == 3) {
            newGrid[i][j] = true;
          } else if (count > 3)
            newGrid[i][j] = false;
        } else {
          if (count == 3) {
            newGrid[i][j] = true;
          }
        }
      }
    }

    grid = newGrid;
  }

}