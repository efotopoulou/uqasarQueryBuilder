/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uqasar.eu.uqasarquerybuildernew;

/**
 *
 * @author eleni
 */
public class Report {

    public Report() {
    }

    public String createReport() {

        String htmlReport = "<html><head>"
                + "        <style>"
                + "            tr:nth-child(odd)		{ background-color:#eee; }"
                + "            tr:nth-child(even)		{ background-color:#fff; }"
                + "            td { padding-right: 15px;}"
                + "        </style>"
                + "    </head>"
                + "    <body>"
                + "        <div><h1><img src='img/logo.png'/></h1><div>"
                + "                <div id=\"cubeurl\">"
                + "                    <h3>Cube Retriever</h3>"
                + "                    <pre><a href=\"http://uqasar.pythonanywhere.com/cube/jira/aggregate?&amp;drilldown=Status\">http://uqasar.pythonanywhere.com/cube/jira/aggregate?&amp;drilldown=Status</a></pre>"
                + "                </div>"
                + "                "
                + "                 <div id=\"totalcount\">"
                + "                    <h3>Total count</h3>"
                + "                    <pre>225</pre>"
                + "                </div>"
                + ""
                + "                <div id=\"cubetable\">"
                + "                    <h3>Cube Visualization</h3>"
                + "                    <pre><table cellpadding=\"10\"><tbody><tr><th>Status</th><th>count</th></tr><tr><td>Done</td><td>149</td></tr><tr><td>In Progress</td><td>7</td></tr><tr><td>To Do</td><td>69</td></tr></tbody></table></pre>"
                + "                </div>"
                + "                <div id=\"donutchart\" style=\"height: 250px;\"><svg style=\"overflow: hidden; position: relative; top: -0.633331px;\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns=\"http://www.w3.org/2000/svg\" width=\"970\" version=\"1.1\" height=\"250\"><desc>Created with Raphaël 2.1.2</desc><defs></defs><path opacity=\"0\" stroke-width=\"2\" d=\"M485,201.66666666666669A76.66666666666667,76.66666666666667,0,0,0,556.7204900298873,97.90850525255897\" stroke=\"#0b62a4\" fill=\"none\" style=\"opacity: 0;\"></path><path stroke-width=\"3\" d=\"M485,204.66666666666669A79.66666666666667,79.66666666666667,0,0,0,559.5269439875785,96.84840328418085L587.9033117820121,86.129594492802A110,110,0,0,1,485,235Z\" stroke=\"#ffffff\" fill=\"#0b62a4\" style=\"\"></path><path opacity=\"0\" stroke-width=\"2\" d=\"M556.7204900298873,97.90850525255897A76.66666666666667,76.66666666666667,0,0,0,547.585407283678,80.71857530618593\" stroke=\"#3980b5\" fill=\"none\" style=\"opacity: 0;\"></path><path stroke-width=\"3\" d=\"M559.5269439875785,96.84840328418085A79.66666666666667,79.66666666666667,0,0,0,550.0344014817349,78.98582390512365L574.7964539287553,61.46578196104939A110,110,0,0,1,587.9033117820121,86.129594492802Z\" stroke=\"#ffffff\" fill=\"#3980b5\" style=\"\"></path><path opacity=\"1\" stroke-width=\"2\" d=\"M547.585407283678,80.71857530618593A76.66666666666667,76.66666666666667,0,1,0,484.97591445671867,201.66666288331834\" stroke=\"#679dc6\" fill=\"none\" style=\"opacity: 1;\"></path><path stroke-width=\"3\" d=\"M550.0344014817349,78.98582390512365A79.66666666666667,79.66666666666667,0,1,0,484.97497197893813,204.6666627352743L484.963871685078,239.99999432497754A115,115,0,1,1,578.8781109255169,58.5778629592789Z\" stroke=\"#ffffff\" fill=\"#679dc6\" style=\"\"></path><text stroke-width=\"0.6956521739130435\" transform=\"matrix(1.4375,0,0,1.4375,-212.1875,-54.25)\" font-weight=\"800\" fill=\"#000000\" stroke=\"none\" font-size=\"15px\" font-family=\"&quot;Arial&quot;\" text-anchor=\"middle\" y=\"115\" x=\"485\" style=\"text-anchor: middle; font-family: &quot;Arial&quot;; font-size: 15px; font-weight: 800;\"><tspan dy=\"5\">Status-&gt;Done</tspan><tspan x=\"485\" dy=\"18\"> </tspan></text><text stroke-width=\"0.6260869565217391\" transform=\"matrix(1.5972,0,0,1.5972,-289.6528,-75.8472)\" fill=\"#000000\" stroke=\"none\" font-size=\"14px\" font-family=\"&quot;Arial&quot;\" text-anchor=\"middle\" y=\"135\" x=\"485\" style=\"text-anchor: middle; font-family: &quot;Arial&quot;; font-size: 14px;\"><tspan dy=\"5\">149</tspan></text></svg></div>"
                + "            </div>	"
                + "        </div></body></html>";

        return htmlReport;
    }

//   <html lang="en"><head>
//        <meta charset="utf-8">
//        <meta name="viewport" content="width=device-width, initial-scale=1.0">
//
//        <title>U-QASAR</title>
//        <style>
//            tr:nth-child(odd)		{ background-color:#eee; }
//            tr:nth-child(even)		{ background-color:#fff; }
//            td { padding-right: 15px;}
//        </style>
//    </head>
//
//    <body>
//        <div class="container">
//            <h1>
//                <img src="img/logo.png">
//            </h1>
//
//            <div class="row marketing">
//                <div id="cubeurl" class="">
//                    <h3>Cube Retriever</h3>
//                    <pre><a href="http://uqasar.pythonanywhere.com/cube/jira/aggregate?&amp;drilldown=Status">http://uqasar.pythonanywhere.com/cube/jira/aggregate?&amp;drilldown=Status</a></pre>
//                </div>
//                
//                 <div id="totalcount" class="">
//                    <h3>Total count</h3>
//                    <pre>225</pre>
//                </div>
//
//                <div id="cubetable" class="">
//                    <h3>Cube Visualization</h3>
//                    <pre><table cellpadding="10"><tbody><tr><th>Status</th><th>count</th></tr><tr><td>Done</td><td>149</td></tr><tr><td>In Progress</td><td>7</td></tr><tr><td>To Do</td><td>69</td></tr></tbody></table></pre>
//                </div>
//                <div id="donutchart" style="height: 250px;"><svg style="overflow: hidden; position: relative; top: -0.633331px;" xmlns:xlink="http://www.w3.org/1999/xlink" xmlns="http://www.w3.org/2000/svg" width="970" version="1.1" height="250"><desc>Created with Raphaël 2.1.2</desc><defs></defs><path opacity="0" stroke-width="2" d="M485,201.66666666666669A76.66666666666667,76.66666666666667,0,0,0,556.7204900298873,97.90850525255897" stroke="#0b62a4" fill="none" style="opacity: 0;"></path><path stroke-width="3" d="M485,204.66666666666669A79.66666666666667,79.66666666666667,0,0,0,559.5269439875785,96.84840328418085L587.9033117820121,86.129594492802A110,110,0,0,1,485,235Z" stroke="#ffffff" fill="#0b62a4" style=""></path><path opacity="0" stroke-width="2" d="M556.7204900298873,97.90850525255897A76.66666666666667,76.66666666666667,0,0,0,547.585407283678,80.71857530618593" stroke="#3980b5" fill="none" style="opacity: 0;"></path><path stroke-width="3" d="M559.5269439875785,96.84840328418085A79.66666666666667,79.66666666666667,0,0,0,550.0344014817349,78.98582390512365L574.7964539287553,61.46578196104939A110,110,0,0,1,587.9033117820121,86.129594492802Z" stroke="#ffffff" fill="#3980b5" style=""></path><path opacity="1" stroke-width="2" d="M547.585407283678,80.71857530618593A76.66666666666667,76.66666666666667,0,1,0,484.97591445671867,201.66666288331834" stroke="#679dc6" fill="none" style="opacity: 1;"></path><path stroke-width="3" d="M550.0344014817349,78.98582390512365A79.66666666666667,79.66666666666667,0,1,0,484.97497197893813,204.6666627352743L484.963871685078,239.99999432497754A115,115,0,1,1,578.8781109255169,58.5778629592789Z" stroke="#ffffff" fill="#679dc6" style=""></path><text stroke-width="0.6956521739130435" transform="matrix(1.4375,0,0,1.4375,-212.1875,-54.25)" font-weight="800" fill="#000000" stroke="none" font-size="15px" font-family="&quot;Arial&quot;" text-anchor="middle" y="115" x="485" style="text-anchor: middle; font-family: &quot;Arial&quot;; font-size: 15px; font-weight: 800;"><tspan dy="5">Status-&gt;Done</tspan><tspan x="485" dy="18"> </tspan></text><text stroke-width="0.6260869565217391" transform="matrix(1.5972,0,0,1.5972,-289.6528,-75.8472)" fill="#000000" stroke="none" font-size="14px" font-family="&quot;Arial&quot;" text-anchor="middle" y="135" x="485" style="text-anchor: middle; font-family: &quot;Arial&quot;; font-size: 14px;"><tspan dy="5">149</tspan></text></svg></div>
//            </div>	
//        </div>          
//
//</body></html> 
}
