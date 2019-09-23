package com.kcsl.graphio.export;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.open.commons.utilities.address.NormalizedAddress;

/**
 * 
 * Template for exporter, currently only DOT format is supported
 * 
 * @author payas
 * 
 */

public class Exporter {
	
	private String fileName;
	private Graph graph;
	private Map<String,String> nodeIdMap;
	
	public Exporter(Graph graph, String fileName) {
		this.graph = graph;
		this.fileName = fileName;
		this.nodeIdMap = new HashMap<String, String>();
	}
	
	public void exportGraph() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
		writer.write("digraph G {\n");
		
		int nodeCount = 1;
		for(Node n : graph.nodes()) {
			writer.write("\t");
			String nodeId = "n" + nodeCount;
			writer.write(nodeId);
			String nodeName = n.getAttr(XCSG.name).toString();
			writer.write(" [label=\"" + nodeName + "\"];\n");
			nodeIdMap.put(n.getAttr(NormalizedAddress.NORMALIZED_ADDRESS_ATTRIBUTE).toString(), nodeId);
			nodeCount = nodeCount + 1;
		}
		
		for(Edge e : graph.edges()) {
			Node from = e.from();
			String fromId = nodeIdMap.get(from.getAttr(NormalizedAddress.NORMALIZED_ADDRESS_ATTRIBUTE).toString());
			Node to = e.to();
			String toId = nodeIdMap.get(to.getAttr(NormalizedAddress.NORMALIZED_ADDRESS_ATTRIBUTE).toString());
			writer.write("\t");
			writer.write(fromId + " -> " + toId + ";");
			writer.write("\n");
		}
		writer.write("}");
		writer.close();
	}

}
