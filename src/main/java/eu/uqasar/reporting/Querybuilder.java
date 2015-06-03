package eu.uqasar.reporting;

import eu.uqasar.reporting.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author eleni
 */
public class Querybuilder extends HttpServlet {

    String firstdrilldown = "";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject objToReturn = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");
        String jsonToParse = request.getParameter("data");
        //extract url
        String urlToLoad = constructCubeRetrieverURL(jsonToParse);
        String urlToLoadAsLink = "<a href='" + urlToLoad + "'>" + urlToLoad + "</a>";
        
        System.out.println(urlToLoadAsLink);
        JSONObject cuberesponse = Util.readJsonFromUrl(urlToLoad);
        System.out.println(cuberesponse);
        JSONObject donutchartJSON = new JSONObject();

        if (cuberesponse.has("error")) {
            System.out.println("Exception during retrieval !");
            objToReturn.put("error", cuberesponse.get("error"));
        } else {

            JSONArray cuberesponse_arr = cuberesponse.getJSONArray("cells");

            if (cuberesponse_arr.length() > 0) {

                System.out.println("cuberesponse_arr");
                System.out.println(cuberesponse_arr);

                List<String> facts = Arrays.asList("Status", "Priority", "Reporter", "Resolution", "Creator", "Project", "Assignee", "jira_Issuekey", "Type", "Created");

                String cube_table = "<table cellpadding='10'>";

                cube_table += "<tr>";
                for (String fact : facts) {

                    if (cuberesponse_arr.optJSONObject(0).has(fact)) {

                        cube_table += "<th>" + fact + "</th>";
                    }

                }
                cube_table += "<th>count</th>";
                cube_table += "</tr>";

                int[] barchartvalues = new int[cuberesponse_arr.length()];

                for (int i = 0; i < cuberesponse_arr.length(); i++) {

                    String factsDescription = "";
                    cube_table += "<tr>";
                    for (String fact : facts) {

                        if (cuberesponse_arr.optJSONObject(i).has(fact)) {

                            String factid = cuberesponse_arr.getJSONObject(i).getString(fact);
                            System.out.println(fact + " : " + factid + "\n\n");
                            cube_table += "<td>" + factid + "</td>";
                            factsDescription += fact + ":" + factid + " ";

                        }

                    }

                    int countID = cuberesponse_arr.getJSONObject(i).getInt("count");
                    System.out.println("count : " + countID + "\n\n");
                    cube_table += "<td>" + countID + "</td>";
                    barchartvalues[i] = countID;

                    cube_table += "</tr>";

                    donutchartJSON.put(factsDescription, countID);

                }

                cube_table += "</table>";

                objToReturn.put("totalcuberesponse", cuberesponse);
                objToReturn.put("cubetable", cube_table);
                objToReturn.put("donutchart", donutchartJSON.toString());

            }

            objToReturn.put("cubeurl", urlToLoadAsLink);
            objToReturn.put("totalcount", cuberesponse.get("summary"));
        }

        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("objToReturn" + objToReturn);
        response.getWriter().print(objToReturn.toString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    //http://uqasar.pythonanywhere.com/cube/sonarqube   http://uqasar.pythonanywhere.com/cube/jira
    public String constructCubeRetrieverURL(String jsonToParse) {   //,String cubeendpoint

//        String urlToLoad = cubeendpoint+"/aggregate?";
        String urlToLoad = "http://uqasar.pythonanywhere.com/cube/sonarqube/aggregate?";

        JSONObject obj = new JSONObject(jsonToParse);
        JSONArray arr = obj.getJSONArray("rules");
        for (int i = 0; i < arr.length(); i++) {
            String id = arr.getJSONObject(i).getString("id");

            if (id.equalsIgnoreCase("drilldown")) {

                String value = arr.getJSONObject(i).getString("value");
                urlToLoad += "&drilldown=" + value;

                if (firstdrilldown.equalsIgnoreCase("")) {
                    firstdrilldown = value;
                }
            } else {
                urlToLoad += "&cut=" + arr.getJSONObject(i).getString("id") + ":" + arr.getJSONObject(i).getString("value");
            }
        }
        return urlToLoad;
    }//EoM

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
