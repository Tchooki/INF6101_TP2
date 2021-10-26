package minicp.examples;

import minicp.cp.Factory;
import minicp.engine.core.IntVar;
import minicp.engine.core.Solver;
import minicp.search.DFSearch;
import minicp.search.SearchStatistics;

import java.util.Arrays;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


import static minicp.cp.BranchingScheme.*;
import static minicp.cp.Factory.*;

/**
 * Programme test du tp2 de INF6101
 */
public class Tp2Test {

    public static void main(String[] args) {

	int domaineMin = Integer.parseInt(args[0]);
	int domaineMax = Integer.parseInt(args[1]);
	int cte = Integer.parseInt(args[2]);

        Solver cp = Factory.makeSolver();

	IntVar[] x = new IntVar[3];
	for(int i = 0; i < 3; i++)
	    x[i] = makeIntVar(cp, domaineMin, domaineMax);
	
 	cp.post(tp2(x[0],x[1],x[2]));
 	cp.post(tp2(x[2],x[1],mul(x[0],cte)));
  	cp.post(tp2(x[1],minus(x[0],cte),x[2]));

     	DFSearch dfs = makeDfs(cp, splitLargeDom(x));

        dfs.onSolution(() -> {
		System.out.println(Arrays.toString(x));
	    });

	SearchStatistics stats = dfs.solve();

	System.out.println(stats);

    }

}
