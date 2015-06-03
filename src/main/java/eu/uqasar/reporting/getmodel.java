package eu.uqasar.reporting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import eu.uqasar.reporting.util.Util;
import java.util.ArrayList;
import java.util.List;

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1 retrieve the cube url with fallback
        String cubeurl = "http://uqasar.pythonanywhere.com/cube/jira";

        if (request.getParameter("cubeurl") != null) {
            cubeurl = request.getParameter("cubeurl");
        }//if

        JSONObject objToReturn = new JSONObject();
        JSONObject drilldownvalues = new JSONObject();

        //step 2 extract all available dimentsions        
        ArrayList dimensions = new ArrayList();
        // get the dimension through /model
        dimensions = (ArrayList) Util.retrieveDimensions(cubeurl + "/model");

        String script = "  <script>               "
                + "       "
                + "   var filters = [";

        for (int i = 0; i < dimensions.size(); i++) {
            //get dimensions
            String dimension = (String) dimensions.get(i);
            ArrayList retvalues = (ArrayList) retrieveValues(cubeurl + "/members/" + dimension, dimension);
            
            if (i>0) script += ",\n";
            //get            
            script += "{\n"
                    + "                    id: \"" + dimension + "\",\n"
                    + "                    label: \"" + dimension + "\",\n"
                    + "                    type: \"string\",\n"
                    + "                    input: \"select\",\n"
                    + "                        values: {\n";
                    for (int j=0;j<retvalues.size();j++) { String  retvalue = (String) retvalues.get(j); script += ""+retvalue+": '"+retvalue+"',\\n\" ";  }
//                    + "                             1: 'Books',\n"
//                    + "                             2: 'Movies',\n"
//                    + "                             3: 'Music',\n"
//                    + "                             4: 'Goodies'\n"
            script += "                         },\n"
                    + "                    operators: [\"equal\"]\n"
                    + "                }";
        }//for

        script += "];    "
                + "            $(\"#builder-basic\").queryBuilder({\n"
                + "                plugins: [\"sortable\", \"bt-tooltip-errors\"],\n"
                + "                filters: filters\n"
                + "            });\n"
                + "            \n"
                + "             //$.unblockUI();"
                + " </script>               ";

        //step 3 dynamic values per dimension        
//        for (int i = 0; i < dimensions.size(); i++) {
//            String dimension = (String) dimensions.get(i);
//            //drilldownvalues.put("Status", "Status");            
//            drilldownvalues.put(dimension, dimension);
//            //get values
//            retrieveValues(cubeurl + "/members/" + dimension, dimension, objToReturn);
//        }//for
//        //step 4 finalize return structure
//        objToReturn.put("drilldownvalues", drilldownvalues.toString());
        
        
        response.setContentType("text/x-json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(objToReturn);
        response.getWriter().flush();
        response.getWriter().close();
    }//EoM processRequest

    
    private List retrieveValues(String url, String filtername) {
        ArrayList ret = new ArrayList();
        
        try {
            JSONObject json = Util.readJsonFromUrl(url);
            if (json.has("error")) {
                return ret;
            } else {
                JSONArray arr = json.getJSONArray("data");
                JSONObject values = new JSONObject();
                for (int i = 0; i < arr.length(); i++) {
                    String value = "null";
                    if (!arr.getJSONObject(i).isNull(filtername)) {
                        value = arr.getJSONObject(i).getString(filtername);
                    }
                    values.put(value, value);
                    ret.add(value);
                }//for
            }//else
        } catch (JSONException ex) {
            Logger.getLogger(getmodel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }//EoM
    
    private JSONObject retrieveValues(String url, String filtername, JSONObject objToReturn) {

        try {
            JSONObject json = Util.readJsonFromUrl(url);

            if (json.has("error")) {
                System.out.println("exo errorrrrrrrrrr!!!!");
                objToReturn.put("error", json.get("error"));
                return objToReturn;
            } else {

                JSONArray arr = json.getJSONArray("data");

                JSONObject values = new JSONObject();
                for (int i = 0; i < arr.length(); i++) {

                    String value = "null";
                    if (!arr.getJSONObject(i).isNull(filtername)) {
                        value = arr.getJSONObject(i).getString(filtername);
                    }
                    values.put(value, value);
                    objToReturn.put(filtername, values.toString());
                }//for
            }//else
        } catch (JSONException ex) {
            Logger.getLogger(getmodel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objToReturn;
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
//    @Override
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
//    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
//    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}//EoC
