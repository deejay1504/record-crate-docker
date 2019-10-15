var getAllSongs      = true;
var currentSortType  = 'asc';
var currentSortField = 1;
var songFormFields = ['song-artist-id', 'song-title-id', 'song-record-label-id', 'song-year-id', 'song-copies-id',
  'song-hh-id', 'song-format-id', 'song-side-id', 'song-genre-id', 'song-bpm-id', 'song-rpm-id'];
var ajaxFields = ['artist', 'songTitle', 'recordLabel', 'year', 'numberOfCopies', 'duration',
  'songFormat', 'side', 'genre', 'songBpm', 'songRpm'];
var songFields = ['Artist', 'Song Title', 'Record Label', 'Year', 'Number Of Copies', 'Duration',
  'Song Format', 'Side', 'Genre', 'Song Rpm', 'Song Bpm'];
$(document).ready(function() {
    var $body = $('body');

    // Highlight the top nav as scrolling occurs
    $body.scrollspy({
        target: '.navbar-fixed-top',
        offset: 51
    });

    // Closes the Responsive Menu on Menu Item Click
    $('.navbar-collapse ul li a').click(function () {
        $('.navbar-toggle:visible').click();
    });

    var greeting =  getTodaysDate() + ' - ' + getGreeting() + "!";
    $('#homeHeading1').text(greeting);
    $('#homeHeading2').text(greeting);

    $(document).on('click', '.popup-modal-dismiss', function (e) {
        $.magnificPopup.close();
    });

    openAllPanels = function(aId) {
      $(aId + ' .panel-collapse:not(".in")').collapse('show');
    }

    closeAllPanels = function(aId) {
      $(aId + ' .panel-collapse.in').collapse('hide');
    }

    // Change events
    $("#no-of-songs").change(function() {
      // If we refresh the page, re-start the song table from page 1
      $('.pagination-song').twbsPagination('destroy');
      showSongTable(1, currentSortField, currentSortType);
    });

    $('#find-song-id').change(function() {
      var msg = 'Please enter the ' + $("#find-song-id option:selected").text() + ' search value';
      $('#find-label').text(msg);
    });

    $('#song-format-id').change(function() {
      setSongFormat();
      setSongRpm();
    });

    $('#song-format-admin').change(function() {
      setSongRpm();
      setSongFormat();
    });

    // Click events
    $(".toggle-accordion").on("click", function() {
      var accordionId = $(this).attr("accordion-id"),
        numPanelOpen = $(accordionId + ' .collapse.in').length;

      $(this).toggleClass("active");

      if (numPanelOpen == 0) {
        openAllPanels(accordionId);
      } else {
        closeAllPanels(accordionId);
      }
    });

    $("#add-song").click(function() {
      validateAndSaveSong('add');
    });

    $("#update-song").click(function() {
      validateAndSaveSong('update');
    });

    $("#delete-song").click(function() {
      askDeleteSong();
    });

    $("#delete-button").click(function() {
      deleteSong();
    });

    $("#song-new-id").click(function() {
      addNewSong();
    });

    $("#song-add-id1").click(function() {
      addNewSong();
    });

    $("#song-add-id2").click(function() {
      addNewSong();
    });

    $('#song-options').click(function() {
      updateSongOptions();
    });

    $('#song-find-id').click(function() {
      $(".modal-header").addClass('light-blue-background');
      $("#find-song-field").val('');
      $('#find-song-modal').modal('show');
    });

    $('#find-button').click(function() {
      getAllSongs = false;
      $('#find-song-modal').modal('hide');
      $('.pagination-song').twbsPagination('destroy');
      showSongTable(1, currentSortField, currentSortType);
    });

    $('#song-crate1').click(function() {
      getAllSongs = true;
      $('.pagination-song').twbsPagination('destroy');
      showSongTable(1, currentSortField, currentSortType);
    });

    $('#song-crate2').click(function() {
      getAllSongs = true;
      $('.pagination-song').twbsPagination('destroy');
      showSongTable(1, currentSortField, currentSortType);
    });

    $('#song-export1').click(function() {
      exportSongs();
    });

    $('#song-export2').click(function() {
      exportSongs();
    });

    getAllSongs = true;
    initialiseSongFormat($('#song-format-admin'));
    initialiseAdminSongOptions();
    initialiseLastSongEntered();
    initialiseVariables();
    initialisePieChart();
    initialiseBarChart();
    initialiseTableHeaders();
    initialiseSongKick();
    showSongTable(1, currentSortField, currentSortType);
});

function setSongFormat() {
  $("#song-side-id").val('A Side');
  var songFormat = $("#song-format-id option:selected").text();
  if (songFormat == 'LP' || songFormat == 'CD' ||
      songFormat == 'CD Single' || songFormat == 'Digital') {
    $("#song-side-id").val('N/A');
  }
  if (songFormat == '7 inch') {
    $("#song-rpm-id").val('45 rpm');
  }
}

function setSongRpm() {
  var songFormat = $("#song-format-id option:selected").text();
  if (songFormat == 'CD' || songFormat == 'CD Single' ||
      songFormat == 'Digital') {
    $("#song-rpm-id").val('N/A');
  } else if (songFormat == 'LP') {
    $("#song-rpm-id").val('33 rpm');
  }
}

function addNewSong() {
  initialiseVariables();
  $("#song-id").val('');
  $("#song-artist-id").val('');
  $("#song-title-id").val('');
  $("#song-record-label-id").val('');
  $("#song-year-id").val('');
  $("#song-copies-id").val('1');
  $("#song-genre-id").val('');
  $("#song-bpm-id").val('0');
  $("#add-song").show();
  $("#update-song").hide();
  $("#delete-song").hide();
  $("#song-modal-info").modal('show');
}

function askDeleteSong() {
  var songId = $("#song-id").val();
  var artist = $("#song-artist-id").val();
  var title  = $("#song-title-id").val();
  $("#ok-button").hide();
  $("#delete-button").show();
  $("#cancel-button").text('Cancel');
  $("#hidden-field").val(songId + ":" + artist + ":" + title);
  var msg = 'Are you sure you want to delete <b>' +
    artist + '</b> - <b>' + title + '</b>?';
  displayAlertBox('Warning!', msg);
}

function deleteSong() {
  $("#song-modal-info").modal('hide');
  var songDetails = $("#hidden-field").val().split(':');
  var songId = songDetails[0];
  var artist = songDetails[1];
  var title  = songDetails[2];
  var songData = {
    'songId' : songId
  };
  $.ajax({
      url: '/api/delete/song',
      type: 'POST',
      contentType: 'application/json',
      data : JSON.stringify(songData)
  }).done(function (data, textStatus) {
    $('.pagination-song').twbsPagination('destroy');
    showSongTable(1, currentSortField, currentSortType);
    displayAlertBox('Info', artist + ' - ' + title + ' deleted!');
    setCloseButtonAlert();
    initialiseLastSongEntered();
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/delete/song ', err);
  });
}

function exportSongs() {
  $.ajax({
      url: '/api/export/songs',
      type: 'GET',
      contentType: 'application/json'
  }).done(function (data, textStatus) {
    if (data) {
      var dirName = $('#song-export-dir').val() + $('#song-export-name').val();
      setCloseButtonAlert();
      displayAlertBox('Info', 'Export to ' + dirName + ' completed!');
    } else {
      displayErrorAlert('Error exporting data!!', '');
    }
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/export/songs ', err);
  });
}

function setCloseButtonAlert() {
  $("#ok-button").hide();
  $("#delete-button").hide();
  $("#cancel-button").text('Close');
}

function displayErrorAlert(errorMsg, err) {
  setCloseButtonAlert();
  console.log(errorMsg, err);
  displayAlertBox('Error!', errorMsg + err);
}

function validateAndSaveSong(actionType) {
  var songId = $("#song-id").val();
  var artist = $("#song-artist-id").val();
  var title  = $("#song-title-id").val();
  var label  = $("#song-record-label-id").val();
  var year   = $("#song-year-id").val();
  var copies = $("#song-copies-id").val();
  var hours  = $("#song-hh-id option:selected").text();
  var mins   = $("#song-mm-id option:selected").text();
  var secs   = $("#song-ss-id option:selected").text();
  var format = $("#song-format-id option:selected").text();
  var side   = $("#song-side-id option:selected").text();
  var genre  = $("#song-genre-id").val();
  var rpm    = $("#song-rpm-id").val();
  var bpm    = $("#song-bpm-id").val();
  var errMsg = '';
  $('#modal-msg1').text('');
  setCloseButtonAlert();
  if (artist == '') {
    errMsg = errMsg + 'Artist name is missing<br/>';
  }
  if (title == '') {
    errMsg = errMsg + 'Song title is missing<br/>';
  }
  if (label == '') {
    errMsg = errMsg + 'Record  label is missing<br/>';
  }
  if (year == '') {
    errMsg = errMsg + 'Year is missing<br/>';
  }
  if (copies == '') {
    errMsg = errMsg + 'Number of copies is missing<br/>';
  }
  if (genre == '') {
    errMsg = errMsg + 'Genre is missing<br/>';
  }
  if (bpm == '') {
    errMsg = errMsg + 'Song BPM is missing<br/>';
  }
  if (isNaN(year)) {
    errMsg = errMsg + 'Year should be a number<br/>';
  }
  if (isNaN(copies)) {
    errMsg = errMsg + 'Number of copies should be a number<br/>';
  }
  if (isNaN(bpm)) {
    errMsg = errMsg + 'Song BPM should be a number<br/>';
  }
  var duration = hours + ":" + mins + ":" + secs;
  if (errMsg == '') {
    var songData = {
      'songId' : songId,
      'artist' : artist,
      'songTitle' : title,
      'recordLabel' : label,
      'year' : year,
      'numberOfCopies' : copies,
      'duration' : duration,
      'songFormat' : format,
      'side' : side,
      'genre' : genre,
      'songRpm' : rpm,
      'songBpm' : bpm
    };
    saveSong(songData, actionType);
  } else {
    displayErrorAlert(errMsg, '');
  }
}

function saveSong(songDetails, actionType) {
  $("#song-modal-info").modal('hide');
  $.ajax({
      url: '/api/save/song',
      type: 'POST',
      contentType: "application/json",
      data: JSON.stringify(songDetails)
  }).done(function (data, textStatus) {
    if (actionType == 'add') {
      var songMsg = songDetails['artist'] + ' - ' +
          songDetails['songTitle'] + ' added!';
      displayAlertBox('Info', songMsg);
      $('.pagination-song').twbsPagination('destroy');
      showSongTable(1, currentSortField, currentSortType);
      initialiseLastSongEntered();
    } else {
      updateTableRow(songDetails);
    }
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/save/song ', err);
  });
}

function displayAlertBox(msgType, msg) {
  $('#modal-msg1').empty();
  $('#modal-msg1').html(msg);
  var containsMessage = false;
  $( "#modal-msg-body label").each(function(i, lbl) {
     if ($(lbl).text() != '') {
       containsMessage = true;
     }
  });
  if (containsMessage) {
    showAlerts(msgType);
  }
}

function showAlerts(title){
    $(".modal-header").removeClass('red-background');
		$(".modal-header").removeClass('yellow-background');
		$(".modal-header").removeClass('light-blue-background');
		if (title == 'Error!') {
			$(".modal-header").addClass('red-background');
		} else if (title == 'Warning!') {
			$(".modal-header").addClass('yellow-background');
		} else {
			$(".modal-header").addClass('light-blue-background');
		}
    $('#modal-title').text(title);
    $('#modal-alert').modal('show');
}

function getTodaysDate() {
  var today = new Date();
  var day = today.getDay();
  var daylist = ["Sun","Mon","Tue","Wed","Thu","Fri","Sat"];
  var monthlist = ["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
  return daylist[day] + ' ' + today.getDate() + ' ' + monthlist[today.getMonth()]
  + ' ' + today.getFullYear();
}

function initialiseVariables() {
  $('#modal-msg1').empty();
  // Initialise the song duration drop down
  $('#song-hh-id').text('');
  $('#song-mm-id').text('');
  $('#song-ss-id').text('');
  for (var i = 0; i < 60; i++) {
    var val = (i < 10) ? "0" + i : i;
    $('#song-hh-id').append("<option value=\"" + val + "\">" + val + "</option>");
    $('#song-mm-id').append("<option value=\"" + val + "\">" + val + "</option>");
    $('#song-ss-id').append("<option value=\"" + val + "\">" + val + "</option>");
  }
  // Initialise the song format drop down
  initialiseSongFormat($('#song-format-id'));
  $('#song-format-id').val($('#song-format-admin').val());
  // Initialise the song side drop down
  $('#song-side-id').empty();
  $('#song-side-id').append("<option value=\"N/A\">N/A</option>");
  $('#song-side-id').append("<option value=\"A Side\">A Side</option>");
  $('#song-side-id').append("<option value=\"B Side\">B Side</option>");
  $('#song-side-id').append("<option value=\"AA Side\">AA Side</option>");
  setSongFormat();
  // $("#song-side-id").val('A Side');
  // Initialise the song rpm drop down
  $('#song-rpm-id').empty();
  $('#song-rpm-id').append("<option value=\"N/A\">N/A</option>");
  $('#song-rpm-id').append("<option value=\"33 rpm\">33 rpm</option>");
  $('#song-rpm-id').append("<option value=\"45 rpm\">45 rpm</option>");
  $('#song-rpm-id').append("<option value=\"78 rpm\">78 rpm</option>");
  setSongRpm();
  // Initialise the number of songs to display drop down
  $('#no-of-songs').empty();
  $("#no-of-songs").append("<option value=\"10\">10</option>");
  $("#no-of-songs").append("<option value=\"20\">20</option>");
  $("#no-of-songs").append("<option value=\"50\">50</option>");
  $("#no-of-songs").append("<option value=\"100\">100</option>");
  $("#no-of-songs").val('20');
  // Initialise the song search drop down
  $('#find-song-id').empty();
  for (var i = 0; i < 11; i++) {
    $('#find-song-id').append("<option value=\"" + (i + 1) + "\">" + songFields[i] + "</option>");
  }
  var msg = 'Please enter the ' + $("#find-song-id option:selected").text() + ' search value';
  $('#find-label').text(msg);
}

// Initialise the song format drop down
function initialiseSongFormat(id) {
  id.empty();
  id.append("<option value=\"7 inch\">7 inch</option>");
  id.append("<option value=\"10 inch\">10 inch</option>");
  id.append("<option value=\"12 inch\">12 inch</option>");
  id.append("<option value=\"EP\">EP</option>");
  id.append("<option value=\"LP\">LP</option>");
  id.append("<option value=\"CD\">CD</option>");
  id.append("<option value=\"CD Single\">CD Single</option>");
  id.append("<option value=\"Digital\">Digital</option>");
}

function initialisePieChart() {
  $.ajax({
      url: '/api/fetch/rpm',
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    if (data.length > 0) {
      buildPieChart('songs-by-rpm', 'Number of songs by RPM', 'RPM', data);
    }
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/fetch/rpm ', err);
  });
}

function initialiseBarChart() {
  $.ajax({
      url: '/api/fetch/format',
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    if (data.length > 0) {
      var categories = data[0];
      var formatData = data[1];
      buildBarChart('songs-by-format', 'Number of songs by format', 'Number of Songs', 'Song Format', categories, formatData);
    }
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/fetch/format ', err);
  });
}

function showSongTable(currentPageNo, sortField, sortType) {
  var noOfSongsToDisplay = $("#no-of-songs option:selected").text();
  var searchFieldNumber  = $("#find-song-id option:selected").val();
  var searchValue        = $("#find-song-field").val();
  $.ajax({
      url: '/api/fetch/allsongs',
      type: 'GET',
      contentType: "application/json",
      data : {
        'pageNo' : currentPageNo,
        'noOfSongsToDisplay' : noOfSongsToDisplay,
        'sortField' : sortField,
        'sortType' : sortType,
        'searchFieldNumber' : searchFieldNumber,
        'searchValue' : searchValue,
        'getAllSongs' : getAllSongs
      }
  }).done(function (data, textStatus) {
    displaySongTable(data, currentPageNo);
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/fetch/allsongs ', err);
  });
}

function displaySongTable(data, currentPageNo) {
  // Secure the first row of data returned by the
  // server as it contains the totals counts
  var totalSongsSelected = data.totalSongsSelected;
  var totalSongPages = data.totalSongPages;
  $("#total-pages").text('Page ' + currentPageNo + ' of ' + data.totalSongPages);
  $("#total-songs").text('Total number of songs: ' + data.totalSongsSelected);
  var songData = data.pageOfResults;
  $('.pagination-song').twbsPagination({
    startPage: currentPageNo,
    totalPages: totalSongPages,
    onPageClick: function (event, page) {
      showSongTable(page, currentSortField, currentSortType);
    }
  });
  $("#song-body").empty();
  if (songData != null && songData.length > 0) {
    var itemList = '';
    $.each(songData, function(index, item) {
      var row = '<tr id = "row_' + item.id + '" onclick="dislayInputForm(\'' + item.id + '\')" data-toggle="modal" href="#song-modal-info">';
      itemList = itemList + row;
      itemList = itemList + buildTableCell(item);
      itemList = itemList + '</tr>';
    });
    $('#song-table > tbody:first').append(itemList);
  }
}

function dislayInputForm(id) {
  $("#delete-song").show();
  $("#update-song").show();
  $("#add-song").hide();
  $("#song-id").val(id);
  var rowId = $('#row_' + id);
  rowId.find('td').each (function(column, td) {
    var field = $("#" + songFormFields[column]);
    var columnValue = $(td).text();
    if (column == 5) {
      // Set Song duration drop down
      var duration = columnValue.split(":");
      $("#song-hh-id").val(duration[0]);
      $("#song-mm-id").val(duration[1]);
      $("#song-ss-id").val(duration[2]);
    } else if (column == 6) {
      // Set Song format drop down
      $("#song-format-id").val(columnValue);
    } else if (column == 7) {
      // Set Song side drop down
      $("#song-side-id").val(columnValue);
    } else if (column == 10) {
      // Set Song RPM drop down
      $("#song-rpm-id").val(columnValue);
    } else {
      field.val(columnValue);
    }
  });
}

// Re-disply the updated table row with the new values entered via the form
function updateTableRow(songData) {
  var rowId = $('#row_' + songData['songId']);
  rowId.find('td').each (function(column, td) {
    var tableCell = '<b>' + songData[ajaxFields[column]] + '</b>';
    $(td).html(tableCell);
  });
}

function initialiseTableHeaders() {
  $.ajax({
      url: '/api/fetch/tableheaders',
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    buildTableHeaders(data, $("#song-header"));
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/fetch/tableheaders ', err);
  });
}

function initialiseAdminSongOptions() {
  $.ajax({
      url: '/api/fetch/config',
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    var exportDir  = data[0];
    var exportName = data[1];
    var currentSongFormat = data[2];
    var fileName = exportName['propertyValue'].split(".");
    fileName = fileName[0];
    $('#song-export-dir').val(exportDir['propertyValue']);
    $('#song-export-name').val(fileName);
    $('#song-format-admin').val(currentSongFormat['propertyValue']);
  }).fail(function (err, textStatus) {
    displayErrorAlert('Error calling /api/fetch/config ', err);
  });
}

function initialiseLastSongEntered() {
  $.ajax({
      url: '/api/fetch/last/song',
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    var lastRecord = data['artist'];
    lastRecord = lastRecord + ', ' + data['songTitle'];
    lastRecord = lastRecord + ', ' + data['recordLabel'];
    lastRecord = lastRecord + ', ' + data['songFormat'];
    lastRecord = lastRecord + ', ' + 'Genre: ' + data['genre'];
    $('#last-record').text(lastRecord);
  }).fail(function (err, textStatus) {
      displayErrorAlert('Error calling /api/fetch/last/song ', err);
  });
}

// Call the songkick api getting dates for the coming month
// Localise the results based on the ip address of the client
function initialiseSongKick() {
  var sDate   = new Date();
  var eDate   = new Date();
  eDate.setDate(sDate.getDate() + 30);
  var sMonth = (sDate.getMonth() + 1) < 10 ? '0' + (sDate.getMonth() + 1) : (sDate.getMonth() + 1);
  var eMonth = (eDate.getMonth() + 1) < 10 ? '0' + (eDate.getMonth() + 1) : (eDate.getMonth() + 1);
  var minDate = sDate.getFullYear() + '-' + sMonth + '-' + sDate.getDate();
  var maxDate = eDate.getFullYear() + '-' + eMonth + '-' + eDate.getDate();
  var songKickUrl = 'http://api.songkick.com/api/3.0/events.json?min_date=' + minDate +
    '&max_date=' + maxDate + '&location=clientip&apikey=NGEqcVHMhrUb1qRu';
  $("#songkickLabel").empty();
  $.ajax({
      url: songKickUrl,
      type: 'GET',
      contentType: "application/json"
  }).done(function (data, textStatus) {
    $.each(data['resultsPage']['results']['event'], function(i, entry){
       $("#songkickLabel").append('<a class="song-kick-label" href="' + entry.uri + ' " target="_blank">' +
        entry.displayName + '</a></br>');
    });
  }).fail(function (err, textStatus) {
    $("#songkickLabel").html('<b>You will need to be online to access Songkick.</b>');
  });
}

function scrollSection(sectionName) {
  var id = $("#home-song-crate");
  if (sectionName == 'graph') {
    id = $("#home-stats");
  } else if (sectionName == 'options') {
    id = $("#home-song-options");
  }
  id.animatescroll({
  	easing: "swing",     // Easing options
  	scrollSpeed: 900,    // Controls the scrolling speed
  	padding: 0,          // Adjusts little ups and downs in scrolling.
    element: "html,body" // The element in which you want this plugin to work.
	});
}

function updateSongOptions() {
  var dirName  = $('#song-export-dir').val();
  var fileName = $('#song-export-name').val();
  var msg = '';
  if (dirName == '') {
    msg = 'Export directory cannot be empty<br/>';
  }
  if (fileName == '') {
    msg = msg + 'Export file name cannot be empty<br/>';
  }
  if (msg == '') {
    var displayFileName = '';
    var fileExt = fileName.split(".");
    if (fileExt.length == 0) {
      displayFileName = fileName;
      fileName = fileName + '.csv';
    } else {
      displayFileName = fileExt[0];
      fileName = fileExt[0] + '.csv';
    }
    var adminData = {
      'exportDir' : dirName,
      'exportName' : fileName,
      'currentSongFormat' : $('#song-format-admin').val()
    };
    $.ajax({
        url: '/api/update/config',
        type: 'POST',
        contentType: "application/json",
        data : JSON.stringify(adminData)
    }).done(function (data, textStatus) {
      if (data) {
        $('#song-format-id').val($('#song-format-admin').val());
        $('#song-export-name').val(displayFileName);
        setCloseButtonAlert();
        displayAlertBox('Info', 'Configuration options updated!');
      } else {
        displayErrorAlert(dirName + ' is not a valid directory. Please enter another name.', '');
      }
    }).fail(function (err, textStatus) {
      displayErrorAlert('Error calling /api/update/cofig ', err);
    });
  } else {
    displayErrorAlert(msg, '');
  }
}

function buildTableHeaders(tableHeaderList, headerId) {
  headerId.text("");
  $.each(tableHeaderList, function(key, item) {
    var th = (item.thWidth == "") ? "<th>" : "<th width='" + item.thWidth + "'>";
    var hdrDetails = "";
    if (item.idUp != "") {
      hdrDetails = "<input id='" + item.idUp + "' type='image' src='/images/up_arrow.png'" +
        " title='" + item.idUpTitle + "'/>";
      hdrDetails = hdrDetails + "<input id='" + item.idDown + "' type='image' src='/images/down_arrow.png'" +
        " title='" + item.idDownTitle + "'/>";
    }
    headerId.append(th + item.hdrName + hdrDetails + "</th>");
    if (item.idUp != "") {
      setArrowStatus($("#" + item.idUp), $("#" + item.idDown), item.sortField);
    }
  });
}

function setArrowStatus(upArrowButton, downArrowButton, sortField) {
  upArrowButton.click(function() {
    currentSortType  = 'asc';
    currentSortField = sortField;
    upArrowButton.hide();
    downArrowButton.show();
    var currentPageNo = $('.pagination-song').twbsPagination('getCurrentPage');
    showSongTable(currentPageNo, currentSortField, currentSortType);
  });
  downArrowButton.click(function() {
    currentSortType  = 'desc';
    currentSortField = sortField;
    upArrowButton.show();
    downArrowButton.hide();
    var currentPageNo = $('.pagination-song').twbsPagination('getCurrentPage');
    showSongTable(currentPageNo, currentSortField, currentSortType);
  });
  if (sortField == 1) {
    upArrowButton.hide();
    downArrowButton.show();
  } else {
    upArrowButton.show();
    downArrowButton.hide();
  }
}

function buildTableCell(item) {
  // Replace any single quotes in names with a space just for display in modal dialog box
  var artist      = item.artist.replace(/\\/g, '');
  var songTitle   = item.songTitle.replace(/\\/g, '');
  var recordLabel = item.recordLabel.replace(/\\/g, '');
  var genre       = item.genre.replace(/\\/g, '');
  var duration    = item.durationHours + ":" + item.durationMins + ":" + item.durationSecs;
  var td = '<td class="text-primary-black cell-text"><b>' + artist + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + songTitle + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + recordLabel + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.year + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.numberOfCopies + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + duration + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.songFormat + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.side + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' +  genre + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.songBpm + '</b></td>';
  td = td + '<td class="text-primary-black cell-text"><b>' + item.songRpm + '</b></td>';
  return td;
}

function getGreeting(){
    var data = [
            [0,   4, "Good Night"],
            [5,  11, "Good Morning"],
            [12, 16, "Good Afternoon"],
            [17, 20, "Good Evening"],
            [21, 23, "Good Night"]
        ],
    hr = new Date().getHours();

    for(var i = 0; i < data.length; i++){
        if(hr >= data[i][0] && hr <= data[i][1]){
            return (data[i][2]);
        }
    }
}
