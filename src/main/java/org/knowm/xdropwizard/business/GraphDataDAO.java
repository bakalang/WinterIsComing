package org.knowm.xdropwizard.business;

import org.knowm.yank.Yank;

import java.util.List;

/**
 * Created by Sky on 2016/7/19.
 */
public class GraphDataDAO {

    public static int insertGraphData(GraphData gd) {

        Object[] params = new Object[] { gd.getqDate(), gd.getParam(), gd.getData() };
        String SQL = "INSERT INTO GRAPH_DATA (Q_DATE, PARAM, DATA) VALUES (?, ?, ?)";
        return Yank.execute(SQL, params);
    }

    public static List<GraphData> selectAll() {
        String SQL = "SELECT Q_DATE, PARAM, DATA  FROM GRAPH_DATA";
        return Yank.queryBeanList(SQL, GraphData.class, null);
    }
}
