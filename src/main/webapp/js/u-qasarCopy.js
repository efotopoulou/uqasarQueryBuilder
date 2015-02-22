$('#builder-basic').queryBuilder({
  plugins: ['sortable', 'bt-tooltip-errors'],
  filters: [{
    id: 'project',
    label: 'Project',
    type: 'string',
    input: 'select',
    values: {
      1: 'U-QASAR',
      2: 'ARCADIA',
      3: 'Linked2Safety',
      4: 'Linked2Media'
    },
    operators: ['equal', 'not_equal']
  }, {
    id: 'type',
    label: 'Type',
    type: 'string',
    input: 'select',
    values: {
      1: 'Bug',
      2: 'Fix',
      3: 'Feature'
    },
    operators: ['equal', 'not_equal']
  }, {
    id: 'date_created',
    label: 'Created',
    type: 'date',
    validation: {
      format: 'YYYY/MM/DD'
    },
    plugin: 'datepicker',
    plugin_config: {
      format: 'yyyy/mm/dd',
      todayBtn: 'linked',
      todayHighlight: true,
      autoclose: true
    },
    operators: ['equal', 'not_equal', 'between']
  }, {
    id: 'date_resolved',
    label: 'Resolved',
    type: 'date',
    validation: {
      format: 'YYYY/MM/DD'
    },
    plugin: 'datepicker',
    plugin_config: {
      format: 'yyyy/mm/dd',
      todayBtn: 'linked',
      todayHighlight: true,
      autoclose: true
    },
    operators: ['equal', 'not_equal', 'between']
  }, {
    id: 'assignee',
    label: 'Assignee',
     type: 'string',
    input: 'select',
    values: {
      1: 'Panagiotis Gouvas',
      2: 'Eleni Fotopoulou'
    },
    operators: ['equal', 'not_equal']
  }, {
    id: 'priority',
    label: 'Priority',
     type: 'string',
    input: 'select',
    values: {
      1: 'Blocker',
      2: 'Critical',
      3: 'Major',
      4: 'Trivial',
      5: 'Minor'
    },
    operators: ['equal', 'not_equal']
  },{
    id: 'attachment 	',
    label: 'Attachment',
    type: 'integer',
    input: 'radio',
    values: {
      1: 'Yes',
      0: 'No'
    },
    operators: ['equal']
  }]

});