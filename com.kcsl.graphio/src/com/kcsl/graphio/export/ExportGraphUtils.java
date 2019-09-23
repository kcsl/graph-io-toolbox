package com.kcsl.graphio.export;

import java.io.IOException;

import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.open.commons.analysis.CommonQueries;

/**
 * Utilities to export Atlas graphs to commonly used graph formats
 * 
 * @author Payas Awadhutkar
 */

public class ExportGraphUtils {
	
	public static void export(Q graph, String filePath) throws IOException {
		export(graph.eval(), filePath);
	}
	
	public static void export(Graph graph, String filePath) throws IOException {
		Exporter exporter = new Exporter(graph, filePath);
		exporter.exportGraph();
	}
	
	public static void exportCFG(String functionName, String filePath) throws IOException {
		exportCFG(functionName, filePath, false);
	}
	
	public static void exportCFGWithContainers(String functionName, String filePath) throws IOException {
		exportCFG(functionName, filePath, true);
	}

	private static void exportCFG(String functionName, String filePath, boolean containers) throws IOException {
		Q function = CommonQueries.functions(functionName);
		Q functionCFG = CommonQueries.cfg(function);
		if(containers) {
			Q functionContainers = function.containers();
			functionCFG = functionCFG.union(functionContainers).induce(Common.edges(XCSG.Contains));
		}
		export(functionCFG, filePath);
	}
	
}
