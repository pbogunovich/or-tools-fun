package pete;

import java.io.File;
import java.time.Duration;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.ortools.graph.MaxFlow.Status;
import com.google.ortools.graph.MaxFlow;


public class MaximumFlowTest {
  private static final Logger logger =
    Logger.getLogger(MaximumFlowTest.class.getName());

  static {
    System.loadLibrary("jniortools");
  }

  private void run() throws Exception {
    long startMillis = System.currentTimeMillis();

    ObjectMapper mapper = new ObjectMapper();

    MaximumFlowSerializationGraph sGraph =
      mapper.readValue(
        new File("/home/pete/Downloads/foobar.json"),
        MaximumFlowSerializationGraph.class);
    logger.info(String.format(
      "*** Loaded graph has (%d) edges", sGraph.edgeSources.size()));

    int numArcs = sGraph.edgeSources.size();

    MaxFlow maxFlow = new MaxFlow();

    for (int i = 0; i < numArcs; i++) {
      int arc = maxFlow.addArcWithCapacity(
        sGraph.edgeSources.get(i),
        sGraph.edgeTargets.get(i),
        sGraph.capacities.get(i));
      if (arc != i) {
        throw new RuntimeException("POOP!");
      }
    }

    logger.info(String.format(
      "Solving max flow with %d arcs, source=%d, sink=%d",
      sGraph.edgeSources.size(), sGraph.source, sGraph.sink));

    logger.info("Calculating flow...");
    long flowStartMillis = System.currentTimeMillis();

    Status solveStatus = maxFlow.solve(sGraph.source, sGraph.sink);
    if (solveStatus == Status.OPTIMAL) {
      long totalFlow = maxFlow.getOptimalFlow();
      logger.info(String.format(
        "total computed flow: %d", totalFlow));

      long endMillis = System.currentTimeMillis();

      Duration totalDuration = Duration.ofMillis(endMillis - startMillis);
      Duration flowDuration = Duration.ofMillis(endMillis - flowStartMillis);

      logger.info(String.format(
        "Total duration: (%s), Flow duration: (%s)",
        totalDuration, flowDuration));

      for (int i = 0; i < 20; i++) {
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

  public static void main(String[] args) throws Exception {
    MaximumFlowTest test = new MaximumFlowTest();
    test.run();
  }
}
