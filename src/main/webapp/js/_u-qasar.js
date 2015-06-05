$.get("/uqasarQueryBuilderNew-1.0-SNAPSHOT/getmodel",
        function(data, status) {
             if (data.error){alert('Problem in loading the model. Please reload the page! '+data.error);}
             var filters = [{
                    id: "drilldown",
                    label: "drilldown",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.drilldownvalues),
                    operators: ["equal"]
                },{
                    id: "Status",
                    label: "Status",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Status),
                    operators: ["equal"]
                },{
                    id: "Priority",
                    label: "Priority",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Priority),
                    operators: ["equal"]
                },{
                    id: "Reporter",
                    label: "Reporter",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Reporter),
                    operators: ["equal"]
                },
//                {
//                    id: "Resolution",
//                    label: "Resolution",
//                    type: "string",
//                    input: "select",
//                    values: JSON.parse(data.Resolution),
//                    operators: ["equal"]
//                },
                {
                    id: "Creator",
                    label: "Creator",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Creator),
                    operators: ["equal"]
                },{
                    id: "Project",
                    label: "Project",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Project),
                    operators: ["equal"]
                },{
                    id: "Assignee",
                    label: "Assignee",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Assignee),
                    operators: ["equal"]
                },{
                    id: "Type",
                    label: "Type",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Type),
                    operators: ["equal"]
                },{
                    id: "Created",
                    label: "Created",
                    type: "string",
                    input: "select",
                    values: JSON.parse(data.Created),
                    operators: ["equal"]
                }]

            $("#builder-basic").queryBuilder({
                plugins: ["sortable", "bt-tooltip-errors"],
                filters: filters
            });
            
             $.unblockUI();

        }, "json");

