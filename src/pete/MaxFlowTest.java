package pete;

import java.util.logging.Logger;

import com.google.ortools.graph.MaxFlow.Status;
import com.google.ortools.graph.MaxFlow;


public class MaxFlowTest {
  private static final Logger logger =
    Logger.getLogger(MaxFlowTest.class.getName());

  static {
    System.loadLibrary("jniortools");
  }

  private void run() {
    int numNodes = 5;
    int numArcs = 9;
    int[] sources = {0, 0, 0, 1, 1, 2, 2, 3, 3};
    int[] destinations = {1, 2, 3, 2, 4, 3, 4, 2, 4};
    int[] capacities = {20, 30, 10, 40, 30, 10, 20, 5, 20};

    MaxFlow maxFlow = new MaxFlow();

    for (int i = 0; i < numArcs; i++) {
      int arc = maxFlow.addArcWithCapacity(
        sources[i], destinations[i], capacities[i]);
      if (arc != i) {
        throw new RuntimeException("POOP!");
      }
    }
    int source = 0;
    int sink = numNodes - 1;

    logger.info(String.format(
      "Solving max flow with %d nodes, and %d arc, source=%d, sink=%d",
      numNodes, numArcs, source, sink));

    Status solveStatus = maxFlow.solve(source, sink);
    if (solveStatus == Status.OPTIMAL) {
      long totalFlow = maxFlow.getOptimalFlow();
      logger.info(String.format(
        "total computed flow: %d", totalFlow));
      for (int i = 0; i < numArcs; i++) {
        logger.info(String.format(
          "Arc %d (%d -> %d), capacity = %d / computed = %d",
          i,
          maxFlow.getTail(i),
          maxFlow.getHead(i),
          maxFlow.getCapacity(i),
          maxFlow.getFlow(i)));
      }
    } else {
      logger.info(String.format(
        "Solving max flow problem failed. Solver status %s", solveStatus));
    }
  }

  public static void main(String[] args) {
    MaxFlowTest test = new MaxFlowTest();
    test.run();
  }
}
