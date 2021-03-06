package hackerrank.graphs.dijstra_shortest_path;

import java.util.*;

// https://www.hackerrank.com/challenges/dijkstrashortreach

public class DijkstraShortestPath {

    Map<Integer,Map<Integer,Integer>> vertices = new HashMap<>();

    public void addWeightedEdge(Integer v1, Integer v2, Integer w) {
        if(vertices.get(v1) == null)
            vertices.put(v1, new HashMap<>());

        if(vertices.get(v1).get(v2)!=null){
            if(vertices.get(v1).get(v2) < w)
                w=vertices.get(v1).get(v2);
        }
        vertices.get(v1).put(v2,w);
    }

    public Integer getWeight(Integer v1, Integer v2) {
        Map<Integer,Integer> map = vertices.get(v1);
        return map.get(v2);

    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        int testCases = Integer.parseInt(reader.nextLine());
        StringBuilder[] output = new StringBuilder[testCases];

        for(int i=0;i<testCases;i++) {
            output[i] = new StringBuilder("");
            DijkstraShortestPath dsp = new DijkstraShortestPath();

            String nm = reader.nextLine();
            Integer n = Integer.parseInt(nm.split(" ")[0]);
            Integer m = Integer.parseInt(nm.split(" ")[1]);

            for(int j=0;j<n;j++)
                dsp.vertices.put(j+1, new HashMap<>());

            for(int j=0;j<m;j++) {
                String edgeWtLine = reader.nextLine();
                dsp.addWeightedEdge(Integer.parseInt(edgeWtLine.split(" ")[0])
                        , Integer.parseInt(edgeWtLine.split(" ")[1])
                        , Integer.parseInt(edgeWtLine.split(" ")[2]));
                dsp.addWeightedEdge(Integer.parseInt(edgeWtLine.split(" ")[1])
                        , Integer.parseInt(edgeWtLine.split(" ")[0])
                        , Integer.parseInt(edgeWtLine.split(" ")[2]));
            }

            Map<Integer,Integer> dist = dsp.getShortestPath(Integer.parseInt(reader.nextLine()));
            for(Integer d:dist.keySet()) {
                if(dist.get(d)==999)
                    output[i].append("-1 ");
                else if(dist.get(d)!=0)
                    output[i].append(dist.get(d) + " ");
            }
            /*if(i<(testCases-1))
                System.out.println("");*/
        }

        for(int i=0;i<testCases;i++) {
            System.out.print(output[i]);
            if(i<(testCases-1))
                System.out.println("");
        }

    }

    public Map<Integer,Integer> getShortestPath(Integer start) {
        VertexDistanceComparator pqc = new VertexDistanceComparator();
        //PriorityQueue<VertexDistance> pq = new PriorityQueue<>(pqc);
        Map<Integer,Integer> pqmap = new HashMap<>();

        Map<Integer,Integer> dist = new HashMap<>();
        Map<Integer,Integer> pred = new HashMap<>();

        for(Integer v:vertices.keySet()) {
            dist.put(v,999);
            pred.put(v,null);
        }

        dist.put(start, 0);

        for(Integer v:vertices.keySet()) {
            //pq.offer(new VertexDistance(v,dist.get(v)));
            pqmap.put(v, dist.get(v));
        }

        while(!pqmap.isEmpty()) {
            //VertexDistance ud = pq.poll();

            Integer minV = -1;
            Integer minDistV = 999;
            for(Integer v: pqmap.keySet()) {
                if(pqmap.get(v)!=999 && pqmap.get(v)<minDistV) {
                    minV = v;
                    minDistV = pqmap.get(v);
                }
            }

            if(minV==-1) {
                minV = pqmap.keySet().iterator().next();
                minDistV = pqmap.get(minV);
            }

            pqmap.remove(minV);
            VertexDistance ud = new VertexDistance(minV, minDistV);

            if(vertices.get(ud.getV())!=null) {
                for (Integer v : vertices.get(ud.getV()).keySet()) {
                    Integer newLen = dist.get(ud.getV()) + this.getWeight(ud.getV(), v);
                    if (dist.get(v) > newLen) {
                        /*Iterator<VertexDistance> it = pq.iterator();
                        while(it.hasNext()) {
                            VertexDistance vd = it.next();
                            if(vd.getV()==v) {
                                vd.setW(newLen);
                                break;
                            }
                        }*/
                        pqmap.put(v, newLen);
                        pred.put(v, ud.getV());
                        dist.put(v, newLen);
                    }
                }
            } else {

            }
        }

        //System.out.println(dist.toString());
        //System.out.println(pred.toString());

        /*Map<Integer, LinkedList<Integer>> map = new HashMap<>();
        for(Integer v: vertices.keySet()) {
            map.put()
        }*/
        return dist;
    }
}

class Pair<V,W> {
    V v;
    W w;

    public Pair(V v, W w) {
        this.v = v;
        this.w = w;
    }

    public V getV() {
        return v;
    }

    public void setV(V v) {
        this.v = v;
    }

    public W getW() {
        return w;
    }

    public void setW(W w) {
        this.w = w;
    }
}

class VertexDistanceComparator implements Comparator<VertexDistance> {
    public int compare(VertexDistance v1, VertexDistance v2) {
        return v1.getW().compareTo(v2.getW());
    }
}

class VertexDistance {
    Integer v;
    Integer w;

    public VertexDistance(Integer v, Integer w) {
        this.v = v;
        this.w = w;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }

}
