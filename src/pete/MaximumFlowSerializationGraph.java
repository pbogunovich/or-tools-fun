package pete;

import java.util.ArrayList;
import java.util.List;


class MaximumFlowSerializationGraph {
  public int source;
  public int sink;
  public List<Integer> edgeSources = new ArrayList<>();
  public List<Integer> edgeTargets = new ArrayList<>();
  public List<Integer> capacities = new ArrayList<>();
}
