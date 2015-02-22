/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uqasar.eu.uqasarquerybuildernew;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author eleni
 */
public class uqquerybuilder extends HttpServlet {

   
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        JSONObject objToReturn = new JSONObject();
        response.setContentType("text/html;charset=UTF-8");

        String jsonToParse = request.getParameter("data");

        String urlToLoad = constructCubeRetrieverURL(jsonToParse);
        String urlToLoadAsLink = "<a href='" + urlToLoad + "'>" + urlToLoad + "</a>";

        System.out.println(urlToLoadAsLink);

        JSONObject cuberesponse = readJsonFromUrl(urlToLoad);
        System.out.println(cuberesponse);

        JSONObject donutchartJSON = new JSONObject();
        
        if (cuberesponse.has("error")){
             System.out.println("exo errorrrrrrrrrr!!!!");
         objToReturn.put("error", cuberesponse.get("error"));
        }else{

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

   
    public String constructCubeRetrieverURL(String jsonToParse) {

        String urlToLoad = "http://uqasar.pythonanywhere.com/cube/jira/aggregate?";
        //http://uqasar.pythonanywhere.com/cube/jira/members/Priority
        // drilldown=Status&drilldown=Priority&cut=Type:Task
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
    }

    public static JSONObject readJsonFromUrl(String url) throws JSONException {
        InputStream is = null;
        JSONObject json = null;
        try {
            is = new URL(url).openStream();
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                json = new JSONObject(jsonText);
                return json;
            } finally {
                is.close();
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(uqquerybuilder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(uqquerybuilder.class.getName()).log(Level.SEVERE, null, ex);
            json =new JSONObject();
            json.put("error", ex);
            return json;
            
        } 
        return json;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

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
