package bearmaps.utils.graph;

import bearmaps.utils.pq.MinHeapPQ;
import edu.princeton.cs.algs4.Stopwatch;
import jdk.jfr.Timespan;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private SolverOutcome outcome;
    private double solutionWeight = 0;
    private List<Vertex> solution;
    private double timeSpent = 0;
    private int num;
    private AStarGraph<Vertex> G;

    /*
    Constructor which finds the solution, computing everything necessary
    for all other methods to return their results in constant time.
    Note that timeout passed in is in seconds.
     */
    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        G = input;
        Path p = new Path(start,end,timeout);
        p.Path(start,end);
        timeSpent = sw.elapsedTime();
        num = p.n;
        if (outcome == SolverOutcome.UNSOLVABLE) {
            solution = new ArrayList<Vertex>();
        } else {
            if (timeSpent < timeout) {
                solution = p.result;
                outcome = SolverOutcome.SOLVED;
                solutionWeight = p.disTo.get(end);
            } else {
                solution = new ArrayList<Vertex>();
                outcome = SolverOutcome.TIMEOUT;
            }
        }
    }

    private class Path {
        ArrayList<Vertex> result = new ArrayList<Vertex>();
        Map<Vertex, Double> disTo = new HashMap<Vertex, Double>();
        Map<Vertex, Vertex> edgeTo = new HashMap<Vertex, Vertex>();
        MinHeapPQ<Vertex> fringe = new MinHeapPQ<>();
        HashSet<Vertex> visited;
        int n = 0;
        Stopwatch sw1;
        double timeout;

        public Path(Vertex start, Vertex stop,double timeout) {
            sw1 = new Stopwatch();
            fringe.insert(start, H(start));
            disTo.put(start, 0.0);
            this.timeout = timeout;
        }
        // while the PQ is not empty, PQ.peek() is
        // not the goal, and timeout is not exceeded:
        public void Path(Vertex start, Vertex stop) {
            while( !fringe.isEmpty() && fringe.peek() != stop
                    && timeSpent <= timeout) {
                Vertex curr = fringe.poll();
                n += 1;
                visited.add(curr);
                for (WeightedEdge e : G.neighbors(curr)) {
                    relax(e);
                }
                timeSpent = sw1.elapsedTime();
            }
            if (fringe.isEmpty() && fringe.peek() != stop) {
                outcome = SolverOutcome.UNSOLVABLE;
                return;
            }
            if (timeSpent < timeout) {
                result.add(0,stop);
                while (result.get(0) != start) {
                    Vertex curr = result.get(0);
                    result.add(0,edgeTo.get(curr));
                }
            }
        }

        public void relax(WeightedEdge e) {
            Vertex p = (Vertex) e.from();
            Vertex q = (Vertex) e.to();
            double w = e.weight();
            disTo.putIfAbsent(q,w);
            edgeTo.putIfAbsent(q,p);
            fringe.insert(q,H(q) + disTo.get(q));
            if (disTo.get(q) > w + disTo.get(p)) {
                disTo.put(q,w);
                edgeTo.put(q,p);
            }
        }

        public double H(Vertex curr) {
            double smallest = Double.POSITIVE_INFINITY;
            for (WeightedEdge<Vertex> a: G.neighbors(curr)){
                if (a.weight() < smallest) {
                    smallest = a.weight();
                }
            }
            return smallest;
        }
    }



    /*
    The result should be:
    SOLVED if the AStarSolver was able to complete all work in the time given.
    UNSOLVABLE if the priority queue became empty before finding the solution.
    TIMEOUT if the solver ran out of time.
    You should check to see if you have run out of time every time you dequeue.
     */
    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    /*
    The total number of priority queue poll() operations.
    Should be the number of states explored so far if result was TIMEOUT or UNSOLVABLE
     */
    @Override
    public int numStatesExplored() {
        return num;
    }

    @Override
    public double explorationTime() {
        return timeSpent ;
    }
}
