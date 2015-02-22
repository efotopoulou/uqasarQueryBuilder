
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>U-QASAR</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/docs.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/docs.min.js"></script>
        <script src="js/jput.min.js"></script>
        <link rel="stylesheet" href="js/morris.css">
        <script src="js/raphael-min.js"></script>
        <script src="js/morris.min.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/u-qasar.js"></script>
        <script src="js/xepOnline.jqPlugin.js"></script>



        <style>
            tr:nth-child(odd)		{ background-color:#eee; }
            tr:nth-child(even)		{ background-color:#fff; }
            td { padding-right: 15px;}
        </style>
        <script>
            $(function() {
                $.blockUI({css: {
                        border: 'none',
                        padding: '15px',
                        backgroundColor: '#000',
                        '-webkit-border-radius': '10px',
                        '-moz-border-radius': '10px',
                        opacity: .5,
                        color: '#fff'
                    }});
            });



        </script>    
    </head>

    <body>
        <!--jsp:include page="/getmodel" /-->
        <div class="container">
            <h1>
                <img src="img/logo.png" />
            </h1>

            <div class="row marketing">
                <p align="center">

                <div id="builder-basic" style="width:1000px;"></div>

                <br/>
                <center>
                    <div class="btn-group">
                        <button class="btn btn-warning reset" data-target="basic">Reset</button>
                    </div>

                    <div class="btn-group">
                        <button class="btn btn-primary parse-json" data-target="basic">Retrieve Cube Info</button>
                    </div>


                    <div class="btn-group">
                        <button  class="btn btn-success"  id="print">Export to PDF</button>   
                    </div>


                </center>


                <div id="cubeurl" class="hide">
                    <h3>Cube Retriever</h3>
                    <pre></pre>
                </div>
                <div id="totalcount" class="hide">
                    <h3>Total count</h3>
                    <pre></pre>
                </div>
                <div id="toprint">
                    <div id="toprintchrome">
                        <img id="myImage" class="hide"/>



                        <div id="cubetable" class="hide">
                            <h3>Cube Report (total count:<span id="totalcountdetaill"></span>) </h3>
                            <pre></pre>
                        </div>  
                    </div>
                    <div id="donutchart" style="width:500px;height:500px;"></div>



                </div>


                <div id="totalcuberesponse" class="hide" style="float:left;width:48%">
                    <h3>Cube Data</h3>
                    <pre></pre>
                </div>

                <div id="result-basic" class="hide" style="float:right;width:48%">
                    <h3>Produced Rules</h3>
                    <pre></pre>
                </div>
                <link href="css/datepicker3.css" rel="stylesheet">
                <link href="css/selectize.default.css" rel="stylesheet">
                <link href="css/query-builder.min.css" rel="stylesheet">


                <script src="js/moment.min.js"></script>
                <script src="js/bootstrap-datepicker.js"></script>
                <script src="js/selectize.min.js"></script>
                <script src="js/query-builder.standalone.min.js"></script>
                <!--script src="js/query-builder.js"></script-->



                <script>
            $(document).ready(function() {


                var converterEngine = function(input) { // fn BLOB => Binary => Base64 ?
                    var uInt8Array = new Uint8Array(input),
                            i = uInt8Array.length;
                    var biStr = []; //new Array(i);
                    while (i--) {
                        biStr[i] = String.fromCharCode(uInt8Array[i]);
                    }
                    var base64 = window.btoa(biStr.join(''));
                    return base64;
                };

                var getImageBase64 = function(url, callback) {
                    // 1. Loading file from url:
                    var xhr = new XMLHttpRequest(url);
                    xhr.open('GET', url, true); // url is the url of a PNG image.
                    xhr.responseType = 'arraybuffer';
                    xhr.callback = callback;
                    xhr.onload = function(e) {
                        if (this.status == 200) { // 2. When loaded, do:
                            var imgBase64 = converterEngine(this.response); // convert BLOB to base64
                            this.callback(imgBase64);//execute callback function with data
                        }
                    };
                    xhr.send();
                };

                getImageBase64('img/logo1.png', function(data) {
                    $("#myImage").attr("src", "data:image/png;base64," + data);  // inject data:image in DOM
                })

            });




            $('#print').click(function() {

                $('#myImage').removeClass('hide');
                printMe();
                $('#myImage').addClass('hide');
            });
            function printMe() {
                xepOnline.Formatter.Format('toprint');

            }

            // reset builder
            $('.reset').on('click', function() {
                $('#builder-' + $(this).data('target')).queryBuilder('reset');
                $('#result-' + $(this).data('target')).addClass('hide');
            });

            // get rules
            $('.parse-json').on('click', function() {
                var res = $('#builder-' + $(this).data('target')).queryBuilder('getRules');
                $('#result-' + $(this).data('target')).removeClass('hide')
                        .find('pre').html(
                        JSON.stringify(res, null, 2)
                        );



                $.post('/uqasarQueryBuilderNew-1.0-SNAPSHOT/uqquerybuilder',
                        {
                            data: JSON.stringify(res, null, 2),
                        },
                        function(data, status) {

                            if (data.error){alert('Message From Query Builder '+data.error);}

                            $('#donutchart').html('');
                            $('#cubeurl').removeClass('hide').find('pre').html(data.cubeurl);
                            $('#totalcount').removeClass('hide').find('pre').html(JSON.stringify(data.totalcount.count.valueOf(), null, 2));

                            var totalcuberesponse = JSON.stringify(data.totalcuberesponse, null, 2);

                            if (totalcuberesponse) {
                                $('#totalcuberesponse').removeClass('hide').find('pre').html(totalcuberesponse);

                                var cubetable = data.cubetable.valueOf();
                                $('#cubetable').removeClass('hide').find('pre').html(cubetable);
                            } else {
                                $('#totalcuberesponse').addClass('hide');
                                $('#result-basic').addClass('hide');
                                $('#cubetable').addClass('hide');
                            }

                            var totalcountdetaill = JSON.stringify(data.totalcount.count.valueOf(), null, 2);
                            if (totalcountdetaill) {
                                $('#totalcountdetaill').removeClass('hide').html(JSON.stringify(data.totalcount.count.valueOf(), null, 2));
                            } else {
                                $('#totalcountdetaill').addClass('hide');
                            }




                            var donutchartjson = JSON.parse(data.donutchart);
                            if (donutchartjson) {
                                var count = Object.keys(donutchartjson).length;



                                var chart = Morris.Donut({
                                    element: 'donutchart',
                                    data: [0, 0],
                                    resize: true
                                });

                                function calcdata() {
                                    var ret = [];
                                    for (i in donutchartjson)
                                    {
                                        ret.push({
                                            label: "" + i + "", value: donutchartjson[i].valueOf()
                                        });
                                    }
                                    return ret;
                                }

                                chart.setData(calcdata());
                            } else {
                                $('#totalcountdetaill').addClass('hide');


                            }

                        }, "json");

            });

                </script>


                </body>
                </html>
