package eu.uqasar.reporting.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author vmadmin
 */
public class Util {

    public static void main(String[] args) {
        Util util = new Util();
//        Util.retrieveDimensions("http://uqasar.pythonanywhere.com/cube/jira/model");
        System.out.println(Util.createExpressionEditor(null));

    }//EoM      

    public static String createExpressionEditor(String cubeurl) {
        String script = "";

        if (cubeurl == null || cubeurl.equalsIgnoreCase("")) {
//            cubeurl = "http://uqasar.pythonanywhere.com/cube/jira";
            cubeurl = "http://uqasar.pythonanywhere.com/cube/sonarqube";
        }

        ArrayList dimensions = new ArrayList();
        // get the dimension through /model
        dimensions = (ArrayList) Util.retrieveDimensions(cubeurl + "/model");

        script = "  <script>               "
                + "       "
                + "   var filters = [";

        script += "{\n"
                + "                    id: \"drilldown\",\n"
                + "                    label: \"drilldown\",\n"
                + "                    type: \"string\",\n"
                + "                    input: \"select\",\n"
                + "                        values: {\n";

        for (int j = 0; j < dimensions.size(); j++) {
            String retvalue = (String) dimensions.get(j);
            if (j > 0) {
                script += ",";
            }
            script += " \"" + retvalue + "\" : \"" + retvalue + "\" ";
        }

        script += "                         },\n"
                + "                    operators: [\"equal\"]\n"
                + "                }";

        for (int i = 0; i < dimensions.size(); i++) {
            //get dimensions
            String dimension = (String) dimensions.get(i);
            ArrayList retvalues = (ArrayList) retrieveValues(cubeurl + "/members/" + dimension, dimension);

            script += ", {\n"
                    + "                    id: \"" + dimension + "\",\n"
                    + "                    label: \"" + dimension + "\",\n"
                    + "                    type: \"string\",\n"
                    + "                    input: \"select\",\n"
                    + "                        values: {\n";
            for (int j = 0; j < retvalues.size(); j++) {
                String retvalue = (String) retvalues.get(j);
                if (j > 0) {
                    script += ",";
                }
                script += " \"" + retvalue + "\" : \"" + retvalue + "\" ";
            }

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

        return script;
    }//EoM

    public static List retrieveDimensions(String url) {
        ArrayList objToReturn = new ArrayList();
        int maxretries = 10;
        boolean wellexecuted = false;
        while (wellexecuted == false && maxretries > 0) {
            try {
                maxretries--;
                JSONObject json = readJsonFromUrl(url);
                if (json.has("error")) {
                    System.out.println("Error during dimension retrieval");
                    objToReturn = null;
                } else {
                    JSONObject values = new JSONObject();
                    JSONArray arr = json.getJSONArray("dimensions");
                    for (int i = 0; i < arr.length(); i++) {
                        String value = arr.getJSONObject(i).getString("name");
                        System.out.println("Dimension: " + value);
                        objToReturn.add(value);
                    }//for
                    wellexecuted = true;
                }//else
            } catch (JSONException ex) {
                ex.printStackTrace();
                wellexecuted = false;
            }//catch            
        }//while

        return objToReturn;
    }//EoM      

    public static JSONObject readJsonFromUrl(String url) throws JSONException {
        InputStream is = null;
        JSONObject json = null;

        int maxretries = 10;
        boolean wellexecuted = false;
        while (wellexecuted == false && maxretries > 0) {
            try {
                maxretries--;
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                json = new JSONObject(jsonText);
                is.close();
                wellexecuted = true;
            } catch (IOException ex) {
                ex.printStackTrace();
                json = new JSONObject();
                json.put("error", ex);
                wellexecuted = false;
            }
        }//while

        return json;
    }//EoM    

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }//EoM        

    public static List retrieveValues(String url, String filtername) {
        ArrayList ret = new ArrayList();
        int maxretries = 10;
        boolean wellexecuted = false;
        while (wellexecuted == false && maxretries > 0) {
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
                wellexecuted = true;
            } catch (JSONException ex) {
                ex.printStackTrace();
                wellexecuted = false;                
            }
        }//while
        return ret;
    }//EoM

    public static String createExpressionEditorInputExperiment() {
        String str = "  <script>               "
                + "       "
                + "   var filters = ["
                + "{\n"
                + "                    id: \"drilldown\",\n"
                + "                    label: \"drilldown\",\n"
                + "                    type: \"string\",\n"
                + "                    input: \"select\",\n"
                + "                        values: {\n"
                + "                             1: 'Books',\n"
                + "                             2: 'Movies',\n"
                + "                             3: 'Music',\n"
                + "                             4: 'Goodies'\n"
                + "                         },\n"
                + "                    operators: [\"equal\"]\n"
                + "                }"
                + "];    "
                + "            $(\"#builder-basic\").queryBuilder({\n"
                + "                plugins: [\"sortable\", \"bt-tooltip-errors\"],\n"
                + "                filters: filters\n"
                + "            });\n"
                + "            \n"
                + "             //$.unblockUI();"
                + " </script>               ";
        return str;
    }//EoM     

}//EoC
