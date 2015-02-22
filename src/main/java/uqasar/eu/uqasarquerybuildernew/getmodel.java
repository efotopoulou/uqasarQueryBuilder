/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uqasar.eu.uqasarquerybuildernew;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static uqasar.eu.uqasarquerybuildernew.uqquerybuilder.readJsonFromUrl;

/**
 *
 * @author eleni
 */
public class getmodel extends HttpServlet {

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

        JSONObject drilldownvalues = new JSONObject();

        drilldownvalues.put("Status", "Status");
        drilldownvalues.put("Priority", "Priority");
        drilldownvalues.put("Reporter", "Reporter");
        drilldownvalues.put("Resolution", "Resolution");
        drilldownvalues.put("Creator", "Creator");
        drilldownvalues.put("Project", "Project");
        drilldownvalues.put("Assignee", "Assignee");
        drilldownvalues.put("jira_Issuekey", "jira_Issuekey");
        drilldownvalues.put("Type", "Type");
        drilldownvalues.put("Created", "Created");

        objToReturn.put("drilldownvalues", drilldownvalues.toString());

        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Status", "Status",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Priority", "Priority",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Reporter", "Reporter",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Resolution", "Resolution",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Creator", "Creator",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Project", "Project",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Assignee", "Assignee",objToReturn);
        //retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/jira_Issuekey", "jira_Issuekey");
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Type", "Type",objToReturn);
        retrieveValues("http://uqasar.pythonanywhere.com/cube/jira/members/Created", "Created",objToReturn);

        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objToReturn);
        response.getWriter().flush();
        response.getWriter().close();
    }

    private JSONObject retrieveValues(String url, String filtername,JSONObject objToReturn) {

        try {
            JSONObject json = readJsonFromUrl(url);
            JSONArray arr = json.getJSONArray("data");

            JSONObject values = new JSONObject();
            for (int i = 0; i < arr.length(); i++) {

                String value = arr.getJSONObject(i).getString(filtername);
                values.put(value, value);
                objToReturn.put(filtername, values.toString());

            }
        } catch (IOException ex) {
            Logger.getLogger(getmodel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(getmodel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objToReturn;
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
